package data_access;

import entity.Recipe;
import use_case.save_recipe.SaveRecipeDataAccessInterface;
import use_case.add_recipe.AddRecipeDataAccessInterface;
import use_case.user_management.UserDataAccessInterface;
import java.util.List;

/**
 * Data Access Object for recipe operations.
 * Handles both adding and saving recipes using the underlying user data access.
 */
public class RecipeDataAccessObject implements AddRecipeDataAccessInterface, SaveRecipeDataAccessInterface {
	private final UserDataAccessInterface userDataAccess;

	public RecipeDataAccessObject() {
		this.userDataAccess = new UserDataAccessObject();
	}

	// For dependency injection - preferred constructor using interface
	public RecipeDataAccessObject(UserDataAccessInterface userDataAccess) {
		this.userDataAccess = userDataAccess;
	}

	// AddRecipeDataAccessInterface implementation
	@Override
	public void saveRecipe(String username, Recipe recipe) {
		userDataAccess.saveRecipeForUser(username, recipe);
	}

	@Override
	public List<Recipe> loadRecipes(String username) {
		return userDataAccess.getSavedRecipesForUser(username);
	}

	// SaveRecipeDataAccessInterface implementation
	@Override
	public boolean saveRecipeForUser(String username, Recipe recipe) {
		return userDataAccess.saveRecipeForUser(username, recipe);
	}

	@Override
	public boolean isRecipeSaved(String username, Integer recipeId) {
		return userDataAccess.isRecipeSaved(username, recipeId);
	}

	/**
	 * Get all saved recipes for a user.
	 *
	 * @param username the username
	 * @return list of saved recipes
	 */
	public List<Recipe> getSavedRecipesForUser(String username) {
		return userDataAccess.getSavedRecipesForUser(username);
	}
}
