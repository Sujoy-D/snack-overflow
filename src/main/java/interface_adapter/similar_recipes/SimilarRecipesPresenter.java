package interface_adapter.similar_recipes;

import use_case.similar_recipes.SimilarRecipesOutputBoundary;
import use_case.similar_recipes.SimilarRecipesOutputData;

public class SimilarRecipesPresenter implements SimilarRecipesOutputBoundary {

    @Override
    public void prepareSuccessView(SimilarRecipesOutputData outputData) {

    }

    @Override
    public void prepareFailView(String exceptionMessage) {

    }
}
