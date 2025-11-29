package use_case.tagging;

/**
 * Input data for the Add Tag use case.
 */
public class AddTagInputData {
    private final String username;
    private final int recipeId;
    private final String tagName;

    public AddTagInputData(String username, int recipeId, String tagName) {
        this.username = username;
        this.recipeId = recipeId;
        this.tagName = tagName;
    }
    public String getUsername() { return username; }
    public int getRecipeId() {
        return recipeId;
    }
    public String getTagName() {
        return tagName;
    }
}
