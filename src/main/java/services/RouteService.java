package services;

import dao.RouteDao;
import entities.Route;
import entities.User;

import java.util.Set;

public class RouteService {
    public static void createRoute(String patente, User user, String kilometres, String duration, String date) {
        RouteDao.createRoute(patente, user, kilometres, duration, date);
    }

    public static Set<Route> getRoutesOfUser(User user) {
        return RouteDao.getRoutesOfUser(user);
    }
}
