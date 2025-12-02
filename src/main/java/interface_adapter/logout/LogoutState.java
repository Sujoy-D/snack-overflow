package interface_adapter.logout;

/**
 * State for the Logout functionality.
 * Contains the current state of logout operation.
 */
public class LogoutState {
    
    private boolean logoutInProgress;
    private String message;
    
    public LogoutState() {

    }
    
    public boolean isLogoutInProgress() {
        return logoutInProgress;
    }
    
    public void setLogoutInProgress(boolean logoutInProgress) {
        this.logoutInProgress = logoutInProgress;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
