package DTOs;

import entities.Store;

public class StoreDTO {

    public String storeName;
    public String storeEmail;
    public String domicilio;
    public String tipoDeServicio;

    public StoreDTO(Store store) {
        this.storeName = store.getStoreName();
        this.storeEmail = store.getStoreEmail();
        this.domicilio = store.getDomicilio();
        this.tipoDeServicio = store.getTipoDeServicio();
    }

    public static StoreDTO from(Store store) {
        return new StoreDTO(store);
    }
}
