package services;

import dao.NotificationDao;

public class NotificationService {
    public static void createNotificationForUsersWithReview(long storeId, String description) {
        NotificationDao.createNotificationForUsersWithReview(storeId, description);
    }
}
