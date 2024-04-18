import entities.Car;
import entities.Familia;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

        final EntityManager entityManager = factory.createEntityManager();

        //sample1(entityManager);
        sample2(entityManager);

        entityManager.close();

        factory.close();
    }

    private static void sample1(EntityManager entityManager) {
        // Creo un conductor, una familia y un auto.
        Familia familiaPerez = new Familia("Perez");
        Car motomoto = new Car("AA476OV", "Toyota", "Corolla Cross", 1000, 2019,
                "1/12/2023", "1/12/2024");

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
        // Creo un conductor, una familia y un auto.
        User maxVerstappen = new User("max234@gmail.com", "Maxiver123", "Max", "Verstappen", "passwordMax", "buenos aires - la matanza", "driver");

        //comienza transacción //
        entityManager.getTransaction().begin();

        entityManager.persist(maxVerstappen);


        entityManager.getTransaction().commit();
        // terminó transacción //
    }

}



