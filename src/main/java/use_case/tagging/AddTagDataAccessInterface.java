package use_case.tagging;

import java.util.List;

/**
 * Data access interface for tagging operations.
 * Defines the contract for storing and retrieving recipe tags.
 */
public interface AddTagDataAccessInterface {
    
    /**
     * Add a tag to a recipe for a user.
     *
     * @param username the username
     * @param recipeId the recipe ID
     * @param tagName the tag name
     */
    void addTagToRecipe(String username, int recipeId, String tagName);
    
    /**
     * Get all tags for a specific recipe.
     *
     * @param username the username
     * @param recipeId the recipe ID
     * @return list of tag names for the recipe
     */
    List<String> getTagsForRecipe(String username, int recipeId);
}
