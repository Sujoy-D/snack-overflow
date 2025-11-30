package interface_adapter.logout;

import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;
import interface_adapter.navigation.NavigationController;

/**
 * Presenter for the Logout Use Case.
 * Converts LogoutOutputData into LogoutState for the ViewModel and handles navigation.
 */
public class LogoutPresenter implements LogoutOutputBoundary {
    
    private final LogoutViewModel logoutViewModel;
    private final NavigationController navigationController;
    
    public LogoutPresenter(LogoutViewModel logoutViewModel, 
                          NavigationController navigationController) {
        this.logoutViewModel = logoutViewModel;
        this.navigationController = navigationController;
    }
    
    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        // Update logout state
        LogoutState newState = new LogoutState();
        newState.setLogoutInProgress(false);
        newState.setMessage(outputData.getMessage());
        
        // Update ViewModel
        logoutViewModel.setState(newState);
        logoutViewModel.firePropertyChanged();
        
        // Navigate back to login page
        navigationController.execute("login", null);
    }
    
    @Override
    public void prepareFailView(String errorMessage) {
        LogoutState newState = new LogoutState();
        newState.setLogoutInProgress(false);
        newState.setMessage(errorMessage);
        
        // Update ViewModel
        logoutViewModel.setState(newState);
        logoutViewModel.firePropertyChanged();
    }
}
