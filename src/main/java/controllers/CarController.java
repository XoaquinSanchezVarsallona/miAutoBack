package controllers;

import DTOs.CarDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entities.Car;
import entities.Familia;
import services.CarService;
import services.FamilyService;
import spark.Route;

import java.util.ArrayList;
import java.util.List;

public class CarController {
    private final Gson gson = new Gson();

    public Route findCarByPatente = (req, res) -> {
        String patente = req.params(":patente");
        try {
            res.type("application/json");
            Car result = CarService.getCarByPatente(patente);
            if (result == null) {
                res.status(404);
                return "Couldn't find car with patente " + patente;
            } else {
                res.status(200);
                return gson.toJson(new CarDTO(result));
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };
    public Route createCar = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(req.body(), JsonObject.class);
        try {
            res.type("application/json");
            Integer familyId = Integer.valueOf(req.params(":familyId"));
            String patente = jsonObj.get("patente").getAsString();
            String marca = jsonObj.get("marca").getAsString();
            String modelo = jsonObj.get("modelo").getAsString();
            String fechaVencimientoSeguro = jsonObj.get("fechaVencimientoSeguro").getAsString();
            String fechaVencimientoVTV = jsonObj.get("fechaVencimientoVTV").getAsString();

            // Validate string inputs
            if (patente == null || patente.isEmpty() ||
                marca == null || marca.isEmpty() ||
                modelo == null || modelo.isEmpty() ||
                fechaVencimientoSeguro == null || fechaVencimientoSeguro.isEmpty() ||
                fechaVencimientoVTV == null || fechaVencimientoVTV.isEmpty()) {
                res.status(400);
                return "Invalid or missing parameters";
            }

            float kilometraje;
            int ano;
            try {
                kilometraje = Float.parseFloat(jsonObj.get("kilometraje").getAsString());
                ano = Integer.parseInt(jsonObj.get("ano").getAsString());
            } catch (NumberFormatException e) {
                res.status(400);
                return "Invalid kilometraje or año parameter";
            }
            CarService.createCar(familyId, patente, marca, modelo, kilometraje, ano, fechaVencimientoSeguro, fechaVencimientoVTV);
            // List<Car> cars = CarController.getCarsOfFamily(familyId);
            res.status(200);
            return "Car created successfully";
            // return gson.toJson(getIdOfCars(cars));
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };
    public Route deleteCar = (req, res) -> {
        String patente = req.params(":patente");
        try {
            res.type("application/json");
            Car car = CarService.getCarByPatente(patente);
            if (car == null) {
                res.status(404);
                return "Couldn't find car with patente " + patente;
            } else {
                CarService.deleteCar(car);
                res.status(200);
                return "Car deleted successfully";
            }
        } catch (Exception e) {
            res.status(500);
            return "Something went wrong";
        }
    };

/*
    private List<String> getIdOfCars(List<Car> cars) {
        List<String> ids = new ArrayList<>();
        for (Car car : cars) {
            ids.add(car.getPatente());
        }
        return ids;
    }

 */

    private static List<Car> getCarsOfFamily(Integer familyId) {
        return CarService.getCarsOfFamily(familyId);
    }
}
