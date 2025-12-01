package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Sign Up View.
 * Manages the state of the sign up form and notifies observers of changes.
 */
public class SignUpViewModel extends ViewModel {
    
    public static final String TITLE_LABEL = "Create Your Account";
    public static final String USERNAME_LABEL = "Username:";
    public static final String PASSWORD_LABEL = "Password:";
    public static final String EMAIL_LABEL = "Email (Optional):";
    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String LOGIN_LINK_LABEL = "Already have an account? Login";
    
    private SignUpState state = new SignUpState();
    
    public SignUpViewModel() {
        super("signup");
    }
    
    public SignUpState getState() {
        return state;
    }
    
    public void setState(SignUpState state) {
        this.state = state;
    }
}
