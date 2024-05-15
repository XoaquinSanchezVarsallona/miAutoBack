
import controllers.*;
import spark.Spark;


import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {

        UserController userController = new UserController();
        FamilyController familyController = new FamilyController();
        CarController carController = new CarController();
        StoreController storeController = new StoreController();
        RouteController routeController = new RouteController();

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

        post("/vehicles/family/:familyId", familyController.vehiclesOfFamily);
        get("/alertasss/family/:familyApellido", AlertController.getAlertsOfFamily);
        post("/alertas/add", AlertController.addAlertToFamily);
        delete("/alerts/:idAlert", AlertController.deleteAlert);


        post("/login", userController.login);
        post("/register", userController.register);

        get("/user/:email", userController.findUserByEmail);
        post("/user/:userId", userController.findUserById);
        get("/family/:idFamilia", familyController.findFamilyById);
        get("/car/:patente", carController.findCarByPatente);
        post("/car/:familyId/addVehicle", carController.createCar);
        delete("/car/:patente/deleteCar", carController.deleteCar);

        get("/user/:username", familyController.familyDisplayed);
        post("/user/:username/addFamily", familyController.addFamily);
        get("/user/:username/delMember", familyController.deleteMember);
        get("/user/:username/addMember", familyController.addMember);
        post("/user/:username/joinToFamily", familyController.joinToFamily);
        get("/user/:userId/vehicle/:patente/routes", routeController.getRoutesOfUserByCar);
        get("/user/:userId/routes", routeController.getRoutesOfUser);

        delete("/family/:surname", FamilyController.deleteFamily);
        put("/family/:surname", familyController.updateSurname);
        post("/family/:familyId/getUsers", familyController.getMembers);

        post("/editProfile", userController.editProfile);
        post("/validateToken", userController.validateToken);

        post("/editCarProfile" , carController.editCarProfile);

        post("/fetchAllStores", storeController.getAllStores);
        post("stores/addStore", storeController.addStore);
        post("stores/fetchStores", storeController.getStores);
        delete("/store/:storeEmail/deleteStore", storeController.deleteStore);
        post("/editStoreProfile", storeController.editStoreProfile);
        // post("editVisualStoreProfile", storeController.editVisualStoreProfile); 

        // Route
        post("/route/:userID/addRoute", RouteController.addRoute);
        delete("/route/:routeID/deleteRoute", RouteController.deleteRoute);
        post("/route/editRoute", RouteController.editRoute);
        post("/route/:routeId/getRoute", RouteController.getRouteById);
    }}
