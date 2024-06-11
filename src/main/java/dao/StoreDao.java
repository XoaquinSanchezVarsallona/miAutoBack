package dao;

import entities.Review;
import entities.Store;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static dao.FactoryCreator.factory;

public class StoreDao {

    public static void saveStore(Store store) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            System.out.println("ENTRO A SAVE STORE");
            em.persist(store);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("An error occurred while saving the store: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static boolean deleteStore(String storeEmail) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            //Store store = entityManager.find(Store.class, storeEmail);
            //como no tengo id, encuentro store por su mail
            Store store = entityManager.createQuery("SELECT s FROM Store s WHERE s.storeEmail = :storeEmail", Store.class)
                    .setParameter("storeEmail", storeEmail)
                    .getSingleResult();

            System.out.println("ENCONTRE A STORE" + store);
            if (store != null) {
                entityManager.remove(store);
                System.out.println("STORE ELIMINADO");
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static boolean editStoreProfile(String email, String field, String value) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Store store = entityManager.createQuery("SELECT s FROM Store s WHERE s.storeEmail = :storeEmail", Store.class)
                    .setParameter("storeEmail", email)
                    .getSingleResult();
            System.out.println("ENCONTRE A STORE" + store);

            if (store != null) {
                switch (field) {
                    case "name":
                        store.setStoreName(value);
                        break;
                    case "email":
                        store.setEmail(value);
                        break;
                    case "domicilio":
                        store.setDomicilio(value);
                        break;
                    case "serviceType":
                        store.setTipoDeServicio(value);
                        break;
                    default:
                        return false;
                }

                entityManager.merge(store);
                System.out.println("MERGEE A STORE" + store);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static List<Store> getAllStores() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT s FROM Store s", Store.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public static boolean editVisualStoreProfile(String email,  String name, String domicilio, String tipoDeServicio, String description, String phoneNumber, String webPageLink, String instagramLink, String googleMapsLink) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Store store = entityManager.createQuery("SELECT s FROM Store s WHERE s.storeEmail = :storeEmail", Store.class)
                    .setParameter("storeEmail", email)
                    .getSingleResult();
            System.out.println("ENCONTRE A STORE" + store);

            if (store != null) {
                store.setStoreName(name);
                store.setDomicilio(domicilio);
                store.setTipoDeServicio(tipoDeServicio);
                store.setDescription(description);
                store.setPhoneNumber(phoneNumber);
                store.setWebPageLink(webPageLink);
                store.setInstagramLink(instagramLink);
                store.setGoogleMapsLink(googleMapsLink);

                entityManager.merge(store);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static Store getStoreByEmail(String email) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT s FROM Store s WHERE s.storeEmail = :email", Store.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public static Store getStoreByName(String storeName) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT s FROM Store s WHERE s.storeName = :storeName", Store.class)
                    .setParameter("storeName", storeName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public static Store getStoreById(long storeId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT s FROM Store s WHERE s.idStore = :storeId", Store.class)
                    .setParameter("storeId", storeId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    public static void saveReview(Review review){
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(review);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Print the full stack trace
            System.out.println("An error occurred while saving the review: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static List<Review> getAllReviews(long storeID) {
        EntityManager em = factory.createEntityManager();

        try {
            TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.storeID = :storeID", Review.class);
            query.setParameter("storeID", storeID);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public static Review getUserReview(long userId, long storeId) {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.userID = :userId AND r.storeID = :storeId", Review.class);
            query.setParameter("userId", userId);
            query.setParameter("storeId", storeId);
            System.out.println("QUERYYYYY" + query.getSingleResult());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean deleteReview(long userId, long storeId) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.userID = :userId AND r.storeID = :storeId", Review.class);
            query.setParameter("userId", userId);
            query.setParameter("storeId", storeId);
            Review review = query.getSingleResult();
            em.remove(review);
            transaction.commit();
            return true;
        } catch (NoResultException e) {
            return false;
        } finally {
            em.close();
        }
    }

    public static boolean updateReview(long userId, long storeId, int rating, String comment) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.userID = :userId AND r.storeID = :storeId", Review.class);
            query.setParameter("userId", userId);
            query.setParameter("storeId", storeId);
            Review review = query.getSingleResult();
            System.out.println("REVIEWWWW" + review);
            review.setRating(rating);
            review.setComment(comment);
            em.merge(review);
            transaction.commit();
            return true;
        } catch (NoResultException e) {
            System.out.println("excepcion de no resultadooo");
            return false;
        } finally {
            em.close();
        }
    }

    public static Map<Store, Integer> getStoresbyRating() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Map<Store, Integer> map = new LinkedHashMap<>();
        List <Store> stores = getAllStores();
        for (Store store : stores) {
            List<Review> reviews = getAllReviews(store.getIdStore());
            int totalRating = 0;
            for (Review review : reviews) {
                totalRating += review.getRating();
            }
            if (!reviews.isEmpty()) {
                map.put(store, totalRating / reviews.size() - 1);
            } else {
                map.put(store, 0);
            }
        }
        em.getTransaction().commit();
        return map;

    }
}
