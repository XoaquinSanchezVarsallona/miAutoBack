import entities.Alert;
import entities.Car;
import entities.Familia;
import entities.User;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        final EntityManager entityManager = factory.createEntityManager();

        //sample1(entityManager);
        //sample2(entityManager);
        // sample8(entityManager); // Intento de vínculo user y familia
        //sample4(entityManager); // Sample para borrar tablas
        // sample5(entityManager); // Sample para vincular auto con familia
        //sample4(entityManager); // Sample para borrar tablas
        //sample5(entityManager); // Sample para vincular auto con familia
        //sample7(entityManager); // Sample para vincular alerta con familia
        //sample8(entityManager);



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
        //Query queryExtra = entityManager.createNativeQuery("ALTER TABLE famlia DROP CONSTRAINT FKL3926P5KPEVQ2G93VDA1W7RWK");
         Query query = entityManager.createNativeQuery("DROP TABLE familia_conductores");
         Query query1 = entityManager.createNativeQuery("DROP TABLE familia_auto");
        Query query2 = entityManager.createNativeQuery("DROP TABLE car");
        Query query3 = entityManager.createNativeQuery("DROP TABLE alert");
        Query query4 = entityManager.createNativeQuery("DROP TABLE user");
        Query query5 = entityManager.createNativeQuery("DROP TABLE Familia");
        Query query6 = entityManager.createNativeQuery("DROP TABLE user_driver");
        Query query7 = entityManager.createNativeQuery("DROP TABLE userdriver");

        query.executeUpdate();
         query1.executeUpdate();
         query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();
        query5.executeUpdate();
        query6.executeUpdate();
        query7.executeUpdate();

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

    public static void sample7(EntityManager entityManager) {
        // Start a new transaction
        entityManager.getTransaction().begin();

        User user = new User("123", "123", "mateo", "A", "123", "buenosaires", "driver");
        Familia familia = new Familia("Smith");
        Alert alert = new Alert("Alert message", "Alert type");

        user.addFamily(familia);
        familia.addUser(user);

        alert.setFamilia(familia);
        familia.addAlert(alert);

        entityManager.persist(user);
        entityManager.persist(familia);
        entityManager.persist(alert);

        // Commit the transaction
        entityManager.getTransaction().commit();

        // Close the EntityManager
        entityManager.close();
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



