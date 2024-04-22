package entities;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Car {

    @Id
    private String patente;

    // Se me ocurrió que el estado va a fluctuar entre 0 y 10 dependiendo como ponderemos las alertas/kilometraje/reparaciones. Y que basándonos en el número, sé display rojo, verde o naranja
    @Column
    private Float estado;

    @Column
    private String marca;

    @Column
    private String modelo;

    @Column
    private float kilometraje;

    // Año de compra? Año desde q se arrancó a usar? año de creación?
    @Column
    private int ano;

    // Estas dos serian string, o serian datetime? en el caso de datetime, no nos importa la hora sino q el año/mes/dia.
    @Column
    private String fechaVencimientoSeguro;

    @Column
    private String fechaVencimientoVTV;

    @ManyToMany
    @JoinTable(
            name = "familia_auto",
            joinColumns = @JoinColumn(name = "patente"),
            inverseJoinColumns = @JoinColumn(name = "idFamilia")
    )
    private List<Familia> familias = new ArrayList<>();


    public Car(String patente, String marca, String modelo, float kilometraje, int ano, String fechaVencimientoSeguro, String fechaVencimientoVTV) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.ano = ano;
        this.fechaVencimientoSeguro = fechaVencimientoSeguro;
        this.fechaVencimientoVTV = fechaVencimientoVTV;
    }

    public Car() {

    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public float getKilometraje() {
        return kilometraje;
    }

    public int getAno() {
        return ano;
    }

    public String getFechaVencimientoSeguro() {
        return fechaVencimientoSeguro;
    }

    public String getFechaVencimientoVTV() {
        return fechaVencimientoVTV;
    }

    public List<Familia> getFamilias() {
        return familias;
    }

    public void addFamilia(Familia familia) {
        this.familias.add(familia);
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setFechaVencimientoSeguro(String fechaVencimientoSeguro) {
        this.fechaVencimientoSeguro = fechaVencimientoSeguro;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setFechaVencimientoVTV(String fechaVencimientoVTV) {
        this.fechaVencimientoVTV = fechaVencimientoVTV;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setKilometraje(float kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }
}