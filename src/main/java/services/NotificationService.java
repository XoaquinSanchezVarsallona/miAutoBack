package services;

import dao.NotificationDao;
import entities.Notification;

import java.util.List;

public class NotificationService {
    public static String createNotificationForUsersWithReview(long storeId, String description) {
        return NotificationDao.createNotificationForUsersWithReview(storeId, description);
    }

    public static List<Notification> getNotificationsByStoreEmail(Long storeId) {
        return NotificationDao.getNotificationsByStoreEmail(storeId);
    }

    public static List<Notification> getNotificationsByUserId(long userId) {
        return NotificationDao.getNotificationsByUserId(userId);
    }

    public static void deleteNotification(long notificationId) {
        NotificationDao.deleteNotification(notificationId);
    }

    public static void deleteNotificationFromDescription(long storeId, String description) {
        NotificationDao.deleteNotificationFromDescription(storeId, description);
    }
}
