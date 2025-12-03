package use_case.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Recipe;

/**
 * Interactor that validates search requests and delegates to the gateway.
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
        final String ingredientsCsv = inputData.getIngredientsCsv();
        final int numberOfResults = inputData.getNumberOfResults();
        final SearchFilters filters = inputData.getFilters();

        final String validationError = validateInput(ingredientsCsv, filters);
        if (validationError == null) {
            performSearch(ingredientsCsv, numberOfResults, filters);
        }
        else {
            presenter.presentFailure(validationError);
        }
    }

    private String validateInput(String ingredientsCsv, SearchFilters filters) {
        String errorMessage = null;

        if ((ingredientsCsv == null || ingredientsCsv.trim().isEmpty())
                && (filters == null || filters.isEmpty())) {
            errorMessage = "Add ingredients or filters to search.";
        }
        else if (filters != null && filters.getMaxCookingTimeMinutes() != null
                && filters.getMaxCookingTimeMinutes() <= 0) {
            errorMessage = "Maximum cooking time must be a positive number.";
        }
        else if (hasIngredientConflict(ingredientsCsv, filters)) {
            errorMessage = "Conflicting filters: ingredients to include overlap with exclusions.";
        }
        
        return errorMessage;
    }

    @SuppressWarnings("IllegalCatch")
    private void performSearch(String ingredientsCsv, int numberOfResults, SearchFilters filters) {
        try {
            List<Recipe> recipes = searchGateway.searchRecipes(
                    normalizeCsv(ingredientsCsv),
                    filters,
                    numberOfResults);
            if (recipes == null) {
                recipes = Collections.emptyList();
            }
            presenter.presentSuccess(new SearchRecipesOutputData(recipes));
        }
        catch (RuntimeException runtimeException) {
            presenter.presentFailure("Unable to fetch recipes: " + runtimeException.getMessage());
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            presenter.presentFailure("Search was interrupted: " + interruptedException.getMessage());
        }
        catch (java.io.IOException ioException) {
            presenter.presentFailure("Network error while searching: " + ioException.getMessage());
        }
        catch (Exception exception) {
            presenter.presentFailure("Unable to fetch recipes: " + exception.getMessage());
        }
    }

    private boolean hasIngredientConflict(String includeCsv, SearchFilters filters) {
        boolean hasConflict = false;
        if (filters != null && !filters.getAllergens().isEmpty()) {
            final List<String> includes = normalizeToList(includeCsv);
            if (!includes.isEmpty()) {
                for (String include : includes) {
                    if (filters.getAllergens().contains(include.toLowerCase())) {
                        hasConflict = true;
                        break;
                    }
                }
            }
        }
        return hasConflict;
    }
    
    private String normalizeCsv(String csv) {
        final List<String> parts = normalizeToList(csv);
        return String.join(",", parts);
    }
    
    private List<String> normalizeToList(String csv) {
        final List<String> result;
        if (csv == null) {
            result = Collections.emptyList();
        }
        else {
            final String[] parts = csv.split(",");
            final List<String> cleaned = new ArrayList<>();
            for (String part : parts) {
                final String ingredient = part.trim().toLowerCase();
                if (!ingredient.isEmpty()) {
                    cleaned.add(ingredient);
                }
            }
            result = cleaned;
        }
        return result;
    }
}
