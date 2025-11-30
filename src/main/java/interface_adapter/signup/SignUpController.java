package interface_adapter.signup;

import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInputData;

/**
 * Controller for the Sign Up Use Case.
 * Receives raw input from the UI and sends it to the Interactor.
 */
public class SignUpController {
    
    private final SignUpInputBoundary interactor;
    
    public SignUpController(SignUpInputBoundary interactor) {
        this.interactor = interactor;
    }
    
    /**
     * Execute the sign up use case.
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @param email the email entered by the user (optional)
     */
    public void execute(String username, String password, String email) {
        SignUpInputData inputData = new SignUpInputData(username, password, email);
        interactor.execute(inputData);
    }
}
