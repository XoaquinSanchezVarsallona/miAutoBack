package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Familia {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private int idFamilia;

    @Column(nullable= false, unique = true)
    private String apellido;

    //@Column(nullable= false)
    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "familias", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private final Set<Car> cars = new HashSet<>();

    //como no quiero eliminar usuario al eliminar familia, saco cascade.remove de la lista.
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "familias", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private final List<User> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="familia", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Alert> alerts = new ArrayList<>();

    public Familia(String apellido) {
        this.apellido = apellido;
        this.password = String.valueOf(' ');
    }

    public Familia(String apellido, String password) {
        this.apellido = apellido;
        this.password = password;
    }

    public Familia() {

    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public Set<Car> getCars() {
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