package use_case.similar_recipes;

/**
 * Interactor for the Similar Recipes Use Case.
 */
public class SimilarRecipesInteractor implements SimilarRecipesInputBoundary {
    private final SimilarRecipeDataAccessInterface similarRecipesDataAccessObject;
    private final SimilarRecipesOutputBoundary similarRecipesPresenter;

    public SimilarRecipesInteractor(SimilarRecipeDataAccessInterface similarRecipesDataAccessObject,
                                    SimilarRecipesOutputBoundary similarRecipesPresenter) {
        this.similarRecipesDataAccessObject = similarRecipesDataAccessObject;
        this.similarRecipesPresenter = similarRecipesPresenter;
    }

    @Override
    public void execute(SimilarRecipesInputData similarRecipesInputData) {

        try {

            final SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(
                    similarRecipesDataAccessObject.getSimilarRecipeID(similarRecipesInputData.getRecipeID()));

            similarRecipesPresenter.prepareSuccessView(outputData);

        }
        catch (Exception ex) {
            similarRecipesPresenter.prepareFailView("Failed to get similar recipes: " + ex.getMessage());
        }
    }
}
