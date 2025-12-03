package use_case.login;

/**
 * Input boundary for the Login Use Case.
 * This interface defines the contract for the login use case interactor.
 * Following Clean Architecture principles, this boundary separates the
 * presentation layer from the business logic layer.
 * The Interactor will implement this interface to handle login operations.
 */
public interface LoginInputBoundary {
    
    /**
     * Executes the login use case with the provided input data.
     *
     * @param inputData the login input data containing username and password
     */
    void execute(LoginInputData inputData);
}
