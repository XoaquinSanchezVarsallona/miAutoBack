package methods;

import org.austral.ing.lab1.UserDriver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class LoginRequest {
    // El SessionFactory que crea las sessiones para hacer los query.
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    // Obtener los mail y contraseña del usuario que quiere acceder


    //IMPLEMENTADO SOLO PARA USERDRIVER POR AHORA.
    public static boolean passwordValidation(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            // Assuming UserDriver is an entity representing users with a 'password' property
            Query<UserDriver> query = session.createQuery("SELECT ud FROM UserDriver ud WHERE ud.Email = :email", UserDriver.class);
            query.setParameter("email", email);
            UserDriver user = query.uniqueResult();

            // Replace this password check with a secure password verification method
            // such as BCrypt or another secure hashing algorithm
            return user != null && user.getPassword().equals(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void responseToRequest(String email, String password) {
        boolean  isValidated = passwordValidation(email, password);
        if (isValidated) {

        }
    }

    //uso UserDao para chequear si existe el usuario en la base de datos

    //si existe, redirecciono a la página home e inicio al usuario, si no existe muestro mensaje indicando q no existe.

}