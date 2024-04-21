import entities.Car;
import entities.Familia;
import entities.User;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

        final EntityManager entityManager = factory.createEntityManager();

        // sample1(entityManager);
        //sample2(entityManager);
        // sample8(entityManager); // Intento de vínculo user y familia
        sample4(entityManager); // Sample para borrar tablas
        //sample5(entityManager); // Sample para vincular auto con familia
        entityManager.close();

        factory.close();
    }

    private static void sample5(EntityManager entityManager) {
        User user = getUserWithUsername("hola", entityManager);
        Car car = new Car("AB567KU", "Toyota", "Corolla Cross", 1000, 2019,
                "11/12/24", "12/12/2023");
        Familia familia = user.getFamilias().get(0);
        familia.addCar(car);
        car.addFamilia(familia);
        // Comienza la transacción
        entityManager.getTransaction().begin();
        entityManager.persist(car);
        entityManager.getTransaction().commit();
        // Finaliza la transacción
        entityManager.close();
    }

    private static void sample4(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Query query = entityManager.createNativeQuery("DROP TABLE familia_conductores");
        Query query1 = entityManager.createNativeQuery("DROP TABLE familia_auto");
        Query query2 = entityManager.createNativeQuery("DROP TABLE familia");
        Query query3 = entityManager.createNativeQuery("DROP TABLE car");
        Query query4 = entityManager.createNativeQuery("DROP TABLE user");
        query.executeUpdate();
        query1.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void sample3(EntityManager entityManager) {
        Familia gonzales = new Familia("Gonzalessss");
        User mateo = new User("AAA", "AAA", "mateo", "A", "AAA", "buenosaires", "driver");
        mateo.addFamily(gonzales);
        gonzales.addUser(mateo);
        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(mateo);
        entityManager.persist(gonzales);

        entityManager.getTransaction().commit();
        // terminó transacción //
    }

    private static void sample8(EntityManager entityManager) {
        Familia gonzales = new Familia("AA");
        Familia misAutosGonza = new Familia("AA2");
        Familia misAutos = new Familia("misAutosAA");


        User mateo = new User("AAA", "AAA", "mateo", "AA", "AAA", "buenosaires", "driver");
        mateo.addFamily(gonzales);
        gonzales.addUser(mateo);

        mateo.addFamily(misAutosGonza);
        misAutosGonza.addUser(mateo);

        mateo.addFamily(misAutos);
        misAutos.addUser(mateo);

        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(mateo);
        entityManager.persist(gonzales);
        entityManager.persist(misAutosGonza);
        entityManager.persist(misAutos);

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
        User user = getUserWithUsername("hola", entityManager);

        Familia Perez = new Familia("Gonzales");
        user.addFamily(Perez);
        Perez.addUser(user);

        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(Perez);

        entityManager.getTransaction().commit();
        // terminó transacción //
    }

    private static User getUserWithUsername(String username, EntityManager entityManager) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}



