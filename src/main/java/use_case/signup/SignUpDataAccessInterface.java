package use_case.signup;

/**
 * Data Access Interface for the Sign Up Use Case.
 * This defines what data operations are needed for sign up functionality.
 */
public interface SignUpDataAccessInterface {
    
    /**
     * Save a new user to the system.
     * @param username the username
     * @param password the password (will be hashed by implementation)
     * @param email the user's email
     * @throws RuntimeException if username already exists or other error occurs
     */
    void saveUser(String username, String password, String email);
    
    /**
     * Check if a username already exists.
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    boolean userExists(String username);
}
