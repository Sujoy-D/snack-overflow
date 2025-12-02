package use_case.logout;

/**
 * Input boundary for the Logout Use Case.
 * The Interactor will implement this.
 */
public interface LogoutInputBoundary {
    /**
     * Executes the logout use case with the provided input data.
     *
     * @param inputData the logout input data containing user information
     */
    void execute(LogoutInputData inputData);
}
