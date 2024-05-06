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
import javax.validation.ConstraintViolationException;
import java.util.List;
import dao.AlertDao;
import extras.IncorrectPasswordException;

public class FamilyService {

    public static void createFamily(String username , String apellido, String password) {
        User user = FamilyDao.lookForUser(username);
        List<Familia> familias = FamilyDao.getFamiliasOfUser(username);
        // Me fijo que no exista una familia con el ismo apellido en la db
        for (Familia familia : familias) {
            if (familia.getApellido().equals(apellido)) throw new IllegalArgumentException("Apellido has already been used");
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
        System.out.println("FAMILIAAAA: " + familia);

        // Find the User entity by username
        System.out.println("voy a buscar el user ");

        User user = UserDao.findUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        System.out.println("USERRRR: " + user);

        // Check if the user is part of the family
        // Check if the user is part of the family
        assert familia != null;
        boolean isUserPartOfFamily = false;
        for (User familyUser : familia.getUsers()) {
            if (familyUser.getUsername().equals(user.getUsername())) {
                isUserPartOfFamily = true;
                break;
            }
        }

        if (!isUserPartOfFamily) {
            throw new IllegalArgumentException("User is not part of the family");
        }

        System.out.println("voy a agregar la alerta a familia ");

        // Create a new Alert
        Alert alert = new Alert(message);
        alert.setFamilia(familia);

        System.out.println("ya la agregué y le agregue la familia ");

        //System.out.println(familia.getAlerts()); //because of the lazy

        familia = FamilyDao.updateFamilia(familia);

        familia.addAlert(alert);

        System.out.println("ya la agregué a la familia ");

        // Save the Alert
        AlertDao.saveAlert(alert);
        System.out.println("ya la guardé ");


    }

    public static List<User> getMembersOfFamily(int familyId) {
        return FamilyDao.getMembersOfFamily(familyId);
    }
}
