package use_case.logout;

/**
 * Interactor for the Logout Use Case.
 * Contains the business logic for user logout.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    
    private final LogoutDataAccessInterface logoutDataAccess;
    private final LogoutOutputBoundary presenter;
    
    public LogoutInteractor(LogoutDataAccessInterface logoutDataAccess, 
                           LogoutOutputBoundary presenter) {
        this.logoutDataAccess = logoutDataAccess;
        this.presenter = presenter;
    }
    
    @Override
    public void execute(LogoutInputData inputData) {
        final String username = inputData.getUsername();
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            presenter.prepareFailView("No user is currently logged in");
            return;
        }
        
        try {
            // Clear user session data
            logoutDataAccess.clearUserSession(username);
            
            // Update last logout timestamp
            logoutDataAccess.updateLastLogout(username);
            
            // Prepare success response
            final LogoutOutputData outputData = new LogoutOutputData(
                username, true, "Logged out successfully");
            presenter.prepareSuccessView(outputData);
            
        }
        catch (Exception error) {
            presenter.prepareFailView("Logout failed due to system error");
        }
    }
}
