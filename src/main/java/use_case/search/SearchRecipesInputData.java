package use_case.search;

/**
 * Input data for the search use case
 */
public class SearchRecipesInputData {
    private final String ingredientsCsv;
    private final int numberOfResults;
    private final SearchFilters filters;
    
    public SearchRecipesInputData(String ingredientsCsv,
                                  int numberOfResults,
                                  SearchFilters filters) {
        this.ingredientsCsv = ingredientsCsv;
        this.numberOfResults = numberOfResults;
        this.filters = filters;
    }
    
    public String getIngredientsCsv() {
        return ingredientsCsv;
    }
    
    public int getNumberOfResults() {
        return numberOfResults;
    }
    
    public SearchFilters getFilters() {
        return filters;
    }
}
