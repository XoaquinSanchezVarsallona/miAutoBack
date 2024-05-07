package services;

import dao.UserDao;
import entities.Store;
import entities.User;
import DTOs.StoreDTO;
import dao.StoreDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoreService {
    public static List<StoreDTO> fetchStores(String email) {
        User user = UserDao.getUserByEmail(email);
        System.out.println("TENGO EL USER " + user);
        if (user == null) {
            return null;
        }
        System.out.println("DEVUELVO LISTA DE STORES" + user.getStores());

        return user.getStores().stream()
                .map(StoreDTO::from)
                .toList();
    }

    public static boolean deleteStore(String storeEmail) {
        return StoreDao.deleteStore(storeEmail);
    }

    public static boolean editStoreProfile(String email, String field, String value) {
        return StoreDao.editStoreProfile(email, field, value);
    }

    public static List<StoreDTO> fetchAllStores() {
        List<Store> stores = StoreDao.getAllStores();
        if (stores == null) {
            return null;
        }
        return stores.stream()
                .map(StoreDTO::from)
                .collect(Collectors.toList());
    }

    public static boolean editVisualStoreProfile(String email, String name, String domicilio, String tipoDeServicio, String description, String phoneNumber, String webPageLink, String instagramLink, String googleMapsLink) {
        return StoreDao.editVisualStoreProfile(email,name, domicilio, tipoDeServicio, description, phoneNumber, webPageLink, instagramLink, googleMapsLink);
    }

    public static StoreDTO getVisualStoreProfile(String email) {
        Store store = StoreDao.getStoreByEmail(email);
        if (store == null) {
            return null;
        }
        return StoreDTO.from(store);
    }
}
