package dao;

import entities.User;
import entities.Route;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;

public class RouteDao {
    public static Route createRoute(String patente, User user, String kilometres, String duration, String date) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        Route route = new Route(patente, kilometres, date, duration);
        user.addRoute(route);
        entityManager.persist(route);
        entityManager.getTransaction().commit();
        entityManager.close();
        return route;
    }

    public static Set<Route> getRoutesOfUser(User user, String patente) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        Set<Route> routes = user.getRoutes();
        Set<Route> result = routes.stream().filter(route -> route.getPatente().equals(patente)).collect(Collectors.toSet()); // Filtro por patente
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    public static Set<Route> getRoutesOfUser(User user) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        Set<Route> routes = user.getRoutes();
        entityManager.getTransaction().commit();
        entityManager.close();
        return routes;
    }

    public static void deleteRoute(Route route) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Route managedRoute = entityManager.merge(route);
            User user = managedRoute.getUser();
            user.removeRoute(managedRoute);
            entityManager.merge(user);
            entityManager.remove(managedRoute);
            entityManager.getTransaction().commit();
            entityManager.clear();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error deleting route");
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public static Route getRouteById(Integer routeId) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        Route route = entityManager.find(Route.class, routeId);
        entityManager.getTransaction().commit();
        entityManager.close();
        if (route == null) {
            System.out.println("Route not found with id: " + routeId);
            return null;
        }
        return route;
    }

    public static void updateRoute(Route route, String kilometres, String duration, String date) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            route.setKilometraje(kilometres);
            route.setDuration(duration);
            route.setFecha(date);
            entityManager.merge(route);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Error updating route");
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public static Set<Route> getRoutesOfCar(String patente) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        entityManager.getTransaction().begin();
        Set<Route> routes = entityManager.createQuery("SELECT r FROM Route r WHERE r.patente = :patente", Route.class)
                .setParameter("patente", patente)
                .getResultList()
                .stream()
                .collect(Collectors.toSet());
        entityManager.getTransaction().commit();
        entityManager.close();
        return routes;
    }
}
