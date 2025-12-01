package data_access;

import use_case.signup.SignUpDataAccessInterface;

/**
 * Data Access Object for Sign Up functionality.
 * Implements the SignUpDataAccessInterface using the existing UserRepository.
 */
public class SignUpDataAccessObject implements SignUpDataAccessInterface {
    
    private final UserDataAccessObject userDataAccessObject;
    
    public SignUpDataAccessObject(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }
    
    @Override
    public void saveUser(String username, String password, String email) {
        try {
            userDataAccessObject.saveUser(username, password, email);
        } catch (RuntimeException e) {
            // Re-throw runtime exceptions (like username already exists)
            throw e;
        } catch (Exception e) {
            // Wrap other exceptions
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean userExists(String username) {
        try {
            return userDataAccessObject.userExists(username);
        } catch (Exception e) {
            // If we can't check, assume user doesn't exist but log the error
            // In a real application, you'd want proper logging here
            return false;
        }
    }
}
