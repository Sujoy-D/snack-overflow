package use_case.save_recipe;

/**
 * Output Boundary for the Save Recipe Use Case.
 */
public interface SaveRecipeOutputBoundary {
	void prepareSuccessView(SaveRecipeOutputData outputData);
	void prepareFailView(String errorMessage);
}
