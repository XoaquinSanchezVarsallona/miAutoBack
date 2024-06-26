package dao;

import entities.Car;
import entities.Familia;
import entities.Route;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CarDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public static void saveCar(Car car) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(car);
        em.getTransaction().commit();
        em.close();
    }

    public static Car getCarByPatente(String patente) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c WHERE c.patente = :patente", Car.class);
        query.setParameter("patente", patente);
        Car result = query.getSingleResult();
        em.close();
        return result;
    }

    public static boolean updateCarProfile(String patente, String field, String newValue) {
        final EntityManager entityManager = FactoryCreator.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Car car = getCarByPatente(patente);
            System.out.println("Car: " + car);

            if (car == null) {
                System.out.println("Car not found");
                return false;
            }

            switch (field) {
                case "marca":
                    car.setMarca(newValue);
                    break;
                case "modelo":
                    car.setModelo(newValue);
                    break;
                case "fechaVencimientoSeguro":
                    car.setFechaVencimientoSeguro(newValue);
                    break;
                case "fechaVencimientoVTV":
                    car.setFechaVencimientoVTV(newValue);
                    break;
                case "ano":
                    car.setAno(Integer.parseInt(newValue));
                case "kilometraje":
                    car.setKilometraje(Integer.parseInt(newValue));
                    break;
                case "patente":
                    if (getCarByPatente(newValue) != null) throw new IllegalArgumentException("Patente already exists");
                    car.setPatente(newValue);
                default:
                    System.out.println("Invalid field: " + field);
                    return false;
            }
            entityManager.merge(car);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println("Failed to update car: " + e.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

    public static void deleteCar(Car car) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Car managedCar = em.merge(car);
        List<Familia> familias = new ArrayList<>(managedCar.getFamilias());
        for (Familia familia : familias) {
            familia.removeCar(managedCar);
            em.merge(familia);
        }
        em.remove(managedCar);
        em.getTransaction().commit();
        em.close();
    }

    public static List<User> getUsersOfCar(String patente) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT DISTINCT r.user.idUser FROM Route r WHERE r.patente = :patente", Long.class);
        query.setParameter("patente", patente);
        List<Long> result = query.getResultList();
        List<User> users = UserDao.findUsersByUserIDs(result);
        em.close();
        return users;
    }

    public static List<Route> getRoutesOfAllUsersOfCar(List<User> usersOfCar, String patente) {
        List<Route> routes = new ArrayList<>();
        for (User u : usersOfCar) {
            routes.addAll(getRoutesOfUserOnCar(u, patente));
        }
        return routes;
    }

    private static List<Route> getRoutesOfUserOnCar(User user, String patente) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Route> query = em.createQuery("SELECT r FROM Route r WHERE r.user = :user AND r.patente = :patente", Route.class);
        query.setParameter("user", user);
        query.setParameter("patente", patente);
        List<Route> results = query.getResultList();
        em.close();
        return results;
    }

    public static void updateCar(Car car) {
        EntityManager em = factory.createEntityManager();
        car.setKilometraje(car.getKilometraje() + getKilometrajeAddedByRoutes(car.getPatente()));
        em.getTransaction().begin();
        em.merge(car);
        em.getTransaction().commit();
        em.close();
    }

    private static float getKilometrajeAddedByRoutes(String patente) {
        List<User> usersOfCar = CarDao.getUsersOfCar(patente);
        List<Route> routesOfUserOnCar = CarDao.getRoutesOfAllUsersOfCar(usersOfCar, patente);
        float totalKilometraje = 0;
        for (Route r : routesOfUserOnCar) {
            totalKilometraje += Float.parseFloat(r.getKilometraje());
        }
        return totalKilometraje;
    }

    public static String getCarOfRouteId(Integer routeId) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Route> query = em.createQuery("SELECT r FROM Route r WHERE r.routeId = :routeId", Route.class);
        query.setParameter("routeId", routeId);
        Route route = query.getSingleResult();
        em.close();
        return route.getPatente();
    }

    public static void substractKilometraje(Car car, String kilometraje) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        car.setKilometraje(car.getKilometraje() - Float.parseFloat(kilometraje));
        em.merge(car);
        em.getTransaction().commit();
        em.close();
    }
}
