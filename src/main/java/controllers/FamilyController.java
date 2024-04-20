package controllers;

import DTOs.FamiliaDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Familia;
import services.FamilyService;
import spark.Route;
import utils.JwtUtil;


import java.util.List;
import java.util.Objects;

public class FamilyController {
    private final Gson gson = new Gson();

    public Route findFamilyById = (req, res) -> {
        int idFamilia = Integer.parseInt(req.params(":idFamilia"));
        try {
            res.type("application/json");
            Familia result = FamilyService.getFamiliaById(idFamilia);
            if (result == null) {
                res.status(404);
                return "Couldn't find familia with id " + idFamilia;
            } else {
                res.status(200);
                FamiliaDTO familiaDTO = new FamiliaDTO(result);
                return gson.toJson(familiaDTO);
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

    public Route familyDisplayed = (req, res) -> {
        String username = req.params(":username");
        try {
            res.type("application/json");
            List<Familia> result = FamilyService.getFamiliasOfUser(username);
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
            String apellido = jsonObj.get("surname").getAsString();
            String username = req.params(":username");
            try { FamilyService.createFamily(username, apellido); } catch (IllegalArgumentException e) {
                res.status(400);
                return "Family already exists!";
            }
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
                // caso de que sea el último miembro de una familia.
            }
            else {
                res.status(201);
                return "Member has been deleted";
                // caso de que tenga más miembros la familia
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

    public static Route deleteFamily = (req, res) -> {
        try {
            System.out.println("route deleteFamily hit");
            String surname = req.params(":surname");
            System.out.println("surname" + surname);
            String token = req.headers("Authorization").replace("Bearer ", "");
            Long userID = Long.valueOf(Objects.requireNonNull(JwtUtil.validateToken(token)).get("userId"));
            boolean result = FamilyService.deleteFamily(userID, surname);
            System.out.println("result: " + result);
            if (result) {
                res.status(200);
                return "Family deleted successfully";
            } else {
                res.status(404);
                return "Family not found";
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return "Invalid id parameter";
        }
    };

    public Route updateSurname = (req, res) -> {
        try {
            System.out.println("UPDATE SURNAME ROUTE FOUND ");

            String familySurname = req.params(":surname");
            JsonObject jsonObject = gson.fromJson(req.body(), JsonObject.class);
            String newSurname = jsonObject.get("surname").getAsString();
            System.out.println("surname " + familySurname);
            System.out.println("newSurname " + newSurname);

            FamilyService.updateSurname(familySurname, newSurname);
            res.status(200);
            return "Surname updated successfully to " + newSurname;
        } catch (Exception e) {
            res.status(500);
            return "Could not update surname";
        }
    };
    public Route vehiclesOfFamily = (req, res) -> {
        try {
            int familyID = Integer.parseInt(req.params(":familyID"));
            res.type("application/json");
            List<String> result = FamilyService.getVehiclesOfFamily(familyID);
            if (result.isEmpty()) {
                res.status(300);
                return "Couldn't find any vehicle";
            } else {
                res.status(200);
                return gson.toJson(result);
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };
}