package dao;

import entities.Familia;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

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
        return em.find(User.class, username);
    }

    public static Familia getFamilia (String username, String apellido) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Familia> familiasOfUserQuery = em.createQuery("SELECT f.idFamilia FROM User ud " +
                                                                        "join ud.familias fc " +
                                                                        "join Familia f on fc.idFamilia = f.idFamilia " +
                                                                        "where ud.username = :username " +
                                                                        "and f.apellido = :apellido", Familia.class);

        familiasOfUserQuery.setParameter("username", username);
        familiasOfUserQuery.setParameter("apellido", apellido);
        Familia familia = familiasOfUserQuery.getSingleResult();
        em.close();
        return familia;
    }

    public static List<Familia> getFamiliasOfUser(String username) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        // Assuming UserDriver is an entity representing users with a 'password' property
        TypedQuery<Familia> familiasOfUserQuery = em.createQuery("SELECT f.idFamilia FROM User ud " +
                                                                        "join ud.familias fc " +
                                                                        "join Familia f on fc.idFamilia = f.idFamilia " +
                                                                        "where ud.username = :username", Familia.class);
        familiasOfUserQuery.setParameter("username", username);
        List<Familia> familias = familiasOfUserQuery.getResultList();
        em.close();
        return familias;
    }

    public static void removeFamily(Familia familia) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.remove(familia);
        em.getTransaction().commit();
        em.close();
    }

}
