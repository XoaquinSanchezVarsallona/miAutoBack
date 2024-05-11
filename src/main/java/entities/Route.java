package entities;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Route {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private int routeId;

    @Column(nullable = false)
    private String patente;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @Column
    private String kilometraje;

    @Column
    private String fecha;

    @Column
    private String duration;

    // Constructor
    public Route(String patente, String kilometraje, String fecha, String duration) {
        this.patente = patente;
        this.kilometraje = kilometraje;
        this.fecha = fecha;
        this.duration = duration;
    }

    public Route() {

    }

    // Get and Setters

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getRouteId() {
        return routeId;
    }
}
