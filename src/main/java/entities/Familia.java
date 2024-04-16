package entities;

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
    private List<Car> cars = new ArrayList<>();

    @ManyToMany(mappedBy = "familias", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

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

    public List<Car> getCars() {
        return cars;
    }
    public List<User> getUserDrivers() {
        return users;
    }

    public int userSize() {
        return users.size();
    }
}