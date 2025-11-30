package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * Controller for the Login Use Case.
 * Receives raw input from the UI and sends it to the Interactor.
 */
public class LoginController {
    
    private final LoginInputBoundary interactor;
    
    public LoginController(LoginInputBoundary interactor) {
        this.interactor = interactor;
    }
    
    /**
     * Execute the login use case.
     * @param username the username entered by the user
     * @param password the password entered by the user
     */
    public void execute(String username, String password) {
        LoginInputData inputData = new LoginInputData(username, password);
        interactor.execute(inputData);
    }
}
