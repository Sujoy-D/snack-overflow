package use_case.similar_recipes;

public interface SimilarRecipesInputBoundary {
    /**
     * Executes the Similar Recipes use case. After this executes,
     * similar recipes will be displayed below the current recipe.
     */
    void execute(SimilarRecipesInputData similarRecipesInputData);
}
