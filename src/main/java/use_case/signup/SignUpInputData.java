package use_case.signup;

/**
 * Input Data for the Sign Up Use Case.
 */
public class SignUpInputData {
    
    private final String username;
    private final String password;
    private final String email;
    
    public SignUpInputData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
}
