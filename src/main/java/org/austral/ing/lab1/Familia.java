package org.austral.ing.lab1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Familia {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private int idFamilia;

    @Column
    private String apellido;

    @ManyToMany(mappedBy = "familias", cascade = CascadeType.ALL)
    private final List<Car> cars = new ArrayList<>();

    @ManyToMany(mappedBy = "familias", cascade = CascadeType.ALL)
    private final List<UserDriver> userDrivers = new ArrayList<>();

    public Familia(String apellido) {
        this.apellido = apellido;
    }

    public Familia() {

    }

    public List<Car> getCars() {
        return cars;
    }
    public List<UserDriver> getUserDrivers() {
        return userDrivers;
    }

}