package use_case.similar_recipes;

/**
 * Input boundary for the Similar Recipes Use Case.
 */
public interface SimilarRecipesInputBoundary {
    /**
     * Executes the Similar Recipes use case. After this executes,
     * similar recipes will be displayed below the current recipe.
     * @param similarRecipesInputData the input data bundled for the Interactor of this use case. Contains a recipe ID.
     */
    void execute(SimilarRecipesInputData similarRecipesInputData);
}
