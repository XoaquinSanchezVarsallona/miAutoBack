package dao;

import entities.Familia;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

import static dao.UserDao.findUserByEmail;
import static dao.UserDao.findUserByUserID;

public class FamilyDao {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");


    public static void saveFamily(Familia family) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(family);
        em.getTransaction().commit();
        em.close();
    }
    public static User lookForUser(String username) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        User result = query.getSingleResult();
        em.close();
        return result;
    }

    public static Familia getFamilia (Long userID, String apellido) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        User user = findUserByUserID(userID);
        if (user == null) {
            System.out.println("User not found");
            return null;
        }
        for (Familia familia : user.getFamilias()) {
            if (familia.getApellido().equals(apellido)) {
                return familia;
            }
        }
        System.out.println("No Familia found for the given username and apellido");
        return null;
    }

    public static Familia getFamilia (String username, String apellido) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        User user = findUserByEmail(username);
        if (user == null) {
            System.out.println("User not found");
            return null;
        }
        for (Familia familia : user.getFamilias()) {
            if (familia.getApellido().equals(apellido)) {
                return familia;
            }
        }
        System.out.println("No Familia found for the given username and apellido");
        return null;
    }


    public static List<Familia> getFamiliasOfUser(String username) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        // Assuming UserDriver is an entity representing users with a 'password' property
        TypedQuery<Familia> familiasOfUserQuery = em.createQuery("SELECT f FROM User u JOIN u.familias f WHERE u.username = :username", Familia.class );
        familiasOfUserQuery.setParameter("username", username);
        List<Familia> familias = familiasOfUserQuery.getResultList();
        em.close();
        return familias;
    }

    public static void removeFamily(Familia familia) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Familia toRemove = em.merge(familia);
        for (User user : toRemove.getUsers()) {
            user.getFamilias().remove(toRemove);
            em.merge(user);
        }
        em.remove(toRemove);
        em.getTransaction().commit();
        em.close();
    }

    public static Familia getFamiliaById(int idFamilia) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Familia> query = em.createQuery("SELECT f FROM Familia f WHERE f.idFamilia = :idFamilia", Familia.class);
        query.setParameter("idFamilia", idFamilia);
        Familia result = query.getSingleResult();
        em.close();
        return result;
    }

    public static void updateUser(User user) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public static List<String> getVehiclesOfFamily(int familyID) {
        EntityManager em = factory.createEntityManager();
        TypedQuery<String> query = em.createQuery("SELECT c.patente FROM Familia f JOIN f.cars c WHERE f.idFamilia = :familyID", String.class);
        query.setParameter("familyID", familyID);
        List<String> result = query.getResultList();
        em.close();
        return result;
    }
}
