package entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Expose
    private Long idAlert;

    @Column(nullable = false)
    @Expose
    private String message;

    @Column(nullable = false)
    @Expose
    private String tipoDeAlerta;

    @Column(nullable = false)
    @Expose
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name="idFamilia", nullable=false)
    private Familia familia;

    public Alert() {
    }

    public Alert(String message, String tipoDeAlerta) {
        this.message = message;
        this.tipoDeAlerta = tipoDeAlerta;
        this.isRead = false;
    }

    public Alert(String message) {
        this.message = message;
        this.tipoDeAlerta = "agregada";
    }

    public Long getId() {
        return idAlert;
    }

    public void setId(Long idAlert) {
        this.idAlert = idAlert;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTipoDeAlerta() {
        return tipoDeAlerta;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setAsRead() {
        this.isRead = true;
    }

    public void setAsUnread() {
        this.isRead = false;
    }
}
