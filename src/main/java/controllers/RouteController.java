package controllers;

import DTOs.RouteDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UserDao;
import entities.User;
import services.RouteService;
import spark.Route;

import java.util.HashSet;
import java.util.Set;

public class RouteController {

    public static Route addRoute = (req, res) -> {
        Gson gson = new Gson();
        Long userId = Long.valueOf(req.params(":userID"));
        User user = UserDao.findUserByUserID(userId);
        if (user == null) {
            res.status(404);
            return "User not found";
        }
        try {
            String requestBody = req.body();
            JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
            String kilometres = jsonObject.get("kilometraje").getAsString();
            String duration = jsonObject.get("duration").getAsString();
            String patente = jsonObject.get("patente").getAsString();
            String date = jsonObject.get("date").getAsString();
            entities.Route route = RouteService.createRoute(patente, user, kilometres, duration, date);
            // Updateo el kilometraje del auto
            CarController.updateKilometraje(patente);
            res.status(200);
            res.type("application/json");
            return gson.toJson(route.getRouteId());
        }
        catch (Exception e) {
            res.status(500);
            return "Could not add Route";
        }

    };

    public static Route deleteRoute = (req, res) -> {
        Integer routeId = Integer.valueOf(req.params(":routeID"));
        entities.Route route = RouteService.getRouteById(routeId);
        if (route == null) {
            res.status(404);
            return "Route not found";
        }
        String kilometraje = route.getKilometraje();
        try {
            String patente = CarController.getPatenteOfRouteId(routeId);
            RouteService.deleteRoute(route);
            // Update car mileage
            CarController.substractKilometraje(patente, kilometraje);
            res.status(200);
            return "Route deleted";
        }
        catch (Exception e) {
            res.status(500);
            return "Could not delete Route";
        }
    };
    public static Route editRoute = (req, res) -> {
        Gson gson = new Gson();
        String requestBody = req.body();
        JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
        Integer routeId = jsonObject.get("routeId").getAsInt();
        entities.Route route = RouteService.getRouteById(routeId);
        if (route == null) {
            res.status(404);
            return "Route not found";
        }
        try {
            JsonObject updatesObject = jsonObject.getAsJsonObject("updates");
            String kilometres = updatesObject.get("Distance").getAsString();
            String duration = updatesObject.get("Duration").getAsString();
            String date = updatesObject.get("Date").getAsString();
            RouteService.updateRoute(route, kilometres, duration, date);
            entities.Route updatedRoute = RouteService.getRouteById(routeId);
            // Update car mileage
            String patente = CarController.getPatenteOfRouteId(routeId);
            CarController.updateKilometraje(patente);
            res.status(200);
            return gson.toJson(new RouteDTO(updatedRoute));
        }
        catch (Exception e) {
            res.status(500);
            return "Could not update Route";
        }
    };
    public static Route getRouteById = (req, res) -> {
        Integer routeId = Integer.valueOf(req.params(":routeId"));
        entities.Route route = RouteService.getRouteById(routeId);
        if (route == null) {
            res.status(404);
            return "Route not found";
        }
        Gson gson = new Gson();
        res.status(200);
        return gson.toJson(new RouteDTO(route));
    };


    public Route getRoutesOfUserByCar = (req, res) -> {
        Long userId = Long.valueOf(req.params(":userId"));
        String patente = String.valueOf(req.params(":patente"));
        User user = UserDao.findUserByUserID(userId);
        if (user == null) {
            res.status(404);
            return "User not found";
        }
        try {
            Set<entities.Route> routes = RouteService.getRoutesOfUser(user, patente);
            Gson gson = new Gson();
            Set<RouteDTO> routeDTOs = generateRouteDTOsFrom(routes);
            String json = gson.toJson(routeDTOs);
            res.type("application/json");
            return json;
        }
        catch (Exception e) {
            res.status(500);
            return "Could not get Routes";
        }
    };
    public Route getRoutesOfUser = (req, res) -> {
        Long userId = Long.valueOf(req.params(":userId"));
        User user = UserDao.findUserByUserID(userId);
        if (user == null) {
            res.status(404);
            return "User not found";
        }
        try {
            Set<entities.Route> routes = user.getRoutes();
            Gson gson = new Gson();
            Set<RouteDTO> routeDTOs = generateRouteDTOsFrom(routes);
            String json = gson.toJson(routeDTOs);
            res.type("application/json");
            return json;
        }
        catch (Exception e) {
            res.status(500);
            return "Could not get Routes";
        }
    };

    private static Set<RouteDTO> generateRouteDTOsFrom(Set<entities.Route> routes) {
        Set<RouteDTO> routeDTOs = new HashSet<>();
        for (entities.Route route : routes) {
            routeDTOs.add(new RouteDTO(route));
        }
        return routeDTOs;
    }
}
