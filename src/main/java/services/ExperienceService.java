package services;

import dao.ExperienceDao;
import entities.Experience;

import java.util.List;

public class ExperienceService {
    public static List<Experience> getExperiences() {
        return ExperienceDao.getAllExperiences();
    }

    public static void deleteExperience(long experienceId) {
        ExperienceDao.deleteExperience(experienceId);
    }

    public static void createExperience(Long userId, Long storeId, String patente, String description, int rating, Float price) {
        ExperienceDao.createExperience(userId, storeId, patente, description, rating, price);
    }

}
