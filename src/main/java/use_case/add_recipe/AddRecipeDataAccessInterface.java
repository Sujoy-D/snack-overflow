package use_case.add_recipe;

import entity.Recipe;
import java.util.List;

/**
 * Data access interface for adding recipes.
 * Defines the contract for storing and retrieving recipes.
 */
public interface AddRecipeDataAccessInterface {
    
    /**
     * Save a recipe for a user.
     *
     * @param username the username
     * @param recipe the recipe to save
     */
    void saveRecipe(String username, Recipe recipe);
    
    /**
     * Load all recipes for a user.
     *
     * @param username the username
     * @return list of recipes
     */
    List<Recipe> loadRecipes(String username);
}
