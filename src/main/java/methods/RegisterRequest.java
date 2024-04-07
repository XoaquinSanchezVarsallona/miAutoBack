package methods;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.austral.ing.lab1.UserDriver;

public class RegisterRequest {
    // Guardado en la bd
    public static void saveInBd(UserDriver user) {
        // Crea una session para poder hacer el query.
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        final EntityManager entityManager = factory.createEntityManager();

        // Comienza la transacción
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        // Finaliza la transacción
        factory.close();
    }
    // Obtener los mail y contraseña del usuario que quiere acceder

    // Si es un driver, pido los otros parámetros que pertenecen a un conductor

    // Si es service, le pido los parámetros del service

    // Uso UserDao para chequear si existe el usuario en la base de datos, y si todavía no existe lo agrego a la bdd.

    // Si se registra, lo redirecciono a la página de login, si no muestro mensaje que el usuario ya existe.
}