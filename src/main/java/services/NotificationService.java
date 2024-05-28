package services;

import dao.NotificationDao;
import entities.Notification;

import java.util.List;

public class NotificationService {
    public static void createNotificationForUsersWithReview(long storeId, String description) {
        NotificationDao.createNotificationForUsersWithReview(storeId, description);
    }

    public static List<Notification> getNotificationsByStoreEmail(Long storeId) {
        return NotificationDao.getNotificationsByStoreEmail(storeId);
    }
}
