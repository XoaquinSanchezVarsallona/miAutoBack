package dao;

import entities.Car;
import entities.Registration;
import entities.User;

import javax.persistence.EntityManager;

public class ImageDao {

    public static void saveDNI(String userID, String fields, String image) {
        EntityManager em = FactoryCreator.getEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, Long.parseLong(userID));
        if (fields.equals("front of DNI")) user.setDniCara(image);
        else if (fields.equals("back of DNI")) user.setDniCara(image);
        else throw new IllegalArgumentException("Invalid fields");

        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public static void saveRegistration(String userID, String patente, String fields, String image) {
        EntityManager em = FactoryCreator.getEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, Long.parseLong(userID));
        Car car = em.find(Car.class, patente);
        Registration register = new Registration(image);
        user.addRegistration(register);
        car.addRegister(register);

        em.merge(user);
        em.merge(car);
        em.getTransaction().commit();
        em.close();
    }
}
