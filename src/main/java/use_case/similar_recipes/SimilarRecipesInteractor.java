package use_case.similar_recipes;

/**
 * Interactor for the Similar Recipes Use Case.
 */
public class SimilarRecipesInteractor implements SimilarRecipesInputBoundary {
    private final SimilarRecipesDataAccessInterface similarRecipesDataAccessObject;
    private final SimilarRecipesOutputBoundary similarRecipesPresenter;

    public SimilarRecipesInteractor(SimilarRecipesDataAccessInterface similarRecipesDataAccessObject,
                                    SimilarRecipesOutputBoundary similarRecipesPresenter) {
        this.similarRecipesDataAccessObject = similarRecipesDataAccessObject;
        this.similarRecipesPresenter = similarRecipesPresenter;
    }

    @Override
    public void execute(SimilarRecipesInputData similarRecipesInputData) {

        try {

            final SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(
                    similarRecipesDataAccessObject.getSimilarRecipeID(similarRecipesInputData.getRecipeID()));

            if (outputData.getSimilarRecipes() == null) {
                throw new Exception();
            }

            similarRecipesPresenter.prepareSuccessView(outputData);

        }
        catch (Exception ex) {
            similarRecipesPresenter.prepareFailView("Failed to get similar recipes: " + ex.getMessage());
        }
    }
}
