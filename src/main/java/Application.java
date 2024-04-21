
import controllers.*;


import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {

        UserController userController = new UserController();
        FamilyController familyController = new FamilyController();
        CarController carController = new CarController();

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

        get("/user/:email", userController.findUserByEmail);
        get("/family/:idFamilia", familyController.findFamilyById);
        get("/vehicles/family/:familyID", familyController.vehiclesOfFamily);
        get("/car/:patente", carController.findCarByPatente);
        post("/car/:familyId/addVehicle", carController.createCar);

        get("/user/:username", familyController.familyDisplayed);
        post("/user/:username/addFamily", familyController.addFamily);
        get("/user/:username/delMember", familyController.deleteMember);
        get("/user/:username/addMember", familyController.addMember);
        post("/user/:username/joinToFamily", familyController.joinToFamily);

        delete("/family/:surname", FamilyController.deleteFamily);
        put("/family/:surname", familyController.updateSurname);

        post("/editProfile", userController.editProfile);
        post("/validateToken", userController.validateToken);

        post("alerts/add", AlertController.addAlertToFamily);
        get("alerts/family/:familyApellido", AlertController.getAlertsOfFamily);

    }}
