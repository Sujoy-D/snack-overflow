package interface_adapter.login;

import interface_adapter.navigation.NavigationController;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the Login Use Case.
 * Converts LoginOutputData into LoginState for the ViewModel and handles navigation.
 */
public class LoginPresenter implements LoginOutputBoundary {
    
    private final LoginViewModel loginViewModel;
    private final NavigationController navigationController;
    
    public LoginPresenter(LoginViewModel loginViewModel, 
                         NavigationController navigationController) {
        this.loginViewModel = loginViewModel;
        this.navigationController = navigationController;
    }
    
    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        // Clear any previous error messages
        final LoginState newState = new LoginState();
        newState.setErrorMessage(null);
        newState.setLoginInProgress(false);
        
        // Update ViewModel
        loginViewModel.setState(newState);
        loginViewModel.firePropertyChanged();
        
        // Navigate to home page
        navigationController.execute("home", outputData.getUsername());
    }
    
    @Override
    public void prepareFailView(String errorMessage) {
        final LoginState currentState = loginViewModel.getState();
        final LoginState newState = new LoginState();
        
        // Preserve form data but show error
        newState.setUsername(currentState.getUsername());
        newState.setPassword("");
        newState.setErrorMessage(errorMessage);
        newState.setLoginInProgress(false);
        
        // Update ViewModel
        loginViewModel.setState(newState);
        loginViewModel.firePropertyChanged();
    }
}
