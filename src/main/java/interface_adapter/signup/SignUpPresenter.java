package interface_adapter.signup;

import use_case.signup.SignUpOutputBoundary;
import use_case.signup.SignUpOutputData;
import interface_adapter.navigation.NavigationController;
import javax.swing.Timer;

/**
 * Presenter for the Sign Up Use Case.
 * Converts SignUpOutputData into SignUpState for the ViewModel and handles navigation.
 */
public class SignUpPresenter implements SignUpOutputBoundary {
    
    private final SignUpViewModel signUpViewModel;
    private final NavigationController navigationController;
    
    public SignUpPresenter(SignUpViewModel signUpViewModel, 
                          NavigationController navigationController) {
        this.signUpViewModel = signUpViewModel;
        this.navigationController = navigationController;
    }
    
    @Override
    public void prepareSuccessView(SignUpOutputData outputData) {
        // Clear any previous error messages and show success
        SignUpState newState = new SignUpState();
        newState.setErrorMessage(null);
        newState.setSuccessMessage(outputData.getMessage());
        newState.setSignUpInProgress(false);
        
        // Update ViewModel
        signUpViewModel.setState(newState);
        signUpViewModel.firePropertyChanged();
        
        // After a short delay, navigate back to login
        Timer timer = new Timer(2000, e -> {
            navigationController.execute("login", null);
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    @Override
    public void prepareFailView(String errorMessage) {
        SignUpState currentState = signUpViewModel.getState();
        SignUpState newState = new SignUpState();
        
        // Preserve form data but show error and clear password for security
        newState.setUsername(currentState.getUsername());
        newState.setEmail(currentState.getEmail());
        newState.setPassword("");  // Clear password for security
        newState.setErrorMessage(errorMessage);
        newState.setSuccessMessage(null);
        newState.setSignUpInProgress(false);
        
        // Update ViewModel
        signUpViewModel.setState(newState);
        signUpViewModel.firePropertyChanged();
    }
}
