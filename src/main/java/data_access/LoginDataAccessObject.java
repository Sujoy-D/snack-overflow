package data_access;

import use_case.login.LoginDataAccessInterface;

/**
 * Data Access Object for Login functionality.
 * Implements the LoginDataAccessInterface using the existing UserRepository.
 */
public class LoginDataAccessObject implements LoginDataAccessInterface {
    
    private final UserDataAccessObject userDataAccessObject;
    
    public LoginDataAccessObject(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }
    
    @Override
    public boolean validateLogin(String username, String password) {
        try {
            return userDataAccessObject.validateLogin(username, password);
        } catch (Exception e) {
            // Log the exception (in a real application)
            return false;
        }
    }
    
    @Override
    public void updateLastLogin(String username) {
        try {
            userDataAccessObject.updateLastLogin(username);
        } catch (Exception e) {
            // Log the exception (in a real application)
            // This is non-critical, so we don't throw the exception
        }
    }
}
