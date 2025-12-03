package use_case.save_recipe;

/**
 * Input Boundary for the Save Recipe Use Case.
 */
public interface SaveRecipeInputBoundary {
    /**
     * Executes the save recipe use case with the provided input data.
     *
     * @param saveRecipeInputData the input data containing recipe and user information
     */
    void execute(SaveRecipeInputData saveRecipeInputData);
}
