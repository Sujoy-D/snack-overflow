package use_case.add_recipe;

/**
 * Input boundary for the Add Recipe use case.
 * Defines the method required to initiate the add-recipe process.
 */
public interface AddRecipeInputBoundary {

    /**
     * Executes the Add Recipe use case.
     *
     * @param inputData the data required to add a new recipe
     */
    void execute(AddRecipeInputData inputData);
}
