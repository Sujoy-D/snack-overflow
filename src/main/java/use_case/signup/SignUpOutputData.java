package use_case.signup;

/**
 * Output Data for the Sign Up Use Case.
 */
public class SignUpOutputData {
    
    private final String username;
    private final boolean success;
    private final String message;
    
    public SignUpOutputData(String username, boolean success, String message) {
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
