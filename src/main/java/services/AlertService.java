package services;

import DTOs.PapersToDisplay;
import entities.Alert;

import java.util.List;
import dao.AlertDao;
import entities.Car;
import entities.Familia;
import entities.User;

public class AlertService {
    public static List<Alert> getAlertsByFamilyApellido(String familyApellido) {
        return AlertDao.getAlertsByFamilyApellido(familyApellido);
    }

    public static boolean deleteAlert(Long idAlert) {
        return AlertDao.deleteAlert(idAlert);
    }

    public static void alertAllFamilies(Car car, User user) {
        AlertDao.alertAllFamilies(car,user);
    }

    public static PapersToDisplay getPapersFromUser(User user, Car car) throws IllegalArgumentException {
        String dniCara = user.getDniCara();
        String dniContra = user.getDniContracara();
        String registration = AlertDao.getPapers(user, car);

        return new PapersToDisplay(user, car, dniCara, dniContra, registration);
    }

    public static void setAsRead(Long idAlert) {
        AlertDao.setAsRead(idAlert);
    }

    public static void setAsUnread(Long idAlert) {
        AlertDao.setAsUnread(idAlert);
    }

    public static int countUnreadAlertsOfFamily(String familyApellido) {
        return AlertDao.countUnreadAlertsOfFamily(familyApellido);
    }
}
