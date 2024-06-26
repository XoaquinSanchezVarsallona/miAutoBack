package services;

import dao.RouteDao;
import entities.Route;
import entities.User;

import java.util.Set;

public class RouteService {
    public static Route createRoute(String patente, User user, String kilometres, String duration, String date) {
        return RouteDao.createRoute(patente, user, kilometres, duration, date);
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

    public static Set<Route> getRoutesOfCar(String patente) {
        return RouteDao.getRoutesOfCar(patente);
    }
}
