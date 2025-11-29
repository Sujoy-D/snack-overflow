package interface_adapter.search;

import use_case.search.SearchFilters;
import use_case.search.SearchRecipesInputBoundary;
import use_case.search.SearchRecipesInputData;

/**
 * Controller for triggering recipe searches from the UI
 */
public class SearchController {
    private final SearchRecipesInputBoundary interactor;
    private final SearchViewModel viewModel;
    private final int defaultNumberOfResults;
    
    public SearchController(SearchRecipesInputBoundary interactor,
                            SearchViewModel viewModel,
                            int defaultNumberOfResults) {
        this.interactor = interactor;
        this.viewModel = viewModel;
        this.defaultNumberOfResults = defaultNumberOfResults;
    }
    
    public void search(String ingredientsCsv, SearchFilters filters) {
        viewModel.setSearching(true);
        viewModel.setErrorMessage(null);
        
        SearchRecipesInputData inputData = new SearchRecipesInputData(
                ingredientsCsv,
                defaultNumberOfResults,
                filters
        );
        Thread worker = new Thread(() -> interactor.execute(inputData));
        worker.start();
    }
}
