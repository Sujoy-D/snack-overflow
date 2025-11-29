package use_case.tagging;

import java.util.List;

/**
 * Output data for the Add Tag use case.
 */
public class AddTagOutputData {
    private final int recipeId;
    private final String newTag;
    private final List<String> allTags;

    public AddTagOutputData(int recipeId, String newTag, List<String> allTags) {
        this.recipeId = recipeId;
        this.newTag = newTag;
        this.allTags = allTags;
    }
    public int getRecipeId() {
        return recipeId;
    }
    public String getNewTag() {
        return newTag;
    }
    public List<String> getAllTags() { return allTags; }
}
