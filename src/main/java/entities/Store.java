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

    //visual profile data
    @Column
    public String description;

    @Column
    public String phoneNumber;

    @Column
    public String webPageLink;

    @Column
    public String instagramLink;

    @Column
    public String googleMapsLink;

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

    public Store(String email, String storeName, String domicilio, String tipoDeServicio, String description, String phoneNumber, String webPageLink, String instagramLink, String googleMapsLink) {
        this.storeEmail = email;
        this.storeName = storeName;
        this.domicilio = domicilio;
        this.tipoDeServicio = tipoDeServicio;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.webPageLink = webPageLink;
        this.instagramLink = instagramLink;
        this.googleMapsLink = googleMapsLink;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoogleMapsLink(String googleMapsLink) {
        this.googleMapsLink = googleMapsLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWebPageLink(String webPageLink) {
        this.webPageLink = webPageLink;
    }

    public String getDescription() {
        return description;
    }

    public String getGoogleMapsLink() {
        return googleMapsLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public String getWebPageLink() {
        return webPageLink;
    }

    public long getIdStore() {
        return idStore;
    }
}
