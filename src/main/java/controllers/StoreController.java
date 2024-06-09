package controllers;

import DTOs.StoreDTO;
import dao.StoreDao;
import entities.Review;
import entities.Store;
import entities.User;
import services.StoreService;
import spark.Request;
import spark.Response;
import spark.Route;
import dao.UserDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StoreController {
    public Route addStore = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String email = jsonObj.get("email").getAsString();

        String storeEmail = jsonObj.get("storeEmail").getAsString();
        String storeName = jsonObj.get("storeName").getAsString();
        String domicilio = jsonObj.get("domicilio").getAsString();
        String tipoDeServicio = jsonObj.get("tipoDeServicio").getAsString();

        System.out.println("EMAIL STOREEE" + email);
        User user = UserDao.getUserByEmail(email); // Assuming you have a method to get User by email
        if (user == null) {
            response.status(400);
            return "User not found";
        }

        Store store = new Store(storeEmail, storeName, domicilio, tipoDeServicio);
        store.setUser(user);
        StoreDao.saveStore(store);

        response.status(200);
        return "Store added successfully";
    };

    public Route getStores = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String email = jsonObj.get("email").getAsString();
        List<StoreDTO> stores = StoreService.fetchStores(email);

        if (stores == null) {
            response.status(400);
            return gson.toJson("User not found");
        }

        response.status(200);
        return gson.toJson(stores);
    };

    public Route deleteStore = (Request request, Response response) -> {
        System.out.println("ENTRO A DELETESTORE");
        String storeEmail = request.params(":storeEmail");
        boolean result = StoreService.deleteStore(storeEmail);
        if (result) {
            response.status(200);
            return "Store deleted successfully";
        } else {
            response.status(400);
            return "Failed to delete store";
        }
    };

    public Route editStoreProfile = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String email = jsonObj.get("email").getAsString();
        String field = jsonObj.get("field").getAsString();
        String value = jsonObj.get("value").getAsString();
        System.out.println("TENGO EMAIL" + email);
        System.out.println("TENGO FIELD" + field);
        System.out.println("TENGO VALUE" + value);

        boolean result = StoreService.editStoreProfile(email, field, value);
        System.out.println("RESULT" + result);

        Map<String, String> message = new HashMap<>();
        if (result) {
            response.status(200);
            message.put("message", "Updated successfully");
        } else {
            response.status(400);
            message.put("message", "Failed to update profile");
        }
        return gson.toJson(message);
    };

    public Route getAllStores = (Request request, Response response) -> {
        System.out.println("ENTRO A GETALLSTORE");
        Gson gson = new Gson();

        List<StoreDTO> stores = StoreService.fetchAllStores();
        System.out.println("TENGO STORES: " + stores);

        if (stores == null) {
            response.status(400);
            return gson.toJson("User not found");
        }

        response.status(200);
        return gson.toJson(stores);
    };

    public Route editVisualStoreProfile = (Request request, Response response) -> {
        System.out.println("ENTRO A EDITVISUALPROFILE" );

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String email = jsonObj.get("email").getAsString();
        String domicilio = jsonObj.get("domicilio").getAsString();
        String name = jsonObj.get("storeName").getAsString();
        String tipoDeServicio = jsonObj.get("tipoDeServicio").getAsString();
        String description = jsonObj.get("description").getAsString();
        String phoneNumber = jsonObj.get("phoneNumber").getAsString();
        String webPageLink = jsonObj.get("webPageLink").getAsString();
        String instagramLink = jsonObj.get("instagramLink").getAsString();
        String googleMapsLink = jsonObj.get("googleMapsLink").getAsString();

        System.out.println("TENGO EMAIL" + email);
        System.out.println("TENGO DESCRIPTION" + description);
        System.out.println("TENGO PHONE" + phoneNumber);
        System.out.println("TENGO WEB" + webPageLink);
        System.out.println("TENGO INSTAGRAM" + instagramLink);
        System.out.println("TENGO GOOGLE" + googleMapsLink);

        boolean result = StoreService.editVisualStoreProfile(email, name, domicilio, tipoDeServicio, description, phoneNumber, webPageLink, instagramLink, googleMapsLink);

        Map<String, String> message = new HashMap<>();
        if (result) {
            response.status(200);
            message.put("message", "Updated successfully");
        } else {
            response.status(400);
            message.put("message", "Failed to update profile");
        }
        return gson.toJson(message);
    };

    public Route getVisualStoreProfile = (Request request, Response response) -> {
        System.out.println("ENTRO A GETVISUALPROFILE" );

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String email = jsonObj.get("email").getAsString();
        System.out.println("EMAILLL" + email);


        StoreDTO store = StoreService.getVisualStoreProfile(email);

        if (store == null) {
            response.status(400);
            return gson.toJson("Store not found");
        }

        response.status(200);
        return gson.toJson(store);
    };

    public static Route submitRatingAndComment = (Request request, Response response) -> {
        System.out.println("ENTRO A SUBMITRATINGANDCOMMENT" );

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        long userID = jsonObj.get("userID").getAsLong();
        String storeEmail = jsonObj.get("storeEmail").getAsString();
        int rating = jsonObj.get("rating").getAsInt();
        String comment = jsonObj.get("comment").getAsString();

        System.out.println("userID" + userID);
        System.out.println("STOREEMAIL" + storeEmail);
        System.out.println("RATING" + rating);
        System.out.println("COMMENT" + comment);

        long storeID = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();
        boolean result = StoreService.submitRatingAndComment(userID, storeID, rating, comment);

        Map<String, String> message = new HashMap<>();
        if (result) {
            response.status(200);
            message.put("message", "Rating and comment submitted successfully");
        } else {
            response.status(400);
            message.put("message", "Failed to submit rating and comment");
        }
        return gson.toJson(message);
    };

    public Route getAllReviews = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String storeEmail = jsonObj.get("email").getAsString();
        long storeID = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        List<Review> reviews = StoreService.fetchAllReviews(storeID);

        if (reviews == null) {
            response.status(400);
            return gson.toJson("No reviews found");
        }

        response.status(200);
        return gson.toJson(reviews);
    };

    public Route getUserReview = (Request request, Response response) -> {
        System.out.println("entrooo a getuserreview");

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        long userId = jsonObj.get("userId").getAsLong();
        String storeEmail = jsonObj.get("email").getAsString();
        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        System.out.println("USERIDDD" + userId);
        System.out.println("STOREIDDD" + storeId);
        Review review = StoreService.fetchUserReview(userId, storeId);

        if (review == null) {
            response.status(400);
            return gson.toJson("No review found");
        }

        response.status(200);
        return gson.toJson(review);
    };
    public Route deleteReview = (Request req, Response resp) -> {
        System.out.println("ENTRO A DELETEREVIEW");

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);

        long userId = jsonObj.get("userId").getAsLong();
        String storeEmail = jsonObj.get("email").getAsString();
        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        boolean result = StoreService.deleteReview(userId, storeId);
        if (result) {
            resp.status(200);
            return "Store deleted successfully";
        } else {
            resp.status(400);
            return "Failed to delete store";
        }
    };

    public Route updateReview = (Request request, Response response) -> {
        System.out.println("ENTRO A UPDATEREVIEW");

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        long userId = jsonObj.get("userID").getAsLong();
        String storeEmail = jsonObj.get("storeEmail").getAsString();
        int rating = jsonObj.get("rating").getAsInt();
        String comment = jsonObj.get("comment").getAsString();
        long storeId = Objects.requireNonNull(StoreDao.getStoreByEmail(storeEmail)).getIdStore();

        boolean result = StoreService.updateReview(userId, storeId, rating, comment);
        if (result) {
            response.status(200);
            return "Review updated successfully";
        } else {
            response.status(400);
            return "Failed to update review";
        }
    };

    public Route getStoresByRating = (Request req, Response res) -> {

        try {
            List<StoreDTO> list = StoreService.getStoresByRating();
            Gson gson = new Gson();
            res.status(200);
            return gson.toJson(list);
        } catch (Exception e) {
            res.status(500);
            e.printStackTrace();
            return "Something went wrong with the function getStoresByRating";
        }
    };

    public Route getStoreByEmail = (Request request, Response response) -> {
        Gson gson = new Gson();
        String email = gson.fromJson(request.body(), JsonObject.class).get("storeEmail").getAsString();

        Store store = StoreService.getStoreByEmail(email);
        System.out.println("STOREEE" + store);
        System.out.println("EMAIL" + store.getIdStore());

        if (store != null) {
            response.status(200);
            return gson.toJson(store.getIdStore());
        } else {
            response.status(400);
            return gson.toJson("Store not found");
        }
    };
}

