package use_case.similar_recipes;

import use_case.similar_recipes.SimilarRecipeDataAccessInterface;

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
        // TODO: potential expansion by adding recipes and similars to cache

        try {

            SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(
                    similarRecipesDataAccessObject.getSimilarRecipeID(similarRecipesInputData.getRecipeID()));

            similarRecipesPresenter.prepareSuccessView(outputData);

        } catch (Exception e) {
            similarRecipesPresenter.prepareFailView("Failed to get similar recipes: " + e.getMessage());
        }
    }

}
