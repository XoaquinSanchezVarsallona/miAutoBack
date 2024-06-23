package services;

import dao.UserDao;
import entities.Review;
import entities.Store;
import entities.User;
import DTOs.StoreDTO;
import dao.StoreDao;

import java.util.*;
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

    public static List<StoreDTO> getStoresByRating() {
        Map<Store, Integer> stores = StoreDao.getStoresbyRating();
        if (stores.isEmpty()) {
            return null;
        }
        Comparator<Map.Entry<Store, Integer>> comparator = Map.Entry.comparingByValue();
        List<Map.Entry<Store, Integer>> sortedKeys = stores.entrySet().stream().sorted(comparator).toList();
        List<StoreDTO> storesDTO  = new ArrayList<>();
        for (Map.Entry<Store, Integer> entry : sortedKeys) storesDTO.add(StoreDTO.from(entry.getKey()));
        Collections.reverse(storesDTO);
        return storesDTO;
    }

    public static boolean editVisualStoreProfile(String email, String name, Double domicilioLongitud, Double domicilioLatitud, String tipoDeServicio, String description, String phoneNumber, String webPageLink, String instagramLink, String googleMapsLink) {
        return StoreDao.editVisualStoreProfile(email,name, domicilioLongitud, domicilioLatitud, tipoDeServicio, description, phoneNumber, webPageLink, instagramLink, googleMapsLink);
    }

    public static StoreDTO getVisualStoreProfile(String email) {
        Store store = StoreDao.getStoreByEmail(email);
        if (store == null) {
            return null;
        }
        return StoreDTO.from(store);
    }

    public static boolean submitRatingAndComment(long userID, long storeID, int rating, String comment) {
        try {
            Review review = new Review();
            review.setUserID(userID);
            review.setStoreID(storeID);
            review.setRating(rating);
            review.setComment(comment);
            StoreDao.saveReview(review);
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred while submitting the rating and comment: " + e.getMessage());
            return false;
        }
    }

    public static List<Review> fetchAllReviews(long storeID) {
        return StoreDao.getAllReviews(storeID);
    }

    public static Review fetchUserReview(long userId, long storeId) {
        return StoreDao.getUserReview(userId, storeId);
    }

    public static boolean deleteReview(Long userID, Long storeID) {

        return StoreDao.deleteReview(userID, storeID);
    }

    public static boolean updateReview(long userId, long storeId, int rating, String comment) {
        return StoreDao.updateReview(userId, storeId, rating, comment);
    }

    public static Store getStoreByEmail(String email) {
        return StoreDao.getStoreByEmail(email);
    }

    public static Store getStoreById(long storeId) {
        return StoreDao.getStoreById(storeId);
    }
}
