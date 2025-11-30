package use_case.signup;

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
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String email = inputData.getEmail();
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            presenter.prepareFailView("Username cannot be empty");
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            presenter.prepareFailView("Password cannot be empty");
            return;
        }
        
        // Additional validation rules
        if (username.length() < 3) {
            presenter.prepareFailView("Username must be at least 3 characters long");
            return;
        }
        
        if (password.length() < 6) {
            presenter.prepareFailView("Password must be at least 6 characters long");
            return;
        }
        
        try {
            // Check if username already exists
            if (signUpDataAccess.userExists(username)) {
                presenter.prepareFailView("Username already exists");
                return;
            }
            
            // Save the new user
            signUpDataAccess.saveUser(username, password, email != null ? email : "");
            
            // Prepare success response
            SignUpOutputData outputData = new SignUpOutputData(
                username, true, "Account created successfully!");
            presenter.prepareSuccessView(outputData);
            
        } catch (Exception e) {
            presenter.prepareFailView("Sign up failed due to system error");
        }
    }
}
