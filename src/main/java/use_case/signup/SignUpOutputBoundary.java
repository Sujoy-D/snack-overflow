package use_case.signup;

/**
 * Output boundary for the Sign Up Use Case.
 * The Presenter will implement this.
 */
public interface SignUpOutputBoundary {
    
    void prepareSuccessView(SignUpOutputData outputData);
    
    void prepareFailView(String errorMessage);
}
