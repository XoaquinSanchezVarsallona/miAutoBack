package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.StoreDao;
import entities.Experience;
import services.ExperienceService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.security.Timestamp;
import java.util.List;
import java.util.Objects;

public class ExperienceController {
    public Route getExperiences = (Request request, Response response) -> {
        Gson gson = new Gson();

        List<Experience> experiences = ExperienceService.getExperiences();

        response.status(200);
        return gson.toJson(experiences);
    };

    public Route deleteExperience = (Request request, Response response) -> {
        Gson gson = new Gson();
        long experienceId = Long.parseLong(request.params(":experienceId"));

        ExperienceService.deleteExperience(experienceId);

        response.status(200);
        return "";
    };

    public Route submitExperience = (Request request, Response response) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(request.body(), JsonObject.class);

        Long userId = jsonObj.get("idUser").getAsLong();
        String storeName = jsonObj.get("nameStore").getAsString();
        Long storeId = Objects.requireNonNull(StoreDao.getStoreByName(storeName)).getIdStore();
        String patente = jsonObj.get("idAuto").getAsString();
        String description = jsonObj.get("description").getAsString();
        int rating = jsonObj.get("rating").getAsInt();

        System.out.println("useeeerId: " + userId);
        System.out.println("storeId: " + storeId);
        System.out.println("patente: " + patente);
        System.out.println("description: " + description);
        System.out.println("rating: " + rating);

        ExperienceService.createExperience(userId, storeId, patente, description, rating);

        response.status(200);
        return "";
    };
}
