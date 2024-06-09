package dao;

import entities.Experience;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

public class ExperienceDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public static List<Experience> getAllExperiences() {
        EntityManager em = factory.createEntityManager();

        try {
            TypedQuery<Experience> query = em.createQuery("SELECT e FROM Experience e", Experience.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void createExperience(Long userId, Long storeId, String patente, String description, int rating) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Experience experience = new Experience();
            experience.setUserId(userId);
            experience.setStoreId(storeId);
            experience.setPatente(patente);
            experience.setDescription(description);
            experience.setRating(rating);
            experience.setCreationDate(new Timestamp(System.currentTimeMillis()));
            em.persist(experience);
            transaction.commit();
        } finally {
            em.close();
        }
    }

    public static void deleteExperience(long experienceId) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Experience experience = em.find(Experience.class, experienceId);
            if (experience != null) {
                em.remove(experience);
            }
            transaction.commit();
        } finally {
            em.close();
        }
    }
}
