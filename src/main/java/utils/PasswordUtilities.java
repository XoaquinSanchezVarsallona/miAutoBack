package utils;

import entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
//import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.Objects;

public class PasswordUtilities {
    static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
    //static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    static EntityManager entityManager;

    public PasswordUtilities() {
        PasswordUtilities.entityManager = factory.createEntityManager();
    }
    public static boolean passwordValidation(String email, String password, String userType) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            User user = findUserByEmail(email, entityManager);

            if (user == null) {
                return false;
            }


            //boolean isPasswordCorrect = BCrypt.checkpw(password, user.getPassword());
            boolean isPasswordCorrect = Objects.equals(password, user.getPassword());

            boolean isUserTypeCorrect = user.getUserType().equalsIgnoreCase(userType);

            return isPasswordCorrect && isUserTypeCorrect;
            // Replace this password check with a secure password verification method
            // such as BCrypt or another secure hashing algorithm
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User findUserByEmail(String email, EntityManager entityManager) {

        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public static User findUserByEmail(String email) {
        EntityManager entityManager = factory.createEntityManager();

        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    //uso UserDao para chequear si existe el usuario en la base de datos

    //si existe, redirecciono a la página home e inicio al usuario, si no existe muestro mensaje indicando q no existe.

}