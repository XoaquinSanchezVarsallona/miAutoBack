package entities;

import javax.persistence.*;
@Entity
public class Registration {

    public Registration() {

    }
    public Registration(byte[] png) {
        this.png = png;
    }

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private int idRegistration;

    public Car getCar() {
        return car;
    }

    @ManyToOne
    @JoinColumn(name= "car", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name="userRegistered", nullable=false)
    private User userRegistered ;

    public byte[] getPng() {
        return png;
    }

    public void setPng(byte[] png) {
        this.png = png;
    }

    @Column
    private byte[] png;

}