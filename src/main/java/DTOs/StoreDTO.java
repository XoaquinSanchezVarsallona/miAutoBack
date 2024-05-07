package DTOs;

import entities.Store;

public class StoreDTO {

    public String storeName;
    public String storeEmail;
    public String domicilio;
    public String tipoDeServicio;
    public String description;
    public String phoneNumber;
    public String webPageLink;
    public String instagramLink;
    public String googleMapsLink;

    public StoreDTO(Store store) {
        this.storeName = store.getStoreName();
        this.storeEmail = store.getStoreEmail();
        this.domicilio = store.getDomicilio();
        this.tipoDeServicio = store.getTipoDeServicio();
        this.description = store.getDescription();
        this.phoneNumber = store.getPhoneNumber();
        this.webPageLink = store.getWebPageLink();
        this.instagramLink = store.getInstagramLink();
        this.googleMapsLink = store.getGoogleMapsLink();
    }

    public static StoreDTO from(Store store) {
        return new StoreDTO(store);
    }
}
