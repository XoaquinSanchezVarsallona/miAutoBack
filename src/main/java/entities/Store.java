package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Store {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private long idStore;

    @Column
    public String tipoDeServicio;

    @Column
    public String domicilio;

    @Column(nullable= false, unique = true)
    public String storeName;

    @Column
    public String storeEmail;

    //un store pertenece a un user 'servicio'
    @ManyToOne
    @JoinColumn(name="idUser", nullable=false)
    private User user;

    public Store(String email, String storeName, String domicilio, String tipoDeServicio) {
        this.storeEmail = email;
        this.storeName = storeName;
        this.domicilio = domicilio;
        this.tipoDeServicio = tipoDeServicio;
    }

    public Store() {
    }

    public void setEmail(String email) {
        this.storeEmail = email;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setTipoDeServicio(String tipoDeServicio) {
        this.tipoDeServicio = tipoDeServicio;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTipoDeServicio() {
        return tipoDeServicio;
    }
}
