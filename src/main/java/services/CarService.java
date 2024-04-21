package services;

import dao.FamilyDao;
import entities.Car;
import entities.Familia;

import java.util.List;

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

    public static List<Car> getCarsOfFamily(Integer familyId) {
        Familia familia = FamilyDao.getFamiliaById(familyId);
        if (familia != null) {
            return familia.getCars();
        } else {
            throw new RuntimeException("Familia not found");
        }
    }
}
