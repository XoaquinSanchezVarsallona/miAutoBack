package methods;
import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.austral.ing.lab1.UserDriver;

// Clase para definir la conexión entre la página web y la base de datos
public class UserDao { // User Data Access Objects
    public static void main(String[] args) {
        Gson gson = new Gson();

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
                // Creo el user
                JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
                UserDriver user = createUserDriver(jsonObj.get("email").getAsString(), jsonObj.get("username").getAsString(), jsonObj.get("name").getAsString(), jsonObj.get("surname").getAsString(), jsonObj.get("password").getAsString(), jsonObj.get("domicilio").getAsString());

                // Esto persiste el objeto en la base de datos
                RegisterRequest.saveInBd(user);

                res.status(201);
                return "User registered successfully!";
            } catch (Exception e) {
                res.status(500);
                return "An error occurred while registering the user: " + e.getMessage();
            }
        });

        //puerto que spark escucha
        //ruta para un posteo del login
        post("/login", (req, res) -> {
            try {
                System.out.println(req.body());
                System.out.println(res);
                // tengo el JSON string
                String requestBody = req.body();

                // lo parseo a un objeto json al string
                JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);

                // extraigo mail y password
                String email = jsonObj.get("email").getAsString();
                String password = jsonObj.get("password").getAsString();

                System.out.println(email);
                System.out.println(password);

                // Validate the password
                boolean isValid = LoginRequest.passwordValidation(email, password);
                //boolean isValid = true;


                // si el usuario existe y los datos son correctos.
                if (isValid) {
                    res.status(200); //manda respuesta positiva al frontend
                    System.out.println("se loggineeooooooooooooooooo");
                    return "User logged in successfully!";
                } else {
                    res.status(401); // 401 Unauthorized
                    return "User not found or password incorrect";
                }
            } catch (Exception e) {
                res.status(500); // 500 Internal Server Error
                return "An error occurred";
            }
        });
    }

    private static UserDriver createUserDriver(String email, String username, String name, String surname, String password, String domicilio) {
        return new UserDriver(email, username, name, surname, password, domicilio);
    }

    // Previo al login o al registro, el usuario debería poder decidir si entrar como userDriver o userService.

    // Función para login, dado un email y un password busca en la bdd y si lo encuentra, deja iniciar sesión.
    // GetUserByEmailAndPassword


    // Función para registrar un usuario, una vez rellenados todos los parámetros pedidos, guardo al usuario en la bdd.


}