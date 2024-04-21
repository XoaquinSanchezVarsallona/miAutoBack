package entities;

import javax.persistence.*;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idAlert;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String tipoDeAlerta;

    @ManyToOne
    @JoinColumn(name="idFamilia", nullable=false)
    private Familia familia;

    public Alert() {
    }

    public Alert(String message, String tipoDeAlerta) {
        this.message = message;
        this.tipoDeAlerta = tipoDeAlerta;
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
}
