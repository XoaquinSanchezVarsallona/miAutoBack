package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long experienceId;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long storeId;

    @Column(nullable = false)
    private String patente;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private Timestamp creationDate = new Timestamp(System.currentTimeMillis());

    // Getters and setters...

    public Long getId() {
        return experienceId;
    }

    public void setId(Long id) {
        this.experienceId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String vehicleId) {
        this.patente = vehicleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCreationDate(Timestamp timestamp) {
        this.creationDate = timestamp;
    }
}