package entities;

import javax.persistence.*;
@Entity
public class Registration {

    public Registration() {

    }
    public Registration(String png , User user, Car car) {
        setCar(car);
        setPng(png);
        setUserRegistered(user);
    }

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private int idRegistration;

    public Car getCar() {
        return car;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "car", nullable = false)
    private Car car;

    public void setUserRegistered(User userRegistered) {
        this.userRegistered = userRegistered;
    }

    public User getUserRegistered() {
        return userRegistered;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userRegistered", nullable=false)
    private User userRegistered ;

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    @Lob
    @Column(columnDefinition = "LONGVARCHAR")
    private String png;

    public void setCar(Car car) {
        this.car = car;
    }
}