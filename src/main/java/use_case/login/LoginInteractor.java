package use_case.login;

/**
 * Interactor for the Login Use Case.
 * Contains the business logic for user authentication.
 */
public class LoginInteractor implements LoginInputBoundary {
    
    private final LoginDataAccessInterface loginDataAccess;
    private final LoginOutputBoundary presenter;
    
    public LoginInteractor(LoginDataAccessInterface loginDataAccess, 
                          LoginOutputBoundary presenter) {
        this.loginDataAccess = loginDataAccess;
        this.presenter = presenter;
    }
    
    @Override
    public void execute(LoginInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            presenter.prepareFailView("Username cannot be empty");
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            presenter.prepareFailView("Password cannot be empty");
            return;
        }
        
        try {
            // Attempt to validate credentials
            boolean isValid = loginDataAccess.validateLogin(username, password);
            
            if (isValid) {
                // Update last login timestamp
                loginDataAccess.updateLastLogin(username);
                
                // Prepare success response
                LoginOutputData outputData = new LoginOutputData(
                    username, true, "Login successful");
                presenter.prepareSuccessView(outputData);
            } else {
                presenter.prepareFailView("Invalid username or password");
            }
        } catch (Exception e) {
            presenter.prepareFailView("Login failed due to system error");
        }
    }
}
