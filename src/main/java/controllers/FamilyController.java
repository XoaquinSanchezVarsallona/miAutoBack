package controllers;

import DTOs.FamiliaDTO;
import DTOs.UserDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Familia;
import entities.User;
import extras.IncorrectPasswordException;
import services.FamilyService;
import spark.Route;
import utils.JwtUtil;


import javax.persistence.NoResultException;
import java.util.ArrayList;
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
    // hola

    public Route familyDisplayed = (req, res) -> {
        String username = req.params(":username");
        try {
            res.type("application/json");
            List<Familia> result = FamilyService.getFamiliasOfUser(username);
            if (result.size() == 1) {
                res.status(300);
                return "You only have your family";
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
            JsonObject jsonObj = (JsonObject)this.gson.fromJson(requestBody, JsonObject.class);
            String apellido = jsonObj.get("surname").getAsString();
            String password = jsonObj.get("password").getAsString();
            String username = req.params(":username");

            try {
                FamilyService.createFamily(username, apellido, password);
                List<Familia> familias = FamilyService.getFamiliasOfUser(username);
                res.status(200);
                return this.gson.toJson(this.getIdOfFamilias(familias));
            } catch (IllegalArgumentException e) {
                res.status(400);
                return "Family already exists!";
            }
        } catch (Exception e) {
            res.status(500);
            return "Could not create Family: " + e.getMessage();
        }
    };

    public Route joinToFamily = (req, res) -> {
        try {
            String requestBody = req.body();
            JsonObject jsonObject = (JsonObject)this.gson.fromJson(requestBody, JsonObject.class);
            String apellido = jsonObject.get("surname").getAsString();
            String password = jsonObject.get("password").getAsString();
            String username = req.params(":username");
            FamilyService.joinToFamily(username, apellido, password);
            List<Familia> familias = FamilyService.getFamiliasOfUser(username);
            res.status(200);
            return this.gson.toJson(this.getIdOfFamilias(familias));
        } catch (NoResultException var9) {
            System.out.println(var9.getMessage());
            res.status(404);
            return "Family doesn't exist";
        } catch (IllegalArgumentException var10) {
            System.out.println(var10.getMessage());
            res.status(400);
            return "You are already in that family";
        } catch (IncorrectPasswordException var11) {
            System.out.println(var11.getMessage());
            res.status(401);
            return "Incorrect password";
        } catch (Exception var12) {
            System.out.println(var12.getMessage());
            res.status(500);
            return var12.getMessage();
        }
    };

    public Route getMembers = (req, res) -> {
        try {
            String requestBody = req.body();
            JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
            int familyId = jsonObject.get("familyId").getAsInt();
            res.type("application/json");
            List<User> result = FamilyService.getMembersOfFamily(familyId);
            List<UserDTO> userDTOs = getDTOVersionOfList(result);
            if (result.isEmpty()) {
                res.status(300);
                return "Couldn't find any member";
            } else {
                res.status(200);
                return gson.toJson(userDTOs);
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

    private List<UserDTO> getDTOVersionOfList(List<User> list) {
        List<UserDTO> result = new ArrayList<>();
        for (User u : list) {
            result.add(new UserDTO(u));
        }
        return result;
    }

    private List<Integer> getIdOfFamilias(List<Familia> familias) {
        List<Integer> ids = new ArrayList<>();
        for (Familia familia : familias) {
            ids.add(familia.getFamiliaId());
        }
        return ids;
    }

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
            String surname = req.params(":surname");
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
            int familyID = Integer.parseInt(req.params(":familyId"));
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