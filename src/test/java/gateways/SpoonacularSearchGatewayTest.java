package gateways;

import entity.Ingredient;
import entity.Recipe;
import entity.RecipeFactory;
import org.junit.jupiter.api.Test;
import use_case.search.SearchFilters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SpoonacularSearchGatewayTest {
    
    @Test
    void filteredSearchFetchesAdditionalPagesToFillResults() throws Exception {
        RecordingHttpGateway http = new RecordingHttpGateway();
        SpoonacularSearchGateway gateway = new SpoonacularSearchGateway(http, new RecipeFactory());
        SearchFilters filters = new SearchFilters(null, null, List.of("raisins"), null, null);
        
        List<Recipe> results = gateway.searchRecipes("apples", filters, 5);
        
        assertEquals(5, results.size(), "Should deliver the requested number of filtered recipes");
        assertTrue(results.stream().noneMatch(r -> ingredientNames(r).contains("raisins")),
                "Excluded ingredient should not appear in results");
        assertTrue(http.requestedUrls.size() >= 2, "Should page through when first batch is too filtered");
        assertTrue(http.requestedUrls.get(1).contains("offset=10"),
                "Second page should include the expected offset");
    }
    
    @Test
    void noFiltersUsesFindByIngredientsEndpoint() throws Exception {
        FindByIngredientsHttpGateway http = new FindByIngredientsHttpGateway();
        SpoonacularSearchGateway gateway = new SpoonacularSearchGateway(http, new RecipeFactory());
        
        List<Recipe> results = gateway.searchRecipes("apples", null, 3);
        
        assertEquals(3, results.size(), "Fallback endpoint should return the requested number of recipes");
        assertTrue(http.requestedUrls.get(0).getPath().contains("findByIngredients"),
                "Should hit the findByIngredients endpoint when no filters are active");
    }
    
    private List<String> ingredientNames(Recipe recipe) {
        return recipe.getIngredients().stream()
                .map(Ingredient::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
    
    /**
     * Recording stub that returns two pages for complex search
     * First page has many excluded items; second page fills the rest
     */
    private static class RecordingHttpGateway extends JavaHttpGateway {
        final List<String> requestedUrls = new ArrayList<>();
        
        @Override
        public String get(String url) {
            requestedUrls.add(url);
            if (url.contains("offset=10")) {
                return complexResponse("apple", "apple", "apple", "apple", "apple");
            }
            if (url.contains("complexSearch")) {
                return complexResponse(
                        "raisins", "raisins", "apple", "raisins", "apple",
                        "raisins", "raisins", "raisins", "apple", "raisins"
                );
            }
            throw new IllegalArgumentException("Unexpected URL: " + url);
        }
    }
    
    /**
     * Stub for findByIngredients endpoint
     */
    private static class FindByIngredientsHttpGateway extends JavaHttpGateway {
        final List<java.net.URL> requestedUrls = new ArrayList<>();
        
        @Override
        public String get(String url) throws Exception {
            requestedUrls.add(new java.net.URL(url));
            if (url.contains("findByIngredients")) {
                return "[" +
                        recipeWithUsedIngredient(1, "Apple Pie", "apple") + "," +
                        recipeWithUsedIngredient(2, "Apple Crisp", "apple") + "," +
                        recipeWithUsedIngredient(3, "Apple Cake", "apple") +
                        "]";
            }
            throw new IllegalArgumentException("Unexpected URL: " + url);
        }
    }
    
    private static String recipeWithUsedIngredient(int id, String title, String ingredient) {
        return "{\"id\":" + id +
                ",\"title\":\"" + title + "\"," +
                "\"usedIngredients\":[{\"name\":\"" + ingredient + "\"}]," +
                "\"missedIngredients\":[]}";
    }
    
    private static String complexResponse(String... ingredientNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"results\":[");
        for (int i = 0; i < ingredientNames.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("{\"id\":").append(i + 1)
                    .append(",\"title\":\"Recipe ").append(i + 1).append("\",")
                    .append("\"extendedIngredients\":[{\"name\":\"").append(ingredientNames[i]).append("\"}]}");
        }
        sb.append("]}");
        return sb.toString();
    }
}
