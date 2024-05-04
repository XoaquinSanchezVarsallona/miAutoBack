package dao;

import entities.Store;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.List;

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
}
