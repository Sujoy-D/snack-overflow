package interface_adapter;


import use_case.similar_recipes.SimilarRecipesInputBoundary;
import use_case.similar_recipes.SimilarRecipesInputData;

/**
 * The controller for the Similar Recipes Use Case.
 */
public class SimilarRecipesController {
    private final SimilarRecipesInputBoundary similarRecipesInteractor;
    private final SimilarRecipesInputData similarRecipesInputData;

    public SimilarRecipesController(SimilarRecipesInputBoundary similarRecipesInteractor,
                                    SimilarRecipesInputData similarRecipesInputData) {
        this.similarRecipesInteractor = similarRecipesInteractor;
        this.similarRecipesInputData = similarRecipesInputData;
    }

    /**
     * Executes the Similar Recipes Use Case.
     */
    public void execute() {
        similarRecipesInteractor.execute(similarRecipesInputData);
    }
}
