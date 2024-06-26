package services;

import dao.FactoryCreator;
import dao.FamilyDao;

import dao.UserDao;
import entities.Alert;
import entities.Familia;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

import dao.AlertDao;
import extras.IncorrectPasswordException;

public class FamilyService {

    public static void createFamily(String username , String apellido, String password) {
        User user = FamilyDao.lookForUser(username);
        // Me fijo que no exista una familia con el mismo apellido en la db
        if (FamilyDao.getFamiliaFromAllExistingFamilies(apellido) != null) {
            throw new IllegalArgumentException("Surname already in use");
        }
        if (Objects.equals(apellido, "")) {
            throw new IllegalArgumentException("Surname cannot be empty");
        }
        Familia family = new Familia(apellido, password);
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
    public static void joinToFamily(String username, String apellido, String password) throws Exception {
        List<Familia> userFamilias = FamilyDao.getFamiliasOfUser(username);
        for (Familia familia : userFamilias) {
            if (familia.getApellido().equals(apellido)) {
                throw new IllegalArgumentException("You are already in that family");
            }
        }

        Familia familia = FamilyDao.getFamiliaFromAllExistingFamilies(apellido);
        if (familia == null) {
            throw new NoResultException("No Familia found for the given apellido");
        }
        if (!familia.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        User user = FamilyDao.lookForUser(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        familia.addUser(user);
        user.addFamily(familia);
        FamilyDao.updateUser(user);
    }

    public static void addAlertToFamily(String message, String apellido, String username) throws Exception {
        Familia familia = FamilyDao.getFamiliaFromAllExistingFamilies(apellido);

        // Find the User entity by username
        User user = UserDao.findUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Check if the user is part of the family
        assert familia != null;
        if (!isUserPartOfFamily(familia, user)) {
            throw new IllegalArgumentException("User is not part of the family");
        }

        // Create a new Alert
        Alert alert = new Alert(message, "warning");
        alert.setFamilia(familia);

        familia = FamilyDao.updateFamilia(familia);

        familia.addAlert(alert);

        // Save the Alert
        AlertDao.saveAlert(alert);
    }

    private static boolean isUserPartOfFamily(Familia familia, User user) {
        for (User familyUser : familia.getUsers()) {
            if (familyUser.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public static List<User> getMembersOfFamily(int familyId) {
        return FamilyDao.getMembersOfFamily(familyId);
    }
}
