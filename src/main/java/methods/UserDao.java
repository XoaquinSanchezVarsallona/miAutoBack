package methods;
import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.austral.ing.lab1.User;

import javax.persistence.*;

// Clase para definir la conexión entre la página web y la base de datos
public class UserDao { // User Data Access Objects
    public static void main(String[] args) {
        Gson gson = new Gson();
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

        port(9002);

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

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

                User user = LoginRequest.findUserByEmail(email, entityManager);
                if (user == null) {
                    res.status(404); // Not Found
                    return "User not found";
                }


                // Validate the password
                boolean isValid = LoginRequest.passwordValidation(jsonObj.get("email").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("userType").getAsString(),entityManager);
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

    private static User createUserDriver(String email, String username, String name, String surname, String password, String domicilio, String userType) {
        return new User(email, username, name, surname, password, domicilio, userType);
    }

    public static boolean userExists(String email, String username, EntityManager entityManager) {
        String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email OR u.username = :username";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("email", email);
        query.setParameter("username", username);

        try {
            //si hay alguno, existe
            Long count = query.getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            //no existen usuarios con ese username o mail
            return false;
        }
    }


    // Previo al login o al registro, el usuario debería poder decidir si entrar como userDriver o userService.

    // Función para login, dado un email y un password busca en la bdd y si lo encuentra, deja iniciar sesión.
    // GetUserByEmailAndPassword


    // Función para registrar un usuario, una vez rellenados todos los parámetros pedidos, guardo al usuario en la bdd.


}