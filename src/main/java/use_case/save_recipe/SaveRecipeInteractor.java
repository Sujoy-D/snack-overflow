package use_case.save_recipe;

/**
 * Interactor for the Save Recipe Use Case.
 */
public class SaveRecipeInteractor implements SaveRecipeInputBoundary {
	private final SaveRecipeDataAccessInterface saveRecipeDataAccess;
	private final SaveRecipeOutputBoundary saveRecipePresenter;

	public SaveRecipeInteractor(SaveRecipeDataAccessInterface saveRecipeDataAccess,
			SaveRecipeOutputBoundary saveRecipePresenter) {
		this.saveRecipeDataAccess = saveRecipeDataAccess;
		this.saveRecipePresenter = saveRecipePresenter;
	}

	@Override
	public void execute(SaveRecipeInputData saveRecipeInputData) {
		final String username = saveRecipeInputData.getUsername();
		final var recipe = saveRecipeInputData.getRecipe();

		try {
			// Check if recipe is already saved
			if (saveRecipeDataAccess.isRecipeSaved(username, recipe.getRecipeId())) {
                saveRecipePresenter.prepareFailView("Recipe '" + recipe.getTitle() + "' is already saved!");
				return;
			}

			// Save the recipe
			final boolean success = saveRecipeDataAccess.saveRecipeForUser(username, recipe);

			if (success) {
                final SaveRecipeOutputData outputData = new SaveRecipeOutputData(true, "Recipe saved successfully!",
						recipe.getTitle());
				saveRecipePresenter.prepareSuccessView(outputData);
			}
            else {
				saveRecipePresenter.prepareFailView("Failed to save recipe. Please try again.");
			}
		}
        catch (Exception error) {
			saveRecipePresenter.prepareFailView("Error saving recipe: " + error.getMessage());
		}
	}
}
