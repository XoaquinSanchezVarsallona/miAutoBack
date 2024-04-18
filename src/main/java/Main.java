import entities.Car;
import entities.Familia;
import entities.User;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

        final EntityManager entityManager = factory.createEntityManager();

        // sample1(entityManager);
        sample2(entityManager);
        // sample3(entityManager); // Intento de vínculo user y familia
        // sample4(entityManager); // Sample para borrar tablas

        entityManager.close();

        factory.close();
    }

    private static void sample4(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Query query = entityManager.createNativeQuery("DROP TABLE familia_conductores");
        Query query2 = entityManager.createNativeQuery("DROP TABLE familia_auto");
        Query query3 = entityManager.createNativeQuery("DROP TABLE familia");
        query.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void sample3(EntityManager entityManager) {
        Familia gonzales = new Familia("Gonzales");
        User mateo = new User("mateo", "mateo", "mateo", "Gonzales", "mateo", "buenosaires", "driver");
        mateo.addFamily(gonzales);
        gonzales.addUser(mateo);
        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(mateo);
        entityManager.persist(gonzales);

        entityManager.getTransaction().commit();
        // terminó transacción //
    }

    private static void sample1(EntityManager entityManager) {
        // Creo un conductor, una familia y un auto.
        Familia familiaPerez = new Familia("Perez");
        Car motomoto = new Car("AA476OV", "Toyota", "Corolla Cross", 1000, 2019,
                "11/12/24", "12/12/2023");

        // Genero la relación entre familia y auto, agregando cada uno a la lista del otro.
        familiaPerez.getCars().add(motomoto);
        motomoto.getFamilias().add(familiaPerez);

        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(familiaPerez);
        entityManager.persist(motomoto);

        entityManager.getTransaction().commit();
        // terminó transacción //
    }

    private static void sample2(EntityManager entityManager) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", "hola");
        User user = query.getSingleResult();
        Familia Perez = new Familia("Gonzales");
        user.addFamily(Perez);
        Perez.addUser(user);

        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(Perez);

        entityManager.getTransaction().commit();
        // terminó transacción //
    }

}



