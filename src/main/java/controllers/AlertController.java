package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Alert;
import spark.Route;
import services.FamilyService;
import java.util.List;
import services.AlertService;

public class AlertController {
    private static final Gson gson = new Gson();

    public static Route addAlertToFamily = (req, res) -> {
        try {
            String requestBody = req.body();
            JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);
            String message = jsonObj.get("message").getAsString();
            String apellido = jsonObj.get("apellido").getAsString();
            String username = jsonObj.get("username").getAsString();

            FamilyService.addAlertToFamily(message, apellido, username);
            res.status(200);
            return "Alert added to family";
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

    public static Route getAlertsOfFamily = (req, res) -> {
        res.type("application/json");
        String familyApellido = req.params(":familyApellido");
        List<Alert> alerts = AlertService.getAlertsByFamilyApellido(familyApellido);
        return new Gson().toJson(alerts);
    };
}
