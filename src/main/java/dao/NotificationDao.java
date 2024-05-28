package dao;

import entities.Notification;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public static void createNotificationForUsersWithReview(long storeId, String description) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Long> query = em.createQuery("SELECT r.userID FROM Review r WHERE r.storeID = :storeId", Long.class);
            query.setParameter("storeId", storeId);
            List<Long> userIds = query.getResultList();

            for (Long userId : userIds) {
                Notification notification = new Notification();
                notification.setStoreId(storeId);
                notification.setUserId(userId);
                notification.setDescription(description);
                em.persist(notification);
            }

            transaction.commit();
        } finally {
            em.close();
        }
    }

    public static List<Notification> getNotificationsByStoreEmail(Long storeId) {
        EntityManager em = factory.createEntityManager();

        try {
            TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.storeId = :storeId", Notification.class);
            query.setParameter("storeId", storeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
