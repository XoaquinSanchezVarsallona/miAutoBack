package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Familia;
import methods.FamilyMethods;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json4s.jackson.Json$;
import services.FamilyService;
import spark.Route;


import java.util.List;

import static spark.Spark.*;

public class FamilyController {
    private final FamilyService familyService;
    private final Gson gson = new Gson();

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    };

    public Route familyDisplayed = (req, res) -> {
        String username = req.params(":username");
        try {
            res.type("application/json");
            List<Familia> result = FamilyMethods.getFamiliasOfUser(username);
            if (result.isEmpty()) {
                res.status(300);
                return "Couldn't find any familia";
            } else {
                res.status(200);
                return gson.toJson(result);
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

    public Route addFamily = (req, res) -> {
        try {
            String requestBody = req.body();

            JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);
            String apellido = jsonObj.get("apellido").getAsString();

            String username = req.params(":username");
            FamilyService.createFamily(username, apellido);
            res.status(200);
            return "Family has been created";
        } catch (Exception e) {
            res.status(500);
            return "Could not create Family";
        }
    };
    public Route deleteMember = (req, res) -> {
        try {
            String requestBody = req.body();
            JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
            String apellido = jsonObject.get("apellido").getAsString();
            String username = req.params(":username");
            boolean response = FamilyService.deleteMember(username, apellido);
            if (response) {
                res.status(200);
                return "Last member deleted, so the family has been deleted";

                // caso de que sea el ultimo miembro de una familia.
            }
            else {
                res.status(201);
                return "Member has been deleted";
                // caso de que tenga mas miembros la familia
            }
        } catch (Exception e) {
            res.status(500);
            return "Could not delete Family";
        }
    };

    public Route addMember = (req, res) -> {
        try {
            String requestBody = req.body();
            JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
            String apellido = jsonObject.get("apellido").getAsString();
            String username = req.params(":username");

            FamilyService.addMember(username, apellido);
            res.status(200);
            return "Added member to Family";
        } catch (Exception e) {
            res.status(500);
            return "Could not add User to Family";
        }
    };

}