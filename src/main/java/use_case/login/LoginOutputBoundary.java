package use_case.login;

/**
 * Output boundary for the Login Use Case.
 * This interface defines the contract for presenting login results to the user interface.
 * Following Clean Architecture principles, this boundary allows the use case
 * to communicate results back to the presentation layer without direct dependencies.
 * The Presenter will implement this interface to format and display login outcomes.
 */
public interface LoginOutputBoundary {
    
    /**
     * Prepares and presents the success view after a successful login attempt.
     * This method is called when user authentication is successful and the user
     * should be navigated to the appropriate authenticated view.
     * 
     * @param outputData the login output data containing user information and success details
     * @throws IllegalArgumentException if outputData is null
     */
    void prepareSuccessView(LoginOutputData outputData);
    
    /**
     * Prepares and presents the failure view after an unsuccessful login attempt.
     * This method is called when user authentication fails due to invalid credentials,
     * account issues, or system errors. The error message should be displayed to the user
     * in an appropriate format.
     * 
     * @param errorMessage a descriptive error message explaining why the login failed
     * @throws IllegalArgumentException if errorMessage is null or empty
     */
    void prepareFailView(String errorMessage);
}
