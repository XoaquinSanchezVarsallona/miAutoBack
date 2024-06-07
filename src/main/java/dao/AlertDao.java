package dao;

import entities.*;
import org.hibernate.Hibernate;
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
        String message = name + " has crashed the vehicle with patente: " + patente;


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

    public static void setAsRead(Long idAlert) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Alert alert = em.find(Alert.class, idAlert);
        if (alert != null) {
            alert.setAsRead();
            em.persist(alert);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void setAsUnread(Long idAlert) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Alert alert = em.find(Alert.class, idAlert);
        if (alert != null) {
            alert.setAsUnread();
            em.persist(alert);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static Integer countUnreadAlertsOfFamily(String familyApellido) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Alert> query = em.createQuery(
                "SELECT a FROM Alert a WHERE a.familia.apellido = :familyApellido AND a.isRead = false", Alert.class);
        query.setParameter("familyApellido", familyApellido);
        List<Alert> alerts = query.getResultList();
        int count = alerts.size();
        System.out.println("The number of unread alerts for the family " + familyApellido + " is: " + count);
        em.getTransaction().commit();
        em.close();
        return count;
    }

    public static boolean alertExists(String message) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Alert> query = em.createQuery(
                "SELECT a FROM Alert a WHERE a.message = :message", Alert.class);
        query.setParameter("message", message);
        List<Alert> alerts = query.getResultList();
        boolean exists = !alerts.isEmpty();
        em.getTransaction().commit();
        em.close();
        return exists;
    }
}
