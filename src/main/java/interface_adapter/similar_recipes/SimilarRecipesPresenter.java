package interface_adapter.similar_recipes;

import use_case.similar_recipes.SimilarRecipesOutputBoundary;
import use_case.similar_recipes.SimilarRecipesOutputData;

/**
 * Presenter for the Meal Plan use case.
 * Converts SimilarRecipesOutputData into SimilarRecipesState for the ViewModel.
 */
public class SimilarRecipesPresenter implements SimilarRecipesOutputBoundary {

    private final SimilarRecipesViewModel viewModel;

    public SimilarRecipesPresenter(SimilarRecipesViewModel viewModel) {this.viewModel = viewModel;}

    @Override
    public void prepareSuccessView(SimilarRecipesOutputData outputData) {
        SimilarRecipesState newState = new SimilarRecipesState();
        newState.setErrorMessage(null);
        newState.setSimilarRecipes(outputData.getSimilarRecipes());

        // Update ViewModel
        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        SimilarRecipesState newState = new SimilarRecipesState();
        newState.setErrorMessage(errorMessage);

        // Update ViewModel
        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }
}
