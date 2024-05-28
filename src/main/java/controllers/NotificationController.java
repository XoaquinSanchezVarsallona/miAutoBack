package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.StoreDao;
import services.NotificationService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class NotificationController {
    public Route createNotification = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String storeEmail = jsonObj.get("email").getAsString();
        String description = jsonObj.get("description").getAsString();
        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        NotificationService.createNotificationForUsersWithReview(storeId, description);

        response.status(200);
        return "Notification created successfully";
    };
}
