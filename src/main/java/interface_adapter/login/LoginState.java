package interface_adapter.login;

/**
 * State for the Login View.
 * Contains the current state of the login form and any messages to display.
 */
public class LoginState {
    
    private String username = "";
    private String password = "";
    private String errorMessage;
    private boolean loginInProgress;

    public LoginState() {

    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public boolean isLoginInProgress() {
        return loginInProgress;
    }
    
    public void setLoginInProgress(boolean loginInProgress) {
        this.loginInProgress = loginInProgress;
    }
}
