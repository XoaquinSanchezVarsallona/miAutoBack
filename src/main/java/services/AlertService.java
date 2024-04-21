package services;

import entities.Alert;

import java.util.List;
import dao.AlertDao;

public class AlertService {
    public static List<Alert> getAlertsByFamilyApellido(String familyName) {
        return AlertDao.getAlertsByFamilyName(familyName);
    }
}
