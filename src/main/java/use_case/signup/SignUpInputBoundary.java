package use_case.signup;

/**
 * Input boundary for the Sign Up Use Case.
 * The Interactor will implement this.
 */
public interface SignUpInputBoundary {
    void execute(SignUpInputData inputData);
}
