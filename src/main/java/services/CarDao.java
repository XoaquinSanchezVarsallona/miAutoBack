package services;

import entities.Car;
import entities.User;
import org.springframework.context.expression.CachedExpressionEvaluator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CarDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public static void saveCar(Car car) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        em.close();
    }

    public static Car getCarByPatente(String patente) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c WHERE c.patente = :patente", Car.class);
        query.setParameter("patente", patente);
        Car result = query.getSingleResult();
        em.close();
        return result;
    }

    public static void deleteCar(Car car) {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    Car managedCar = em.merge(car);
    em.remove(managedCar);
    em.getTransaction().commit();
    em.close();
    }
}
