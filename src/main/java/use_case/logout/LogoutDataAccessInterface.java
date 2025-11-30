package use_case.logout;

/**
 * Data Access Interface for the Logout Use Case.
 * This defines what data operations are needed for logout functionality.
 */
public interface LogoutDataAccessInterface {
    
    /**
     * Clear any session data for the user.
     * This might include clearing cached data, updating last logout time, etc.
     * @param username the username of the user logging out
     */
    void clearUserSession(String username);
    
    /**
     * Update the last logout timestamp for a user.
     * @param username the username of the user who logged out
     */
    void updateLastLogout(String username);
}
