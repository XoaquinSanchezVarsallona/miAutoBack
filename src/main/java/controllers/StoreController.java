package controllers;

import DTOs.StoreDTO;
import dao.StoreDao;
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

public class StoreController {
    public Route addStore = (Request request, Response response) -> {
        System.out.println("ENTRO A ADDSTORE");

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

        System.out.println("USUUUARIO" + user);

        Store store = new Store(storeEmail, storeName, domicilio, tipoDeServicio);
        store.setUser(user);
        StoreDao.saveStore(store);

        System.out.println("NUEVOOO STORE" + store);

        System.out.println("STORE AGREGADOOO" + store);
        System.out.println("User's stores: " + user.getStores());


        System.out.println("se agrego succesfullyyyyy");
        response.status(200);
        return "Store added successfully";
    };

    public Route getStores = (Request request, Response response) -> {
        System.out.println("ENTRO A GETSTORE");
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        String email = jsonObj.get("email").getAsString();
        System.out.println("TENGO MAIL: " + email);
        List<StoreDTO> stores = StoreService.fetchStores(email);
        System.out.println("TENGO STORES: " + stores);

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
}

