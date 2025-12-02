package use_case.logout;

/**
 * Output boundary for the Logout Use Case.
 * The Presenter will implement this.
 */
public interface LogoutOutputBoundary {
    /**
     * Prepares the success view for the logout operation.
     *
     * @param outputData the logout output data containing result information
     */
    void prepareSuccessView(LogoutOutputData outputData);

    /**
     * Prepares the failure view for the logout operation.
     *
     * @param errorMessage a descriptive error message explaining why the logout failed
     */
    void prepareFailView(String errorMessage);
}
