package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

/**
 * Controller for the Logout Use Case.
 * Receives logout request from the UI and sends it to the Interactor.
 */
public class LogoutController {
    
    private final LogoutInputBoundary interactor;
    
    public LogoutController(LogoutInputBoundary interactor) {
        this.interactor = interactor;
    }
    
    /**
     * Execute the logout use case.
     * @param username the username of the user to log out
     */
    public void execute(String username) {
        final LogoutInputData inputData = new LogoutInputData(username);
        interactor.execute(inputData);
    }
}
