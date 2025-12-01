package use_case.login;

/**
 * Data Access Interface for the Login Use Case.
 * This defines what data operations are needed for login functionality.
 */
public interface LoginDataAccessInterface {
    
    /**
     * Validate user credentials.
     * @param username the username to validate
     * @param password the password to validate
     * @return true if credentials are valid, false otherwise
     */
    boolean validateLogin(String username, String password);
    
    /**
     * Update the last login timestamp for a user.
     * @param username the username of the user who logged in
     */
    void updateLastLogin(String username);
}
