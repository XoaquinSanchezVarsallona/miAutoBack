package services;

import dao.RouteDao;
import entities.Route;
import entities.User;

import java.util.Set;

public class RouteService {
    public static void createRoute(String patente, User user, String kilometres, String duration, String date) {
        RouteDao.createRoute(patente, user, kilometres, duration, date);
    }

    public static Set<Route> getRoutesOfUser(User user, String patente) {
        return RouteDao.getRoutesOfUser(user, patente);
    }

    public static Set<Route> getRoutesOfUser(User user) {
        return RouteDao.getRoutesOfUser(user);
    }

    public static void deleteRoute(Route route) {
        RouteDao.deleteRoute(route);
    }

    public static Route getRouteById(Integer routeId) {
        return RouteDao.getRouteById(routeId);
    }

    public static void updateRoute(Route route, String kilometres, String duration, String date) {
        RouteDao.updateRoute(route, kilometres, duration, date);
    }
}
