package services;

import dao.FamilyDao;

import entities.Familia;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class FamilyService {
    static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
    static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static User lookForUser(String username) {
        final EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(User.class, username);
    }
    public static void createFamily(String username , String apellido) {
        final EntityManager entityManager = factory.createEntityManager();

        User user = lookForUser(username);
        List<Familia> familias = getFamiliasOfUser(sessionFactory.openSession(), username);
        for (Familia familia : familias) {
            if (familia.getApellido().equals(apellido)) throw  new IllegalArgumentException("Apellido has already been used");
        }
        Familia family = new Familia(apellido);
        // Comienza la transacción
        entityManager.getTransaction().begin();
        entityManager.persist(family);
        entityManager.getTransaction().commit();
        entityManager.close();
        family.addUser(user);
        user.addFamily(family);
        // Finaliza la transacción
    }
    public static List<Familia> getFamiliasOfUser(Session session, String username) {
        // Assuming UserDriver is an entity representing users with a 'password' property
        Query<Familia> familiasOfUserQuery = session.createQuery("SELECT f.idFamilia FROM User ud " +
                "join ud.familias fc " +
                "join Familia f on fc.idFamilia = f.idFamilia " +
                "where ud.username = :username", Familia.class);
        familiasOfUserQuery.setParameter("username", username);
        List<Familia> familias = familiasOfUserQuery.list();
        session.close();
        return familias;
    }

    private static Familia getFamilia (Session session, String username, String apellido) {
        Query<Familia> familiasOfUserQuery = session.createQuery("SELECT f.idFamilia FROM User ud " +
                "join ud.familias fc " +
                "join Familia f on fc.idFamilia = f.idFamilia " +
                "where ud.username = :username " +
                "and f.apellido = :apellido", Familia.class);

        familiasOfUserQuery.setParameter("username", username);
        familiasOfUserQuery.setParameter("apellido", apellido);
        return familiasOfUserQuery.uniqueResult();
    }

    public static boolean deleteMember(String username, String apellido) {
        Familia familia = getFamilia(sessionFactory.openSession(), username, apellido);
        User user = lookForUser(username);
        familia.removeUser(user);
        user.removeFamilia(familia);

        if (familia.userSize() == 0) {
            removeFamily(familia);
            return true;

        } else return false;
        //verificacion de que se borro solo el member o que se borro la familia tambien.
    }

    private static void removeFamily(Familia familia) {
        final EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(familia);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void addMember(String username, String apellido) {
        Familia familia = getFamilia(sessionFactory.openSession(), username, apellido);
        User user = lookForUser(username);
        familia.addUser(user);
    }
}
