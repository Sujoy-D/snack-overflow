package use_case.save_recipe;

/**
 * Output Boundary for the Save Recipe Use Case.
 */
public interface SaveRecipeOutputBoundary {

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data containing information about the saved recipe
     */
    void prepareSuccessView(SaveRecipeOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage a descriptive error message explaining why the save operation failed
     */
    void prepareFailView(String errorMessage);
}
