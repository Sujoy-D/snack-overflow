package interface_adapter.signup;

import use_case.signup.SignUpOutputBoundary;
import use_case.signup.SignUpOutputData;
import interface_adapter.navigation.NavigationController;
import javax.swing.Timer;

/**
 * Interface for handling delayed navigation in the Sign Up use case.
 */
interface SignUpDelayedNavigator {
    void navigateAfterDelay(int delayMs, Runnable navigationAction);
}

/**
 * Default implementation that uses Swing Timer for delayed navigation.
 */
class DefaultSignUpDelayedNavigator implements SignUpDelayedNavigator {
    @Override
    public void navigateAfterDelay(int delayMs, Runnable navigationAction) {
        Timer timer = new Timer(delayMs, e -> navigationAction.run());
        timer.setRepeats(false);
        timer.start();
    }
}

/**
 * Presenter for the Sign Up Use Case.
 * Converts SignUpOutputData into SignUpState for the ViewModel and handles navigation.
 */
public class SignUpPresenter implements SignUpOutputBoundary {
    
    private final SignUpViewModel signUpViewModel;
    private final NavigationController navigationController;
    private final SignUpDelayedNavigator delayedNavigator;
    
    public SignUpPresenter(SignUpViewModel signUpViewModel, 
                          NavigationController navigationController) {
        this.signUpViewModel = signUpViewModel;
        this.navigationController = navigationController;
        this.delayedNavigator = new DefaultSignUpDelayedNavigator();
    }

    // Constructor for testing with custom delayed navigator
    public SignUpPresenter(SignUpViewModel signUpViewModel, 
                          NavigationController navigationController,
                          SignUpDelayedNavigator delayedNavigator) {
        this.signUpViewModel = signUpViewModel;
        this.navigationController = navigationController;
        this.delayedNavigator = delayedNavigator;
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
        delayedNavigator.navigateAfterDelay(2000, () -> {
            navigationController.execute("login", null);
        });
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
