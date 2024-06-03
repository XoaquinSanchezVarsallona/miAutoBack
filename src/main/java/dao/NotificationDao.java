package dao;

import entities.Notification;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public static String createNotificationForUsersWithReview(long storeId, String description) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Notification notification = null;


        try {
            transaction.begin();
            TypedQuery<Long> query = em.createQuery("SELECT r.userID FROM Review r WHERE r.storeID = :storeId", Long.class);
            query.setParameter("storeId", storeId);
            List<Long> userIds = query.getResultList();

            if (userIds.isEmpty()) {
                return "No users to notify";
            }

            for (Long userId : userIds) {
                notification = new Notification();
                notification.setStoreId(storeId);
                notification.setUserId(userId);
                notification.setDescription(description);
                em.persist(notification);
            }

            transaction.commit();
        } finally {
            em.close();
        }

        return "Notification created successfully";
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

    public static List<Notification> getNotificationsByUserId(long userId) {
        EntityManager em = factory.createEntityManager();

        try {
            TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.userId = :userId", Notification.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static void deleteNotification(long notificationId) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Notification notification = em.find(Notification.class, notificationId);
            if (notification != null) {
                em.remove(notification);
            }
            transaction.commit();
        } finally {
            em.close();
        }
    }

    public static void deleteNotificationFromDescription(long storeId, String description) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.storeId = :storeId AND n.description = :description", Notification.class);
            query.setParameter("storeId", storeId);
            query.setParameter("description", description);
            List<Notification> notifications = query.getResultList();
            for (Notification notification : notifications) {
                em.remove(notification);
            }
            transaction.commit();
        } finally {
            em.close();
        }
    }
}
