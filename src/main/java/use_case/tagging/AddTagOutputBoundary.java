package use_case.tagging;

/**
 * Output boundary for the Add Tag use case.
 * The Presenter implements this interface
 */
public interface AddTagOutputBoundary {
    void prepareSuccessView(AddTagOutputData tagData);
    void prepareFailView(String errorMessage);
}
