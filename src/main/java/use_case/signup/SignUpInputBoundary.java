package use_case.signup;

/**
 * Input boundary for the Sign-Up Use Case.
 * The Interactor will implement this.
 */
public interface SignUpInputBoundary {
    /**
     * Executes the sign-up use case with the provided input data.
     *
     * @param inputData the sign-up input data containing user information
     */
    void execute(SignUpInputData inputData);
}
