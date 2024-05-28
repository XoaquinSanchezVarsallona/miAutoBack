package services;
import DTOs.PapersToDisplay;
import dao.ImageDao;
public class ImageService {

    public static void saveImage(String userID, String patente, String fields, String image) {
        if (!fields.equals("registration")) ImageDao.saveDNI(userID, fields, image);
        else ImageDao.saveRegistration(userID, patente, fields, image);
    }

    public static PapersToDisplay getImages(String userID, String patente) {
        return ImageDao.getImages(userID, patente);
    }
}
