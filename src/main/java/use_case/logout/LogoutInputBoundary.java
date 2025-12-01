package use_case.logout;

/**
 * Input boundary for the Logout Use Case.
 * The Interactor will implement this.
 */
public interface LogoutInputBoundary {
    void execute(LogoutInputData inputData);
}
