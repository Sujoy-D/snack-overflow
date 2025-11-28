package use_case.tagging;

/**
 * Output data for the Add Tag use case.
 */
public class AddTagOutputData {
    private final boolean successfulTag;
    private final String message;

    public AddTagOutputData(boolean successfulTag, String message) {
        this.successfulTag = successfulTag;
        this.message = message;
    }
    public boolean isSuccess() {
        return successfulTag;
    }
    public String getMessage() {
        return message;
    }
}
