package interface_adapter.signup;

/**
 * State for the Sign Up View.
 * Contains the current state of the sign up form and any messages to display.
 */
public class SignUpState {
    
    private String username = "";
    private String password = "";
    private String email = "";
    private String errorMessage = null;
    private String successMessage = null;
    private boolean signUpInProgress = false;
    
    public SignUpState() {}
    
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getSuccessMessage() {
        return successMessage;
    }
    
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    
    public boolean isSignUpInProgress() {
        return signUpInProgress;
    }
    
    public void setSignUpInProgress(boolean signUpInProgress) {
        this.signUpInProgress = signUpInProgress;
    }
}
