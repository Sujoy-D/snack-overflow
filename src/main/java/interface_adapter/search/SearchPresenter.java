package interface_adapter.search;

import use_case.search.SearchRecipesOutputBoundary;
import use_case.search.SearchRecipesOutputData;

/**
 * Presenter that maps search use case output into the SearchViewModel
 */
public class SearchPresenter implements SearchRecipesOutputBoundary {
    private final SearchViewModel viewModel;
    
    public SearchPresenter(SearchViewModel viewModel) {
        this.viewModel = viewModel;
    }
    
    @Override
    public void presentSuccess(SearchRecipesOutputData outputData) {
        viewModel.setSearching(false);
        viewModel.setErrorMessage(null);
        viewModel.setRecipes(outputData.getRecipes());
    }
    
    @Override
    public void presentFailure(String errorMessage) {
        viewModel.setSearching(false);
        viewModel.setRecipes(null);
        viewModel.setErrorMessage(errorMessage);
    }
}
