package use_case.login;

/**
 * Output boundary for the Login Use Case.
 * The Presenter will implement this.
 */
public interface LoginOutputBoundary {
    
    void prepareSuccessView(LoginOutputData outputData);
    
    void prepareFailView(String errorMessage);
}
