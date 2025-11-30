package data_access;

import java.util.List;

public interface TaggingDataAccessInterface {
    void addTagToRecipe(String username, int recipeId, String tagName);
    List<String> getTagsForRecipe(String username, int recipeId);
}
