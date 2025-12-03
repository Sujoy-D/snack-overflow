package use_case.search;

/**
 * Presenter boundary for search results.
 */
public interface SearchRecipesOutputBoundary {
    /**
     * Presents successful search results.
     *
     * @param outputData the output data containing search results
     */
    void presentSuccess(SearchRecipesOutputData outputData);

    /**
     * Presents a search failure with an error message.
     *
     * @param errorMessage the error message to present
     */
    void presentFailure(String errorMessage);
}
