package dao;

import entities.Alert;
import entities.Familia;

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
}