package dao;

import entities.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class AlertDao {

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

    public static List<Alert> getAlertsByFamilyName(String familyName) {
        EntityManager entityManager = FactoryCreator.factory.createEntityManager();

        TypedQuery<Alert> query = entityManager.createQuery(
                "SELECT a FROM Alert a WHERE a.familia.apellido = :familyName", Alert.class);
        query.setParameter("familyName", familyName);
        return query.getResultList();
    }
}
