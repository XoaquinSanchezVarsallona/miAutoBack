import entities.*;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        final EntityManager entityManager = factory.createEntityManager();

        //sample1(entityManager);
        //sample2(entityManager);
        // sample8(entityManager); // Intento de vínculo user y familia
        //sample4(entityManager); // Sample para borrar tablas
        // sample4(entityManager); // Sample para borrar tablas
        //sample5(entityManager); // Sample para vincular auto con familia
        //sample7(entityManager); // Sample para vincular alerta con familia
        //sample8(entityManager);
        sample9(entityManager);


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



