package services;

import dao.FamilyDao;
import entities.Car;
import entities.Familia;

public class CarService {
        public static void createCar(Integer familyId, String patente, String marca, String modelo, float kilometraje, int ano, String fechaVencimientoSeguro, String fechaVencimientoVTV) {
        Car car = new Car(patente, marca, modelo, kilometraje, ano, fechaVencimientoSeguro, fechaVencimientoVTV);
            System.out.println("esssssssssssso"  + car);
        Familia familia = FamilyDao.getFamiliaById(familyId);
            System.out.println("esoooooooooooo" + familia + car);
        if (familia != null) {
            car.addFamilia(familia);
            System.out.println("caaaaaaaaaaaaaaar");
            CarDao.saveCar(car);
            System.out.println("siiiiiiiiiiiiiiiiiiiii");
        } else {
            throw new RuntimeException("Familia not found");
        }
        // MANEJAR LOS CASOS QUE YA EXISTE UN AUTO
    }

    public static Car getCarByPatente(String patente) {
        return CarDao.getCarByPatente(patente);
    }
}
