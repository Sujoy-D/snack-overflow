package use_case.search;

import java.util.List;

import entity.Recipe;

/**
 * Gateway boundary for recipe search requests.
 */
public interface SearchRecipesGateway {
    /**
     * Searches for recipes based on ingredients and filters.
     *
     * @param ingredientsCsv comma-separated list of ingredients
     * @param filters search filters to apply
     * @param numberOfResults maximum number of results to return
     * @return list of recipes matching the search criteria
     * @throws Exception if the search operation fails
     */
    List<Recipe> searchRecipes(String ingredientsCsv,
                               SearchFilters filters,
                               int numberOfResults) throws Exception;
}
