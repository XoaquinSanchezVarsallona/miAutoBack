package methods;

import org.austral.ing.lab1.Familia;
import org.austral.ing.lab1.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class FamilyMethods {
    static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
    static final EntityManager entityManager = factory.createEntityManager();
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static User lookForUser(String username) {
        return entityManager.find(User.class, username);
    }
    public static void familyWithUser(User user) {

    }

    public static void RemoveUserFromFamily(User user, Familia family) {
        family.removeUser(user);
    }
    public static void AddUserToFamily(User user, Familia family) {
        family.addUser(user);
    }
    public static void CreateFamily(String username , String apellido) {
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
        factory.close();
    }
    public static List<Familia> getFamiliasOfUser(Session session, String username) {
        // Assuming UserDriver is an entity representing users with a 'password' property
        Query<Familia> familiasOfUserQuery = session.createQuery("SELECT f.idFamilia FROM User ud " +
                                                                            "join ud.familias fc " +
                                                                            "join Familia f on fc.idFamilia == f.idFamilia " +
                                                                            "where ud.username == :username", Familia.class);
        familiasOfUserQuery.setParameter("username", username);
        return familiasOfUserQuery.list();
    }

}

