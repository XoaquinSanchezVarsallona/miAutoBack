package controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.User;
import services.UserService;
import spark.Route;
import utils.PasswordUtilities;
import utils.JwtUtil;

import java.util.Map;

import static dao.UserDao.createUserDriver;

// Clase para definir la conexión entre la página web y la base de datos
public class UserController { // User Data Access Objects

    public Route login = (req, res) -> {
        Gson gson = new Gson();
        try {
            JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
            String email = jsonObj.get("email").getAsString();
            String password = jsonObj.get("password").getAsString();

            // Step 1: Verify user exists
            User user = PasswordUtilities.findUserByEmail(email);
            if (user == null) {
                res.status(404);
                return "User not found";
            }

            // Step 2: Validate the password
            boolean isValid = PasswordUtilities.passwordValidation(email, password, jsonObj.get("userType").getAsString());
            if (!isValid) {
                res.status(401);
                return "Incorrect password, try again.";
            }

            // Step 3: Generate and send token
            String token = JwtUtil.generateToken(user.getUsername(), user.getUserID()); // Ensure user.getUsername() is not null
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("token", token);
            responseJson.addProperty("user", gson.toJson(user)); // Serialize user object to JSON
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

        User user = createUserDriver(jsonObj.get("email").getAsString(), jsonObj.get("username").getAsString(), jsonObj.get("name").getAsString(), jsonObj.get("surname").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("domicilio").getAsString(), jsonObj.get("usertype").getAsString());

        if (UserService.registerUser(user)) {
            res.status(201);
            return "User registered successfully!";
        } else {
            res.status(409);
            return "Email or username already exists";
        }
    };

    public Route editProfile = (req, res) -> {
        //System.out.println("editProfile route hitted");

        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
        Long userId = jsonObj.get("userId").getAsLong();  // Assuming you send userId to identify the user
        String field = jsonObj.get("field").getAsString();
        String newValue = jsonObj.get("value").getAsString();

        try {
            //System.out.println("Calling UserService.updateUserField");

            // Implement the update logic. Assuming updateUserField updates the given field with the new value for the specified user
            boolean isUpdated = UserService.updateUserField(userId, field, newValue);
            if (isUpdated) {
                res.status(200);
                //return "Profile updated successfully!";
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

}


    /*public static void main(String[] args) {
        Gson gson = new Gson();
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

        post("/register", (req, res) -> {
            try {
                final EntityManager entityManager = factory.createEntityManager();

                // Creo el user
                JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);

                if (userExists(jsonObj.get("email").getAsString(), jsonObj.get("username").getAsString(), entityManager)) {
                    res.status(409);
                    return "Email o username ya existe";
                }

                User user = createUserDriver(jsonObj.get("email").getAsString(), jsonObj.get("username").getAsString(), jsonObj.get("name").getAsString(), jsonObj.get("surname").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("domicilio").getAsString(), jsonObj.get("usertype").getAsString());
                RegisterRequest.saveInBd(user, entityManager);
                entityManager.close();


                res.status(201);
                return "Usuario registrado exitosamente!";
            } catch (Exception e) {
                res.status(500);
                return "An error occurred while registering the user: " + e.getMessage();
            }
        });

        // Ruta para un posteo del login
        post("/login", (req, res) -> {
            try {
                final EntityManager entityManager = factory.createEntityManager();

                // tengo el JSON string
                String requestBody = req.body();

                // lo parseo a un objeto json al string
                JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);
                String email = jsonObj.get("email").getAsString();
                String password = jsonObj.get("password").getAsString();
                String userType = jsonObj.get("userType").getAsString();

                User user = PasswordUtilities.findUserByEmail(email, entityManager);
                if (user == null) {
                    res.status(404); // Not Found
                    return "User not found";
                }


                // Validate the password
                boolean isValid = PasswordUtilities.passwordValidation(jsonObj.get("email").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("userType").getAsString(),entityManager);
                entityManager.close();

                if (isValid) {
                    res.status(200);
                    return "User logged in successfully!";
                } else {
                    if (user == null) {
                        res.status(404);
                        return "User not found";
                    } else if (!user.getUserType().equalsIgnoreCase(userType)) {
                        res.status(401);
                        return "Incorrect account type";
                    } else {
                        res.status(401);
                        return "Incorrect password, try again.";
                    }
                }


            } catch (Exception e) {
                res.status(500); // 500 Internal Server Error
                return "An error occurred";
            }
        });
    }

    // Previo al login o al registro, el usuario debería poder decidir si entrar como userDriver o userService.

    // Función para login, dado un email y un password busca en la bdd y si lo encuentra, deja iniciar sesión.
    // GetUserByEmailAndPassword


    // Función para registrar un usuario, una vez rellenados todos los parámetros pedidos, guardo al usuario en la bdd.

*/
