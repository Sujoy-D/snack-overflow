package use_case.login;

/**
 * Input boundary for the Login Use Case.
 * The Interactor will implement this.
 */
public interface LoginInputBoundary {
    void execute(LoginInputData inputData);
}
