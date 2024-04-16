package methods;

import org.austral.ing.lab1.User;
//import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

public class LoginRequest {
    public static boolean passwordValidation(String email, String password, String userType, EntityManager entityManager) {
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

    static User findUserByEmail(String email, EntityManager entityManager) {
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