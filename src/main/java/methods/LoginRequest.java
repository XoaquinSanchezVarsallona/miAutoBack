package methods;

import org.austral.ing.lab1.UserDriver;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;

public class LoginRequest {
    //IMPLEMENTADO SOLO PARA USERDRIVER POR AHORA.
    public static boolean passwordValidation(String email, String password, EntityManager entityManager) {
        try {
            // Assuming UserDriver is an entity representing users with a 'password' property
            javax.persistence.TypedQuery<UserDriver> query = entityManager.createQuery("SELECT ud FROM UserDriver ud WHERE ud.email = :email", UserDriver.class);
            query.setParameter("email", email);
            UserDriver user = query.getSingleResult();

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