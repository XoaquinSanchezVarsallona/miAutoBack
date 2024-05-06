package dao;

import entities.User;
import entities.Route;

import javax.persistence.EntityManager;
import java.util.Set;

public class RouteDao {
    public static void createRoute(String patente, User user, String kilometres, String duration, String date) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        Route route = new Route(patente, kilometres, date, duration);
        user.addRoute(route);
        entityManager.persist(route);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Set<Route> getRoutesOfUser(User user) {
        return user.getRoutes();
    }
}
