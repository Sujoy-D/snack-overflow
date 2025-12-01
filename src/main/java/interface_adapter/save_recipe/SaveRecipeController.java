package interface_adapter.save_recipe;

import entity.Recipe;
import use_case.save_recipe.SaveRecipeInputBoundary;
import use_case.save_recipe.SaveRecipeInputData;

/**
 * Controller for the Save Recipe Use Case.
 */
public class SaveRecipeController {
	private final SaveRecipeInputBoundary saveRecipeInteractor;

	public SaveRecipeController(SaveRecipeInputBoundary saveRecipeInteractor) {
		this.saveRecipeInteractor = saveRecipeInteractor;
	}

	/**
	 * Executes the save recipe use case.
	 *
	 * @param username
	 *            the username of the current user
	 * @param recipe
	 *            the recipe to save
	 */
	public void execute(String username, Recipe recipe) {
		SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);
		saveRecipeInteractor.execute(inputData);
	}
}
