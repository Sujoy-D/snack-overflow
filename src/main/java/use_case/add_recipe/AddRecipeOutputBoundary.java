package use_case.add_recipe;

/**
 * Output boundary for the Add Recipe use case.
 * Defines how the response data is presented to the user interface layer.
 */
public interface AddRecipeOutputBoundary {

    /**
     * Presents the outcome of the Add Recipe use case.
     *
     * @param outputData the data to present
     */
    void present(AddRecipeOutputData outputData);
}
