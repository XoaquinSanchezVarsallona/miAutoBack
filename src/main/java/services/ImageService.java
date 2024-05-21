package services;
import dao.ImageDao;
public class ImageService {

    public static void saveImage(String userID, String patente, String fields, String image) {
        if (patente.isEmpty()) ImageDao.saveDNI(userID, fields, image);
        else ImageDao.saveRegistration(userID, patente, fields, image);
    }
}
