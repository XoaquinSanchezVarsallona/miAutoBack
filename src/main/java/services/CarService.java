package services;

import dao.FamilyDao;
import entities.Car;
import entities.Familia;

import java.util.List;
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
        System.out.println("FAMILLLLLLLLIA: " + familia);
        if (familia != null) {
            System.out.println("familia is not nuuuuuuuuull");
            Set<Car> cars = familia.getCars();
            System.out.println("CARSsssssssssssss: " + cars);
            return cars;
        } else {
            throw new RuntimeException("Familia not found");
        }
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
}
