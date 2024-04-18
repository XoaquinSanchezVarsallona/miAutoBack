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

    public static boolean updateUserField(String userId, String field, String newValue) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, userId);

        if (user == null) {
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
                entityManager.close();
                return false;
        }

        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return true;
    }
}

