package services;

import dao.FamilyDao;

import entities.Familia;
import entities.User;
import java.util.List;

public class FamilyService {

    public static void createFamily(String username , String apellido) {
        User user = FamilyDao.lookForUser(username);
        List<Familia> familias = FamilyDao.getFamiliasOfUser(username);
        for (Familia familia : familias) {
            if (familia.getApellido().equals(apellido)) throw  new IllegalArgumentException("Apellido has already been used");
        }
        Familia family = new Familia(apellido);
        // Comienza la transacción
        FamilyDao.saveFamily(family);
        family.addUser(user);
        user.addFamily(family);
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
}
