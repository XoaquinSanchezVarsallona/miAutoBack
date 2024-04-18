package utils;

import dao.FactoryCreator;
import entities.User;
import javax.persistence.*;
import java.util.Objects;

public class PasswordUtilities {
    static EntityManager entityManager = FactoryCreator.getEntityManager();

    public static boolean passwordValidation(String email, String password, String userType) {
        try {
            User user = findUserByEmail(email);

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

    public static User findUserByEmail(String email) {
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