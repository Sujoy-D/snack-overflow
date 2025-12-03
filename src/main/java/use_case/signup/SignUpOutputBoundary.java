package use_case.signup;

/**
 * Output boundary for the Sign-Up Use Case.
 * The Presenter will implement this.
 */
public interface SignUpOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the sign-up output data containing user information
     */
    void prepareSuccessView(SignUpOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage a descriptive error message explaining why the sign-up operation failed
     */
    void prepareFailView(String errorMessage);
}
