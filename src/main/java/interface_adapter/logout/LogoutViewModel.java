package interface_adapter.logout;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Logout functionality.
 * Manages the state of logout operations and notifies observers of changes.
 */
public class LogoutViewModel extends ViewModel {
    
    public static final String LOGOUT_BUTTON_LABEL = "Logout";
    
    private LogoutState state = new LogoutState();
    
    public LogoutViewModel() {
        super("logout");
    }
    
    public LogoutState getState() {
        return state;
    }
    
    public void setState(LogoutState state) {
        this.state = state;
    }
}
