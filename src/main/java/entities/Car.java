package entities;

import entities.condicionales.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Car {

    @Id
    private String patente;

    @Column
    private String estadoActual;

    @Column
    private String marca;

    @Column
    private String modelo;

    @Column
    private float kilometraje;

    @Column
    private int ano;

    @Column
    private String fechaVencimientoSeguro;

    @Column
    private String fechaVencimientoVTV;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "familia_auto",
            joinColumns = @JoinColumn(name = "patente"),
            inverseJoinColumns = @JoinColumn(name = "idFamilia")
    )
    private List<Familia> familias = new ArrayList<>();

    public Set<Registration> getRegisters() {
        return registers;
    }

    public void addRegister(Registration register) {
        registers.add(register);
    }

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Registration> registers = new HashSet<>();

    @Transient
    private final List<Condicionales> condicionales;

    public Car(String patente, String marca, String modelo, float kilometraje, int ano, String fechaVencimientoSeguro, String fechaVencimientoVTV) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.ano = ano;
        this.fechaVencimientoSeguro = fechaVencimientoSeguro;
        this.fechaVencimientoVTV = fechaVencimientoVTV;
        this.estadoActual = getEstado();
        this.condicionales = getCondicionales();
    }


    public Car() {
        this.condicionales = getCondicionales();
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

    public void removeFamily(Familia familia) {
        this.familias.remove(familia);
    }

    public String getEstado() {
        int count = 0;
        int total = condicionales.size();
        for (Condicionales condicional : condicionales) {
            if (condicional.verificado(this.kilometraje)) {
                count++;
            }
        }
        double avg = (double) count / total;
        if (avg >= 0.7) {
            return "Verde";
        } else if (avg >= 0.3) {
            return "Amarillo";
        } else {
            return "Rojo";
        }
    }

    private List<Condicionales> getCondicionales() {
        List<Condicionales> result = new ArrayList<>();
        result.add(new AceiteMotor());
        result.add(new CorreaDistribucion());
        result.add(new FiltroAceite());
        result.add(new FiltroAire());
        result.add(new FiltroCombustible());
        result.add(new FiltroHabitaculo());
        return result;
    }
}