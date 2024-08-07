package dao;

import DTOs.PapersToDisplay;
import entities.Car;
import entities.Registration;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ImageDao {

    public static void saveDNI(String userID, String fields, String image) {
        EntityManager em = FactoryCreator.getEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, Long.parseLong(userID));
        if (fields.equals("front of DNI")) user.setDniCara(image);
        else if (fields.equals("back of DNI")) user.setDniContracara(image);
        else throw new IllegalArgumentException("Invalid fields");

        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public static void saveRegistration(String userID, String patente, String fields, String image) {
        EntityManager em = FactoryCreator.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            User user = em.find(User.class, Long.parseLong(userID));
            Car car = em.find(Car.class, patente);
            Registration registration = registrationAlreadyExists(user, car);

            if (registration != null) {
                registration.setPng(image);
            }
            else {
                registration = new Registration(image, user, car);
                user.addRegistration(registration);
                car.addRegister(registration);
            }

            em.merge(user);
            em.merge(car);

            transaction.commit();
        }
        catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        em.close();
    }

    private static Registration registrationAlreadyExists(User user, Car car) {
        for (Registration registration : user.getRegistration()) {
            if (registration.getCar().getPatente().equals(car.getPatente())) return registration;
        }
        return null;
    }

    public static PapersToDisplay getImages(String userID, String patente) {
        EntityManager em = FactoryCreator.getEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, Long.parseLong(userID));
        Car car = em.find(Car.class, patente);
        for (Registration registration :user.getRegistration()) {
            if (registration.getCar().equals(car)) return new PapersToDisplay(user, car, user.getDniCara(), user.getDniContracara(), registration.getPng());
        }
        throw new IllegalArgumentException("No registration binding ${user.name} and ${patente}");
    }
}
