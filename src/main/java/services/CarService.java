package services;

import dao.CarDao;
import dao.FamilyDao;
import entities.Car;
import entities.Familia;

import java.util.Set;

public class CarService {
        public static void createCar(Integer familyId, String patente, String marca, String modelo, float kilometraje, int ano, String fechaVencimientoSeguro, String fechaVencimientoVTV) {
        Car car = new Car(patente, marca, modelo, kilometraje, ano, fechaVencimientoSeguro, fechaVencimientoVTV);
        Familia familia = FamilyDao.getFamiliaById(familyId);
        if (familia != null) {
            car.addFamilia(familia);
            CarDao.saveCar(car);
        } else {
            throw new RuntimeException("Familia not found");
        }
    }

    public static Car getCarByPatente(String patente) {
        return CarDao.getCarByPatente(patente);
    }

    public static Set<Car> getCarsOfFamily(Integer familyId) {
        Familia familia = FamilyDao.getFamiliaById(familyId);
        if (familia != null) {
            return familia.getCars();
        } else {
            throw new RuntimeException("Familia not found");
        }
    }

    public static boolean updateCarProfile(String patente, String field, String newValue) {
        return CarDao.updateCarProfile(patente, field, newValue);
    }

    public static void deleteCar(Car car) {
        if (car == null) {
            System.out.println("Car is null");
            return;
        }
        try {
            CarDao.deleteCar(car);
            System.out.println("Car deleted successfully");
        } catch (Exception e) {
            System.out.println("Error deleting car: " + e.getMessage());
        }
    }

    public static void updateKilometraje(String patente) {
        Car car = CarDao.getCarByPatente(patente);
        if (car == null) {
            throw new RuntimeException("Car not found");
        }
        CarDao.updateCar(car);
    }

    public static String getCarOfRouteId(Integer routeId) {
        return CarDao.getCarOfRouteId(routeId);
    }

    public static void substractKilometraje(Car car, String kilometraje) {
        CarDao.substractKilometraje(car, kilometraje);
    }
}
