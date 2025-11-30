package interface_adapter.tagging;

/**
 * State for the Tagging and Add Tag feature.
 */
public class TaggingState {

    private String message = "";
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
