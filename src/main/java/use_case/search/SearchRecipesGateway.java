package use_case.search;

import entity.Recipe;

import java.util.List;

/**
 * Gateway boundary for recipe search requests
 */
public interface SearchRecipesGateway {
    List<Recipe> searchRecipes(String ingredientsCsv,
                               SearchFilters filters,
                               int numberOfResults) throws Exception;
}
