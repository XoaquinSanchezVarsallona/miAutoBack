import entities.*;
import services.RouteService;
import services.UserService;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        final EntityManager entityManager = factory.createEntityManager();

        sample1(entityManager);
        //sample2(entityManager);
        // sample8(entityManager); // Intento de vínculo user y familia
        //sample4(entityManager); // Sample para borrar tablas
        // sample4(entityManager); // Sample para borrar tablas
        //sample5(entityManager); // Sample para vincular auto con familia
        //sample7(entityManager); // Sample para vincular alerta con familia
        //sample8(entityManager);
        // sample9(entityManager);



        entityManager.close();
        factory.close();
    }
    private static void sample9 (EntityManager entityManager) {
        Car car = new Car("AA476OV", "Toyota", "Corolla Cross", 1000, 2019,
                "11/12/24", "12/12/2023");
    }

    private static void sample4(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        //Query query5 = entityManager.createNativeQuery("ALTER TABLE familia DROP CONSTRAINT FKSQ79NXOIP6D3QOO9AI7X81MIS");
        //Query query6 = entityManager.createNativeQuery("ALTER TABLE familia DROP CONSTRAINT FK2L52BICGRLY0PS2UBRQBSC9FU");
        //Query query7 = entityManager.createNativeQuery("ALTER TABLE familia DROP CONSTRAINT FKL3926P5KPEVQ2G93VDA1W7RWK");
        Query query = entityManager.createNativeQuery("DROP TABLE familia_conductores");
        Query query1 = entityManager.createNativeQuery("DROP TABLE familia_auto");
        Query query2 = entityManager.createNativeQuery("DROP TABLE familia");
        Query query3 = entityManager.createNativeQuery("DROP TABLE car");
        Query query4 = entityManager.createNativeQuery("DROP TABLE familia");
        Query query6 = entityManager.createNativeQuery("DROP TABLE car");
        //query5.executeUpdate();
        //query6.executeUpdate();
        //query7.executeUpdate();
        //query.executeUpdate();
        query1.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();
        query4.executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void sample3(EntityManager entityManager) {
        Familia gonzales = new Familia("Gonzalessss");
        User mateo = new User("AAA", "AAA", "mateo", "A", "AAA", 11.0,11.0, "driver");
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

        User user = new User("123", "123", "mateo", "A", "123", 50.0, 50.0, "driver");
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


        User mateo = new User("AAA", "AAA", "mateo", "AA", "AAA", 100.0, 100.0, "driver");
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
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Random random = new Random();
        User user = UserService.findUserById(8052L);

        entityManager.getTransaction().begin();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int randomKilometraje = 1 + random.nextInt(150); // Generates random integers between 100 and 999
            RouteService.createRoute("ETU234", user, String.valueOf(randomKilometraje), "1", date.toString());
        }

        entityManager.getTransaction().commit();
    }

    private static void sample2(EntityManager entityManager) {
        Notification noti = new Notification();
        noti.setDescription("descuentooo");
        noti.setStoreId(8989);
        noti.setUserId(9999);

        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(noti);

        entityManager.getTransaction().commit();
        // terminó transacción //
    }

    private static User getUserWithUsername(String username, EntityManager entityManager) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}



