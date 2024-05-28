package controllers;

import DTOs.PapersToDisplay;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dao.UserDao;
import entities.Alert;
import entities.Car;
import entities.Familia;
import entities.User;
import services.CarService;
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

    public static Route setAsRead = (req, res) -> {
        try {
            Long idAlert = Long.parseLong(req.params(":idAlert"));
            AlertService.setAsRead(idAlert);
            res.status(200);
            return "Alert set as read";
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong while trying to set alert as read";
        }
    };
    public static Route setAsUnread = (req, res) -> {
        try {
            Long idAlert = Long.parseLong(req.params(":idAlert"));
            AlertService.setAsUnread(idAlert);
            res.status(200);
            return "Alert set as unread";
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong while trying to set alert as unread";
        }
    };
    public static Route countUnreadAlertsOfFamily = (req, res) -> {
        String familyApellido = req.params(":familyApellido");
        try {
            int count = AlertService.countUnreadAlertsOfFamily(familyApellido);
            res.status(200);
            return count;
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };
    public static Route countUnreadAlertsOfFamilyId = (req, res) -> {
        int idFamilia = Integer.parseInt(req.params(":idFamilia"));
        String familyApellido = FamilyService.getFamiliaById(idFamilia).getApellido();
        try {
            int count = AlertService.countUnreadAlertsOfFamily(familyApellido);
            res.status(200);
            return count;
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

    public String displayPapers(String username, String patente) {
        try {
            User user = UserDao.findUserByUsername(username);
            Car car = CarService.getCarByPatente(patente);

            assert user != null;
            assert car != null;
            PapersToDisplay papers = AlertService.getPapersFromUser(user, car);

            return gson.toJson(papers);

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static Route sendAlertToFamilies = (req, res) -> {
        String username = req.params(":username");
        String patente = req.params(":patente");

        try {
            User user = UserDao.findUserByUsername(username);
            Car car = CarService.getCarByPatente(patente);

            AlertService.alertAllFamilies(car,user);

            return "OK";

        } catch (Exception e) {

            return "Error message: " + e.getMessage();
        }
    };
}
