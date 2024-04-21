package dao;

import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserDao {
    private static final EntityManager entityManager = FactoryCreator.getEntityManager();


    public static User findUserByEmail(String email) {
        final EntityManager entityManagerA = FactoryCreator.getEntityManager();
        return entityManagerA.find(User.class, email);
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

    public static User findUserByUserID(Long userID) {
        final EntityManager entityManagerA = FactoryCreator.getEntityManager();
        return entityManagerA.find(User.class, userID);
    }

    public static void saveUser(User user) {
        final EntityManager entityManagerA = FactoryCreator.getEntityManager();

        entityManagerA.getTransaction().begin();
        entityManagerA.persist(user);
        entityManagerA.getTransaction().commit();
        entityManagerA.close();
    }

    public static boolean userExists(String email, String username) {
        final EntityManager entityManagerA = FactoryCreator.getEntityManager();

        String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email OR u.username = :username";
        TypedQuery<Long> query = entityManagerA.createQuery(jpql, Long.class);
        query.setParameter("email", email);
        query.setParameter("username", username);

        try {
            //si hay alguno, existe
            Long count = query.getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            //no existen usuarios con ese username o mail
            return false;
        }

    }
    public static User createUserDriver(String email, String username, String name, String surname, String password, String domicilio, String userType) {
        return new User(email, username, name, surname, password, domicilio, userType);
    }

    public static boolean updateUserField(Long userId, String field, String newValue) {
        //System.out.println("updateUserField called with userId: " + userId + ", field: " + field + ", newValue: " + newValue);

        try {
            final EntityManager entityManager = FactoryCreator.getEntityManager();
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, userId);
            System.out.println("User: " + user);

            if (user == null) {
                System.out.println("User not found");
                entityManager.close();
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
                case "domicilio":
                    user.setDomicilio(newValue);
                    break;
                default:
                    System.out.println("Invalid field: " + field);
                    entityManager.close();
                    return false;
            }
            entityManager.merge(user);
            entityManager.getTransaction().commit();
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
}}

