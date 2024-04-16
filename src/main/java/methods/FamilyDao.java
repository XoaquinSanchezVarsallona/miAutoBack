package methods;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.austral.ing.lab1.Familia;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json4s.jackson.Json$;


import java.util.List;

import static spark.Spark.*;

public class FamilyDao {
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        Gson gson = new Gson();

        port(8082);

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

        get("/user/:username", (req, res) -> {
            String username = req.params(":username");
            try {
                res.type("application/json");
                List<Familia> result = FamilyMethods.getFamiliasOfUser(sessionFactory.openSession(), username);
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
        });

        get("/user/:username/addFamily", (req, res) -> {
            try {
                String requestBody = req.body();

                JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);
                String apellido = jsonObj.get("apellido").getAsString();

                String username = req.params(":username");
                FamilyMethods.CreateFamily(username, apellido);
                res.status(200);
                return "Family has been created";
            } catch (Exception e) {
                res.status(500);
                return "Could not create Family";
            }
        });

    }
/*        get( "/user/:username/removeFrom" , (req, res) -> {
            String username = req.params(":username");
            try{
                String requestBody = req.body();
                JsonObject jsonObj = gson.fromJson(requestBody, JsonObject.class);
                String apellido = jsonObj.get("apellido").getAsString();
                return ;
            }
            catch (Exception e){
                res.status(500);
                return "Something went worng";
            }
        });
    }

 */

}
