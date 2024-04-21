package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entities.Alert;
import entities.Familia;
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
        System.out.println("ENTRO EN RUTA GETALERTS");
        String familyApellido = req.params(":familyApellido");

        try {
            res.type("application/json");
            List<Alert> alerts = AlertService.getAlertsByFamilyApellido(familyApellido);
            for (Alert alerta : alerts){
                System.out.println("ALERTASSS :" + alerta.getMessage());
            }
            if (alerts.isEmpty()) {
                res.status(300);
                return "Couldn't find any familia";
            } else {
                res.status(200);
                System.out.println("devolviendo alertas");

                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String json = gson.toJson(alerts);
                System.out.println(json);
                return json;
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

    public static Route deleteAlert = (req, res) -> {
        try {
            Long idAlert = Long.parseLong(req.params(":idAlert"));
            boolean isDeleted = AlertService.deleteAlert(idAlert);
            if (!isDeleted) {
                res.status(404);
                return "Couldn't find alert with id " + idAlert;
            }
            res.status(200);
            return "Alert deleted successfully";
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };
}
