package use_case.save_recipe;

import entity.Recipe;

/**
 * Data Access Interface for saving recipes.
 */
public interface SaveRecipeDataAccessInterface {
    /**
     * Save a recipe for a specific user.
     *
     * @param username
     *            the username of the user
     * @param recipe
     *            the recipe to save
     * @return true if successful, false otherwise
     */
    boolean saveRecipeForUser(String username, Recipe recipe);

    /**
     * Check if a recipe is already saved by the user.
     *
     * @param username
     *            the username of the user
     * @param recipeId
     *            the ID of the recipe
     * @return true if recipe is already saved, false otherwise
     */
    boolean isRecipeSaved(String username, Integer recipeId);
}
