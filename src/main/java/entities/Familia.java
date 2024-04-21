package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Familia {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private int idFamilia;

    @Column(nullable= false, unique = true)
    private String apellido;

    @ManyToMany(mappedBy = "familias", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private final List<Car> cars = new ArrayList<>();

    //como no quiero eliminar usuario al eliminar familia, saco cascade.remove de la lista.
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "familias", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private final List<User> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="familia", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Alert> alerts = new ArrayList<>();

    public Familia(String apellido) {
        this.apellido = apellido;
    }

    public Familia() {

    }
    public void addUser(User user) {
        users.add(user);
    }
    public void addCar(Car car) {
        cars.add(car);
    }
    public void removeUser( User user){
        users.remove(user);
    }
    public void removeCar(Car car) {
        cars.remove(car);
    }
    public String getApellido() {return apellido;}
    public int getFamiliaId() {
        return idFamilia;
    }
    public List<Car> getCars() {
        return cars;
    }
    public List<User> getUserDrivers() {
        return users;
    }

    public int userSize() {
        return users.size();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setSurname(String nuevoApellido) {
        this.apellido = nuevoApellido;
    }

    public void addAlert(Alert alert) {
        alerts.add(alert);
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}