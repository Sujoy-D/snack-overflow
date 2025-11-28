package use_case.tagging;

/**
 * Input data for the Add Tag use case.
 */
public class AddTagInputData {
    private final int recipeId;
    private final String tagName;

    public AddTagInputData(int recipeId, String tagName) {
        this.recipeId = recipeId;
        this.tagName = tagName;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public String getTagName() {
        return tagName;
    }
}
