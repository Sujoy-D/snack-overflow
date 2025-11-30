package use_case.logout;

/**
 * Output boundary for the Logout Use Case.
 * The Presenter will implement this.
 */
public interface LogoutOutputBoundary {
    
    void prepareSuccessView(LogoutOutputData outputData);
    
    void prepareFailView(String errorMessage);
}
