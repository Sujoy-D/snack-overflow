package use_case.logout;

/**
 * Output Data for the Logout Use Case.
 */
public class LogoutOutputData {
    
    private final String username;
    private final boolean success;
    private final String message;
    
    public LogoutOutputData(String username, boolean success, String message) {
        this.username = username;
        this.success = success;
        this.message = message;
    }
    
    public String getUsername() {
        return username;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
}
