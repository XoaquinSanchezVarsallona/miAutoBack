package services;

import dao.FamilyDao;

import dao.UserDao;
import entities.Familia;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class FamilyService {
    private FamilyDao familyDao;

    static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");

    public FamilyService(FamilyDao familyDao) {
        this.familyDao = familyDao;
    }

    public static User lookForUser(String username) {
        final EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(User.class, username);
    }
    public static void createFamily(String username , String apellido) {
        final EntityManager entityManager = factory.createEntityManager();

        User user = lookForUser(username);
        List<Familia> familias = getFamiliasOfUser(username);
        for (Familia familia : familias) {
            if (familia.getApellido().equals(apellido)) throw  new IllegalArgumentException("Apellido has already been used");
        }
        Familia family = new Familia(apellido);
        FamilyDao.addFamilyToUser(entityManager, family, user);
    }


    private static Familia getFamilia (String username, String apellido) {
        final EntityManager entityManager = factory.createEntityManager();
        TypedQuery<Familia> familiasOfUserQuery = entityManager.createQuery("SELECT f.idFamilia FROM User ud " +
                "join ud.familias fc " +
                "join Familia f on fc.idFamilia = f.idFamilia " +
                "where ud.username = :username " +
                "and f.apellido = :apellido", Familia.class);

        familiasOfUserQuery.setParameter("username", username);
        familiasOfUserQuery.setParameter("apellido", apellido);
        Familia familia = familiasOfUserQuery.getSingleResult();
        entityManager.close();
        return familia;
    }


    public static List<Familia> getFamiliasOfUser(String username) {
        final EntityManager entityManager = factory.createEntityManager();

        // Assuming UserDriver is an entity representing users with a 'password' property
        TypedQuery<Familia> familiasOfUserQuery = entityManager.createQuery("SELECT f.idFamilia FROM User ud " +
                "join ud.familias fc " +
                "join Familia f on fc.idFamilia = f.idFamilia " +
                "where ud.username = :username", Familia.class);
        familiasOfUserQuery.setParameter("username", username);
        List<Familia> familias = familiasOfUserQuery.getResultList();
        entityManager.close();
        return familias;
    }

    public static boolean deleteMember(String username, String apellido) {
        Familia familia = getFamilia(username, apellido);
        User user = lookForUser(username);
        familia.removeUser(user);
        user.removeFamilia(familia);

        if (familia.userSize() == 0) {
            FamilyDao.removeFamily(familia);
            return true;

        } else return false;
        //verificacion de que se borro solo el member o que se borro la familia tambien.
    }

    public static void addMember(String username, String apellido) {
        Familia familia = getFamilia( username, apellido);
        User user = lookForUser(username);
        familia.addUser(user);
    }
}
