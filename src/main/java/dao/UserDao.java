package dao;

import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserDao {
    static EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        UserDao.entityManager = entityManager;
    }

    public User findUserByEmail(String email) {
        return entityManager.find(User.class, email);
    }

    public void saveUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static boolean userExists(String email, String username) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email OR u.username = :username";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
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
}

