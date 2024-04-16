package dao;

import entities.Car;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AutoDao {
    public void RemoveCar(Car car) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        final EntityManager entityManager = factory.createEntityManager();

        // Comienza la transacción
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(car));

        entityManager.getTransaction().commit();
        entityManager.close();
        // Finaliza la transacción
        factory.close();
    }
    public void AddAuto(Car car){
        // Crea una session para poder hacer el query.
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        final EntityManager entityManager = factory.createEntityManager();

        // Comienza la transacción
        entityManager.getTransaction().begin();
        entityManager.persist(car);
        entityManager.getTransaction().commit();
        entityManager.close();
        // Finaliza la transacción
        factory.close();
    }
}
