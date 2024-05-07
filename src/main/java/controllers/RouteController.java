package controllers;

import DTOs.RouteDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UserDao;
import entities.User;
import services.RouteService;
import spark.Route;

import java.util.ArrayList;
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
            RouteService.createRoute(patente, user, kilometres, duration, date);
            res.status(200);
            return "Route added successfully";
        }
        catch (Exception e) {
            res.status(500);
            return "Could not add Route";
        }
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

    private Set<RouteDTO> generateRouteDTOsFrom(Set<entities.Route> routes) {
        Set<RouteDTO> routeDTOs = new HashSet<>();
        for (entities.Route route : routes) {
            routeDTOs.add(new RouteDTO(route));
        }
        return routeDTOs;
    }
}