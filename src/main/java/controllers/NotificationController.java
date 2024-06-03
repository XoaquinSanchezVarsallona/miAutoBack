package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.StoreDao;
import entities.Notification;
import services.NotificationService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Objects;

public class NotificationController {
    public Route createNotification = (Request request, Response response) -> {
        System.out.println("CREATENOTII");
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String storeEmail = jsonObj.get("email").getAsString();
        String description = jsonObj.get("description").getAsString();
        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        String message = NotificationService.createNotificationForUsersWithReview(storeId, description);
        System.out.println(message);

        if ("No users to notify".equals(message)) {
            response.status(400);
            return message;
        }

        response.status(200);
        return message;
    };

    public Route fetchNotifications = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String storeEmail = jsonObj.get("storeEmail").getAsString();
        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();
        List<Notification> notifications = NotificationService.getNotificationsByStoreEmail(storeId);

        System.out.println("storeeee id" + storeId);
        System.out.println("notification" + notifications);
        response.status(200);
        response.type("application/json");
        return gson.toJson(notifications);
    };

    public Route fetchNotificationsByUserId = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        long userId = jsonObj.get("userID").getAsLong();
        List<Notification> notifications = NotificationService.getNotificationsByUserId(userId);

        response.status(200);
        return gson.toJson(notifications);
    };
    public Route deleteNotification = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        long notificationId = jsonObj.get("notificationId").getAsLong();
        NotificationService.deleteNotification(notificationId);

        response.status(200);
        return "Notification deleted";
    };

    public Route deleteNotificationFromDescription = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String storeEmail = jsonObj.get("storeEmail").getAsString();
        System.out.println("storeEmailll" + storeEmail);

        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        String description = jsonObj.get("description").getAsString();
        System.out.println("descriptionnnn" + description);
        NotificationService.deleteNotificationFromDescription(storeId, description);

        response.status(200);
        return "Notification deleted";
    };

}
