package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Login View.
 * Manages the state of the login form and notifies observers of changes.
 */
public class LoginViewModel extends ViewModel {
    
    public static final String TITLE_LABEL = "Welcome to Snack Overflow";
    public static final String USERNAME_LABEL = "Username:";
    public static final String PASSWORD_LABEL = "Password:";
    public static final String LOGIN_BUTTON_LABEL = "Login";
    public static final String SIGNUP_LINK_LABEL = "Don't have an account? Sign up";
    
    private LoginState state = new LoginState();
    
    public LoginViewModel() {
        super("login");
    }
    
    public LoginState getState() {
        return state;
    }
    
    public void setState(LoginState state) {
        this.state = state;
    }
}
