package use_case.add_recipe;

public class AddRecipeOutputData {
    private final boolean success;
    private final String message;

    public AddRecipeOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }

}
