package controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.User;
import services.UserService;
import spark.Route;
import utils.PasswordUtilities;

import static dao.UserDao.createUserDriver;

// Clase para definir la conexión entre la página web y la base de datos
public class UserController { // User Data Access Objects
    private UserService userService;
    private Gson gson;

    public UserController(UserService userService) {
        this.userService = userService;
        this.gson = new Gson();
    }

    public Route getUserByEmail = (req, res) -> {
        String email = req.params(":email");
        User user = userService.getUserByEmail(email);
        if (user == null) {
            res.status(404); // Not Found
            return "User not found";
        } else {
            res.status(200);
            return gson.toJson(user);
        }
    };

    public Route login = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
        String email = jsonObj.get("email").getAsString();
        String password = jsonObj.get("password").getAsString();

        User user = PasswordUtilities.findUserByEmail(email);
        if (user == null) {
            res.status(404); // Not Found
            return "User not found";
        }

        // Validate the password
        boolean isValid = PasswordUtilities.passwordValidation(jsonObj.get("email").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("userType").getAsString());

        if (isValid) {
            res.status(200);
            return "User logged in successfully!";
        } else {
            res.status(401);
            return "Incorrect password, try again.";
        }
    };

    public Route register = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);

        User user = createUserDriver(jsonObj.get("email").getAsString(), jsonObj.get("username").getAsString(), jsonObj.get("name").getAsString(), jsonObj.get("surname").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("domicilio").getAsString(), jsonObj.get("usertype").getAsString());

        if (userService.registerUser(user)) {
            res.status(201);
            return "User registered successfully!";
        } else {
            res.status(409);
            return "Email or username already exists";
        }
    };
}
