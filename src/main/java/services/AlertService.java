package services;

import entities.Alert;

import java.util.List;
import dao.AlertDao;

public class AlertService {
    public static List<Alert> getAlertsByFamilyApellido(String familyApellido) {
        return AlertDao.getAlertsByFamilyApellido(familyApellido);
    }

    public static boolean deleteAlert(Long idAlert) {
        return AlertDao.deleteAlert(idAlert);
    }
}
