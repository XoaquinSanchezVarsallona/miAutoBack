package DTOs;

import com.google.gson.JsonElement;
import entities.Car;

public class CarDTO {
    public String patente;
    String marca;
    String modelo;
    float kilometraje;
    int ano;
    String fechaVencimientoSeguro;
    String fechaVencimientoVTV;
    String estadoActual;
    public CarDTO(Car result) {
        this.patente = result.getPatente();
        this.marca = result.getMarca();
        this.modelo = result.getModelo();
        this.kilometraje = result.getKilometraje();
        this.ano = result.getAno();
        this.fechaVencimientoSeguro = result.getFechaVencimientoSeguro();
        this.fechaVencimientoVTV = result.getFechaVencimientoVTV();
        this.estadoActual = result.getEstado();
    }
}
