package dao;

import entities.Familia;
import entities.User;

import javax.persistence.EntityManager;

public class FamilyDao {
    private static EntityManager entityManager;
    public FamilyDao(EntityManager entityManager) {
        //aca iria el manejo de entityManager de la parte de familyService
        UserDao.entityManager = entityManager;
    }

    public static void addFamilyToUser(EntityManager entityManager, Familia family, User user) {
        // Comienza la transacción
        entityManager.getTransaction().begin();
        entityManager.persist(family);
        entityManager.getTransaction().commit();
        entityManager.close();
        family.addUser(user);
        user.addFamily(family);
        // Finaliza la transacción
    }

    public static void removeFamily(Familia familia) {
        entityManager.getTransaction().begin();
        entityManager.remove(familia);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
