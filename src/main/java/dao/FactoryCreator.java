package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryCreator {
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("miAutoDB");
    public static EntityManager getEntityManager() {return factory.createEntityManager();}
}
