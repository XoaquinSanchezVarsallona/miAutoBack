package controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.User;
import DTOs.UserDTO;
import services.UserService;
import spark.Route;
import utils.PasswordUtilities;

import static dao.UserDao.createUserDriver;

// Clase para definir la conexión entre la página web y la base de datos
public class UserController { // User Data Access Objects

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
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
        String email = jsonObj.get("email").getAsString();
        String password = jsonObj.get("password").getAsString();

        User user = PasswordUtilities.findUserByEmail(email);
        if (user == null) {
            res.status(404); // Not Found
            return "User not found";
        }

        // Validate the password
        boolean isValid = PasswordUtilities.passwordValidation(email, password, jsonObj.get("userType").getAsString());

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
        if (UserService.registerUser(user)) {
            res.status(201);
            return "User registered successfully!";
        } else {
            res.status(409);
            return "Email or username already exists";
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
