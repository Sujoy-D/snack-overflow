package data_access;

import use_case.login.LoginDataAccessInterface;

/**
 * Data Access Object for Login functionality.
 * Implements the LoginDataAccessInterface using the existing UserRepository.
 */
public class LoginDataAccessObject implements LoginDataAccessInterface {
    
    private final UserRepository userRepository;
    
    public LoginDataAccessObject(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public boolean validateLogin(String username, String password) {
        try {
            return userRepository.validateLogin(username, password);
        } catch (Exception e) {
            // Log the exception (in a real application)
            return false;
        }
    }
    
    @Override
    public void updateLastLogin(String username) {
        try {
            userRepository.updateLastLogin(username);
        } catch (Exception e) {
            // Log the exception (in a real application)
            // This is non-critical, so we don't throw the exception
        }
    }
}
