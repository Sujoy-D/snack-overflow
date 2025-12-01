package data_access;

import use_case.logout.LogoutDataAccessInterface;

/**
 * Data Access Object for Logout functionality.
 * Implements the LogoutDataAccessInterface using the existing UserDataAccess.
 */
public class LogoutDataAccessObject implements LogoutDataAccessInterface {
    
    private final UserDataAccessObject userDataAccessObject;
    
    public LogoutDataAccessObject(UserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }
    
    @Override
    public void clearUserSession(String username) {
        try {
            // Clear any cached user data or session information
            // This could include clearing stored preferences, temporary data, etc.
            // For now, this is a placeholder - in a real app you might:
            // - Clear authentication tokens
            // - Clear cached user data
            // - Close database connections specific to the user
            // - Clear any temporary files
            
            // Log the session clearing (in a real app, use proper logging)
            System.out.println("Session cleared for user: " + username);
        } catch (Exception e) {
            // Log the exception but don't throw it as session clearing is not critical
            System.err.println("Error clearing session for user " + username + ": " + e.getMessage());
        }
    }
    
    @Override
    public void updateLastLogout(String username) {
        try {
            // Update the last logout timestamp in the database
            // This is optional functionality - the UserRepository doesn't currently
            // have a method for this, but we can add it if needed
            
            // For now, just log the logout (in a real app, use proper logging)
            System.out.println("User logged out: " + username + " at " + java.time.LocalDateTime.now());
        } catch (Exception e) {
            // Log the exception but don't throw it as this is not critical
            System.err.println("Error updating logout time for user " + username + ": " + e.getMessage());
        }
    }
}
