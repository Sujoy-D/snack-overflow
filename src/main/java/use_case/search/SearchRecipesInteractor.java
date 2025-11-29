package use_case.search;

import entity.Recipe;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Interactor that validates search requests and delegates to the gateway
 */
public class SearchRecipesInteractor implements SearchRecipesInputBoundary {
    private final SearchRecipesGateway searchGateway;
    private final SearchRecipesOutputBoundary presenter;
    
    public SearchRecipesInteractor(SearchRecipesGateway searchGateway,
                                   SearchRecipesOutputBoundary presenter) {
        this.searchGateway = searchGateway;
        this.presenter = presenter;
    }
    
    @Override
    public void execute(SearchRecipesInputData inputData) {
        String ingredientsCsv = inputData.getIngredientsCsv();
        int numberOfResults = inputData.getNumberOfResults();
        SearchFilters filters = inputData.getFilters();
        
        if ((ingredientsCsv == null || ingredientsCsv.trim().isEmpty())
                && (filters == null || filters.isEmpty())) {
            presenter.presentFailure("Add ingredients or filters to search.");
            return;
        }
        
        if (filters != null && filters.getMaxCookingTimeMinutes() != null
                && filters.getMaxCookingTimeMinutes() <= 0) {
            presenter.presentFailure("Maximum cooking time must be a positive number.");
            return;
        }
        
        if (hasIngredientConflict(ingredientsCsv, filters)) {
            presenter.presentFailure("Conflicting filters: ingredients to include overlap with exclusions.");
            return;
        }
        
        try {
            List<Recipe> recipes = searchGateway.searchRecipes(
                    normalizeCsv(ingredientsCsv),
                    filters,
                    numberOfResults);
            if (recipes == null) {
                recipes = Collections.emptyList();
            }
            presenter.presentSuccess(new SearchRecipesOutputData(recipes));
        } catch (Exception e) {
            presenter.presentFailure("Unable to fetch recipes: " + e.getMessage());
        }
    }
    
    private boolean hasIngredientConflict(String includeCsv, SearchFilters filters) {
        if (filters == null || filters.getAllergens().isEmpty()) {
            return false;
        }
        List<String> includes = normalizeToList(includeCsv);
        if (includes.isEmpty()) {
            return false;
        }
        for (String include : includes) {
            if (filters.getAllergens().contains(include.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    private String normalizeCsv(String csv) {
        List<String> parts = normalizeToList(csv);
        return String.join(",", parts);
    }
    
    private List<String> normalizeToList(String csv) {
        if (csv == null) {
            return Collections.emptyList();
        }
        String[] parts = csv.split(",");
        List<String> cleaned = new ArrayList<>();
        for (String part : parts) {
            String ingredient = part.trim().toLowerCase();
            if (!ingredient.isEmpty()) {
                cleaned.add(ingredient);
            }
        }
        return cleaned;
    }
}
