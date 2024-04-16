import com.google.gson.Gson;
import controllers.FamilyController;
import dao.FamilyDao;
import dao.UserDao;
import controllers.UserController;
import services.FamilyService;
import services.UserService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        Gson gson = new Gson();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
        EntityManager entityManager = factory.createEntityManager();

        UserDao userDao = new UserDao(entityManager);
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);

        FamilyDao familyDao = new FamilyDao(entityManager);
        FamilyService familyService = new FamilyService(familyDao);
        FamilyController familyController = new FamilyController(familyService);

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

        post("/login", userController.login);
        post("/register", userController.register);

        //get("/user/:username", familyController.familyDisplayed);
        //get("/user/:username/addFamily", familyController.addFamily);
        //get("/user/:username/delMember", familyController.deleteMember);
        //get("/user/:username/addMember", familyController.addMember);
        post("/editProfile", userController.editProfile);
}}
