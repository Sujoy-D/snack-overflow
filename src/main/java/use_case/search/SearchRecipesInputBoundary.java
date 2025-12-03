package use_case.search;

/**
 * Input boundary for the search recipes use case.
 */
public interface SearchRecipesInputBoundary {
    /**
     * Executes the search recipes use case.
     *
     * @param inputData the input data containing search parameters
     */
    void execute(SearchRecipesInputData inputData);
}
