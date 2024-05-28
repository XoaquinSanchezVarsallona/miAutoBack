package dao;

import entities.*;
import services.FamilyService;

import javax.persistence.*;
import java.util.List;

public class AlertDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");


    public static void saveAlert(Alert alert) {
        EntityManager em = FactoryCreator.factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(alert);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("An error occurred while saving the alert: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static List<Alert> getAlertsByFamilyApellido(String familyApellido) {
        EntityManager entityManager = factory.createEntityManager();
        List<Alert> alertas = null;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Alert> query = entityManager.createQuery(
                    "SELECT a FROM Alert a WHERE a.familia.apellido = :familyApellido", Alert.class);
            query.setParameter("familyApellido", familyApellido);
            alertas = query.getResultList();
            System.out.println("ALERTAS SON" + alertas);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("An error occurred while fetching the alerts: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return alertas;
    }

    public static boolean deleteAlert(Long idAlert) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Alert alert = em.find(Alert.class, idAlert);
        boolean isDeleted = false;
        if (alert != null) {
            em.remove(alert);
            isDeleted = true;
        }
        em.getTransaction().commit();
        em.close();
        return isDeleted;
    }

    public static void alertAllFamilies(Car car, User user) {
        EntityManager em = factory.createEntityManager();
        List<Familia> familias = car.getFamilias();
        String name = user.getName();
        String patente  = car.getPatente();
        String message = name + " has crashed vehicle " + patente;


        for (Familia familia : familias){
            try {
                FamilyService.addAlertToFamily(message, familia.getApellido(), name);
            }
            catch (Exception e) {
                System.out.println("An error occurred while saving the alert: " + e.getMessage());
            }
        }
        em.close();
    }

    public static String getPapers(User user, Car car) {
        for (Registration paper : user.getRegistration()) {
            if (paper.getCar() == car) {
                return paper.getPng();
            }

        }
        return null;
    }

    /*public static boolean deleteAlert(Long idAlert) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Alert alert = em.find(Alert.class, idAlert);
        boolean isDeleted = false;
        if (alert != null) {
            Familia familia = alert.getFamilia(); // Assuming you have a getter for Familia in Alert
            if (familia != null) {
                familia.getAlerts().remove(alert); // Assuming you have a getter for alerts in Familia
                em.persist(familia); // Update the Familia entity
            }
            em.remove(alert);
            isDeleted = true;
        }
        em.getTransaction().commit();
        em.close();
        return isDeleted;
    }*/
}
