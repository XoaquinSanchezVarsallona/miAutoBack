package methods;

import org.austral.ing.lab1.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;

public class LoginRequest {
    public static boolean passwordValidation(String email, String password, String userType, EntityManager entityManager) {
        try {
            // Assuming UserDriver is an entity representing users with a 'password' property
            javax.persistence.TypedQuery<User> query = entityManager.createQuery("SELECT ud FROM User ud WHERE ud.email = :email AND ud.userType = :userType", User.class);
            query.setParameter("email", email);
            query.setParameter("userType", userType);
            User user = query.getSingleResult();

            // Replace this password check with a secure password verification method
            // such as BCrypt or another secure hashing algorithm
            return user != null && user.getPassword().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //uso UserDao para chequear si existe el usuario en la base de datos

    //si existe, redirecciono a la página home e inicio al usuario, si no existe muestro mensaje indicando q no existe.

}