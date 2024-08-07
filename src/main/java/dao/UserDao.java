package dao;

import entities.Familia;
import entities.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public static User findUserByEmail(String email) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        User user = entityManager.find(User.class, email);
        entityManager.close();
        return user;
    }

    public static User findUserByUsername(String username) {
        final EntityManager entityManagerA = FactoryCreator.getEntityManager();
        TypedQuery<User> query = entityManagerA.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static User getUserByEmail(String email) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public static User findUserByUserID(Long userID) {
        final EntityManager entityManagerA = FactoryCreator.getEntityManager();
        return entityManagerA.find(User.class, userID);
    }

    public static void saveUser(User user) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static boolean userExists(String email, String username) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email OR u.username = :username";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("email", email);
        query.setParameter("username", username);
        try {
            // Si hay alguno, existe
            Long count = query.getSingleResult();
            entityManager.close();
            return count > 0;
        } catch (NoResultException e) {
            // No existen usuarios con ese username o mail
            return false;
        }

    }
    public static User createUserDriver(String email, String username, String name, String surname, String password, Double longitude, Double latitude, String userType) {
        return new User(email, username, name, surname, password, longitude, latitude, userType);
    }

    public static boolean updateUserField(Long userId, String field, String newValue) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, userId);
            System.out.println("User: " + user);

            if (user == null) {
                System.out.println("User not found");
                return false;
            }

            switch (field) {
                case "email":
                    user.setEmail(newValue);
                    break;
                case "username":
                    user.setUsername(newValue);
                    break;
                case "name":
                    user.setName(newValue);
                    break;
                case "surname":
                    user.setSurname(newValue);
                    break;
                case "password":
                    user.setPassword(newValue);
                    break;
                case "domicilioLongitude":
                    user.setDomicilioLongitude(Double.valueOf(newValue));
                    break;
                case "domicilioLatitude":
                    user.setDomicilioLatitude(Double.valueOf(newValue));
                    break;
                default:
                    System.out.println("Invalid field: " + field);
                    return false;
            }
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Failed to update user: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    public static List<User> findUsersByUserIDs(List<Long> result) {
        List<User> users = new ArrayList<>();
        for (Long id : result) {
            User user = findUserByUserID(id);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public static boolean usernameExists(String newValue) {
        return findUserByUsername(newValue) != null;
    }

    public static boolean deleteUser(Long userId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                return false;
            }
            for (Familia family : user.getFamilias()) {
                family.getUsers().remove(user);
                entityManager.merge(family);
            }
            user.getFamilias().clear();
            entityManager.remove(user);
            entityManager.flush();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}

