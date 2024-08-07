import controllers.*;
import static spark.Spark.*;

public class  Application {

    public static void main(String[] args) {

        UserController userController = new UserController();
        FamilyController familyController = new FamilyController();
        CarController carController = new CarController();
        StoreController storeController = new StoreController();
        RouteController routeController = new RouteController();
        NotificationController notificationController = new NotificationController();
        ExperienceController experienceController = new ExperienceController();

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

        // Before each request, set the response header to allow CORS
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Mercado Pago
        post("/mercadopago/createPreference", PaymentController.createPreference);

        // Alert
        post("/alertas/family/:familyApellido", AlertController.getAlertsOfFamily);
        post("/alertas/add", AlertController.addAlertToFamily);
        delete("/alerts/:idAlert", AlertController.deleteAlert);
        post("/alertas/alertAccident/:username/:patente", AlertController.sendAlertToFamilies);
        post("/alerts/setAsRead/:idAlert", AlertController.setAsRead);
        post("/alerts/setAsUnread/:idAlert", AlertController.setAsUnread);
        post("/alerts/unreadAlerts/:familyApellido", AlertController.countUnreadAlertsOfFamily);
        post("/alerts/unreadAlertsWithId/:idFamilia", AlertController.countUnreadAlertsOfFamilyId);
        post("/displayPapers", AlertController.displayPapers);

        // User
        post("/login", userController.login);
        post("/register", userController.register);
        post("/editProfile", userController.editProfile);
        post("/validateToken", userController.validateToken);
        get("/user/:email", userController.findUserByEmail);
        post("/user/:userId", userController.findUserById);
        post("/findUserById/:userId", userController.findUserById);
        delete("/user/:userId/deleteUser", userController.deleteUser);

        // Car
        get("/car/:patente", carController.findCarByPatente);
        post("/car/:familyId/addVehicle", carController.createCar);
        delete("/car/:patente/deleteCar", carController.deleteCar);
        post("/editCarProfile" , carController.editCarProfile);
        post("/vehicles/family/:familyId", familyController.vehiclesOfFamily);
        post("/car/:patente/getInfo", carController.getCarInformation);

        // Family
        get("/family/:idFamilia", familyController.findFamilyById);
        get("/user/:username", familyController.familyDisplayed);
        post("/user/:username/addFamily", familyController.addFamily);
        get("/user/:username/delMember", familyController.deleteMember);
        get("/user/:username/addMember", familyController.addMember);
        post("/user/:username/joinToFamily", familyController.joinToFamily);
        delete("/family/:surname", FamilyController.deleteFamily);
        put("/family/:surname", familyController.updateSurname);
        post("/family/:familyId/getUsers", familyController.getMembers);

        // Store
        post("/fetchAllStores", storeController.getAllStores);
        post("stores/addStore", storeController.addStore);
        post("stores/fetchStores", storeController.getStores);
        delete("/store/:storeEmail/deleteStore", storeController.deleteStore);
        post("/editStoreProfile", storeController.editStoreProfile);
        post("/editVisualStoreProfile", storeController.editVisualStoreProfile);
        post("/getVisualStoreProfile", storeController.getVisualStoreProfile);
        post("/getStoreByEmail", storeController.getStoreByEmail);
        post("getStoresByRating", storeController.getStoresByRating);
        post("/getStoreById", storeController.getStoreById);

        // Route
        post("/route/:userID/addRoute", RouteController.addRoute);
        delete("/route/:routeID/deleteRoute", RouteController.deleteRoute);
        post("/route/editRoute", RouteController.editRoute);
        post("/route/:routeId/getRoute", RouteController.getRouteById);
        get("/route/:patente", RouteController.getRoutesOfCar);
        get("/user/:userId/vehicle/:patente/routes", routeController.getRoutesOfUserByCar);
        get("/user/:userId/routes", routeController.getRoutesOfUser);

        // Image
        post("/saveImage", ImageController.saveImage);

        // Review
        post("/submitRatingAndComment", StoreController.submitRatingAndComment);
        post("/getReviews", storeController.getAllReviews);
        post("/getUserReview", storeController.getUserReview);
        post("/DeleteReview", storeController.deleteReview);
        post("/UpdateReview", storeController.updateReview);

        // Notification
        post("/createNotification", notificationController.createNotification);
        post("/fetchNotifications", notificationController.fetchNotifications);
        post("/fetchNotificationsByUserId", notificationController.fetchNotificationsByUserId);
        post("/deleteNotification", notificationController.deleteNotification);
        post("/deleteNotificationFromDescription", notificationController.deleteNotificationFromDescription);

        // Experiences
        post("/getExperiences", experienceController.getExperiences);
        delete("/deleteExperience/:experienceId", experienceController.deleteExperience);
        post("/submitExperience", experienceController.submitExperience);
    }}
