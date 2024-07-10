package controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UserDao;
import entities.User;
import DTOs.UserDTO;
import services.UserService;
import spark.Route;
import utils.PasswordUtilities;
import utils.JwtUtil;

import java.util.Map;
import java.util.Objects;

public class UserController {

    public Route findUserByEmail = (req, res) -> {
        Gson gson = new Gson();
        String email = req.params("email");
        User user = PasswordUtilities.findUserByEmail(email);
        if (user == null) {
            res.status(404); // Not Found
            return "User not found";
        }
        res.status(200);
        UserDTO userDTO = new UserDTO(user); // Me llevo al front solo lo que necesito del user
        return gson.toJson(userDTO);
    };

    public Route login = (req, res) -> {
        Gson gson = new Gson();
        try {
            JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
            String email = jsonObj.get("email").getAsString();
            String password = jsonObj.get("password").getAsString();
            // Step 1: Verify user exists //
            User user = PasswordUtilities.findUserByEmail(email);

            if (user == null) {
                res.status(404);
                return "User not found";
            }

            // Step 2: Validate the password
            boolean isValid = PasswordUtilities.passwordValidation(email, password, jsonObj.get("userType").getAsString());
            if (!isValid) {
                res.status(401);
                return "Incorrect password, please try again.";
            }

            // Step 3: Generate and send token
            String token = JwtUtil.generateToken(user.getUsername(), user.getUserID()); // Ensure user.getUsername() is not null
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("token", token);
            responseJson.addProperty("user", gson.toJson(new UserDTO(user))); // Serialize user object to JSON
            res.status(200);
            return responseJson.toString();

        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            res.status(500);
            return "Internal Server Error: " + e.getMessage();
        }
    };



    public Route register = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);

        User user = UserDao.createUserDriver(jsonObj.get("email").getAsString(), jsonObj.get("username").getAsString(), jsonObj.get("name").getAsString(), jsonObj.get("surname").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("longitud").getAsDouble(), jsonObj.get("latitud").getAsDouble(), jsonObj.get("usertype").getAsString());
        if (UserService.registerUser(user)) {
            res.status(201);
            return "User registered successfully!";
        } else {
            res.status(409);
            return "Email or username already exists";
        }
    };

    public Route editProfile = (req, res) -> {

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
        Long userId = jsonObj.get("userId").getAsLong();
        String field = jsonObj.get("field").getAsString();
        String newValue = jsonObj.get("value").getAsString();

        if (Objects.equals(field, "username")) {
            if (UserService.usernameExists(newValue)) {
                res.status(409);
                return "Username already exists";
            }
        }

        try {
            boolean isUpdated = UserService.updateUserField(userId, field, newValue);
            if (isUpdated) {
                res.status(200);
                return jsonObj.toString();
            } else {
                res.status(400);
                return "Failed to update profile";
            }
        } catch (Exception e) {
            res.status(500);
            return "Internal Server Error: " + e.getMessage();
        }
    };

    public Route validateToken = (req, res) -> {
        System.out.println("busco tokeeeeen");
        String token = req.headers("Authorization").replace("Bearer ", "");
        Map<String, String> userInfo = JwtUtil.validateToken(token);
        if (userInfo == null) {
            res.status(401);
            return "{\"message\":\"Invalid or expired token\"}";
        } else {
            res.type("application/json");
            return new Gson().toJson(userInfo);
        }
    };

    public Route findUserById = (req, res) -> {
        System.out.println("Entrooo a finduserbyid");

        Gson gson = new Gson();
        Long userId = Long.parseLong(req.params("userId"));
        User user = UserService.findUserById(userId);
        if (user == null) {
            res.status(404);
            return "User not found";
        }
        res.status(200);
        UserDTO userDTO = new UserDTO(user);
        System.out.println("el siguiente usuario se obtuvo");
        System.out.println(userDTO.email);
        System.out.println(userDTO);
        return gson.toJson(userDTO);
    };
    public Route deleteUser = (req, res) -> {
        Long userId = Long.parseLong(req.params("userId"));
        boolean isDeleted = UserDao.deleteUser(userId);
        if (isDeleted) {
            res.status(200);
            return "User deleted successfully";
        } else {
            res.status(404);
            return "User not found";
        }
    };
}
