package services;

import dao.UserDao;
import entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import utils.PasswordUtilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String email, String password) {
        User user = userDao.findUserByEmail(email);

        if (user != null && PasswordUtilities.passwordValidation(user.getPassword(), password, user.getUserType())) {
            return user;
        }
        return null;
    }

    public boolean registerUser(User user) {
        if (!UserDao.userExists(user.getEmail(), user.getUsername())) {
            userDao.saveUser(user);
            return true;
        }
        return false;
    }

    public User getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}
