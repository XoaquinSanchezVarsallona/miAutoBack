package services;

import dao.FactoryCreator;
import dao.FamilyDao;

import entities.Familia;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

public class FamilyService {

    public static void createFamily(String username , String apellido) {


        User user = FamilyDao.lookForUser(username);
        List<Familia> familias = FamilyDao.getFamiliasOfUser(username);
        // Me fijo que no exista una familia con el ismo apellido en la db
        for (Familia familia : familias) {
            if (familia.getApellido().equals(apellido)) throw new IllegalArgumentException("Apellido has already been used");
        }
        Familia family = new Familia(apellido);
        // Comienza la transacción
        FamilyDao.saveFamily(family);
        family.addUser(user);
        user.addFamily(family);
        FamilyDao.updateUser(user);
        // Finaliza la transacción
    }

    public static boolean deleteMember(String username, String apellido) {
        Familia familia = FamilyDao.getFamilia(username, apellido);
        User user = FamilyDao.lookForUser(username);
        familia.removeUser(user);
        user.removeFamilia(familia);

        if (familia.userSize() == 0) {
            FamilyDao.removeFamily(familia);
            return true;

        } else return false;
        //verificacion de que se borro solo el member o que se borro la familia tambien.
    }

    public static List<Familia> getFamiliasOfUser(String username) {
        return FamilyDao.getFamiliasOfUser(username);
    }

    public static void addMember(String username, String apellido) {
        Familia familia = FamilyDao.getFamilia( username, apellido);
        User user = FamilyDao.lookForUser(username);
        familia.addUser(user);
    }

    public static Familia getFamiliaById(int idFamilia) {
        return FamilyDao.getFamiliaById(idFamilia);
    }

    public static boolean deleteFamily(Long userID, String apellido) {
        Familia familia = FamilyDao.getFamilia(userID, apellido);
        if (familia != null) {
            FamilyDao.removeFamily(familia);
            return true;
        }
        return false;
    }

    public static void updateSurname(String apellido, String nuevoApellido) {
        EntityManager entityManager = FactoryCreator.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Familia familia = entityManager.createQuery("SELECT f FROM Familia f WHERE f.apellido = :apellido", Familia.class)
                    .setParameter("apellido", apellido)
                    .getSingleResult();
            System.out.println("FAMILIAAAA" + familia);

            familia.setSurname(nuevoApellido);
            entityManager.merge(familia);

            transaction.commit();
        } catch (NoResultException e) {
            System.out.println("No Familia entity with the given surname exists");
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while updating the surname: " + e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public static List<String> getVehiclesOfFamily(int familyID) {
        return FamilyDao.getVehiclesOfFamily(familyID);
    }
}
