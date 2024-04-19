package services;

import dao.UserDao;
import entities.User;
import utils.PasswordUtilities;

public class UserService {

    public static boolean updateUserField(Long userId, String field, String newValue) {
        //updatea el userField.
        //System.out.println("UserService.updateUserField called");

        return UserDao.updateUserField(userId, field, newValue);
    }

    public User login(String email, String password) {
        User user = UserDao.findUserByEmail(email);

        if (user != null && PasswordUtilities.passwordValidation(user.getPassword(), password, user.getUserType())) {
            return user;
        }
        return null;
    }

    public static boolean registerUser(User user) {
        if (!UserDao.userExists(user.getEmail(), user.getUsername())) {
            UserDao.saveUser(user);
            return true;
        }
        return false;
    }
}
