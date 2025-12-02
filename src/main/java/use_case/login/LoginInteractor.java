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
        final String username = inputData.getUsername();
        final String password = inputData.getPassword();

        String errorMessage = null;

        // Validate input
        if (username == null || username.trim().isEmpty()) {
            errorMessage = "Username cannot be empty";
        }
        else if (password == null || password.trim().isEmpty()) {
            errorMessage = "Password cannot be empty";
        }

        if (errorMessage != null) {
            presenter.prepareFailView(errorMessage);
        }
        else {
            try {
                // Attempt to validate credentials
                final boolean isValid = loginDataAccess.validateLogin(username, password);

                if (isValid) {
                    // Update last login timestamp
                    loginDataAccess.updateLastLogin(username);

                    // Prepare success response
                    final LoginOutputData outputData = new LoginOutputData(
                            username, true, "Login successful");
                    presenter.prepareSuccessView(outputData);
                }
                else {
                    presenter.prepareFailView("Invalid username or password");
                }
            }
            catch (IllegalArgumentException error) {
                presenter.prepareFailView("Login failed due to system error");
            }
        }
    }
}
