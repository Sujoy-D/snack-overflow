package use_case.search;

/**
 * Presenter boundary for search results
 */
public interface SearchRecipesOutputBoundary {
    void presentSuccess(SearchRecipesOutputData outputData);
    void presentFailure(String errorMessage);
}
