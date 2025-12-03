package use_case.signup;

import java.util.Objects;

/**
 * Interactor for the Sign Up Use Case.
 * Contains the business logic for user registration.
 */
public class SignUpInteractor implements SignUpInputBoundary {
    
    private final SignUpDataAccessInterface signUpDataAccess;
    private final SignUpOutputBoundary presenter;
    
    public SignUpInteractor(SignUpDataAccessInterface signUpDataAccess, 
                           SignUpOutputBoundary presenter) {
        this.signUpDataAccess = signUpDataAccess;
        this.presenter = presenter;
    }
    
    @Override
    public void execute(SignUpInputData inputData) {
        final String username = inputData.getUsername();
        final String password = inputData.getPassword();
        final String email = inputData.getEmail();
        
        // Validate input and get error message if any
        final String validationError = validateInput(username, password);
        
        if (validationError != null) {
            presenter.prepareFailView(validationError);
        }
        else {
            processSignUp(username, password, email);
        }
    }
    
    private String validateInput(String username, String password) {
        String errorMessage = null;
        
        if (username == null || username.trim().isEmpty()) {
            errorMessage = "Username cannot be empty";
        }
        else if (password == null || password.trim().isEmpty()) {
            errorMessage = "Password cannot be empty";
        }
        else if (username.length() < 3) {
            errorMessage = "Username must be at least 3 characters long";
        }
        else if (password.length() < 6) {
            errorMessage = "Password must be at least 6 characters long";
        }
        
        return errorMessage;
    }
    
    private void processSignUp(String username, String password, String email) {
        try {
            // Check if username already exists
            if (signUpDataAccess.userExists(username)) {
                presenter.prepareFailView("Username already exists");
            }
            else {
                // Save the new user
                final String email1;
                email1 = Objects.requireNonNullElse(email, "");

                signUpDataAccess.saveUser(username, password, email1);

                // Prepare success response
                final SignUpOutputData outputData = new SignUpOutputData(
                    username, true, "Account created successfully!");
                presenter.prepareSuccessView(outputData);
            }
        }
        catch (Exception error) {
            presenter.prepareFailView("Sign up failed due to system error");
        }
    }
}
