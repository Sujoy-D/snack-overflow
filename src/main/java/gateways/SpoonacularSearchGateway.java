package gateways;

import entity.Recipe;
import entity.RecipeFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.search.SearchFilters;
import use_case.search.SearchRecipesGateway;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gateway that adapts Spoonacular's "complex search" endpoints to the search use case
 */
public class SpoonacularSearchGateway implements SearchRecipesGateway {
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/complexSearch";
    
    private final JavaHttpGateway httpGateway;
    private final RecipeFactory recipeFactory;
    
    public SpoonacularSearchGateway(JavaHttpGateway httpGateway, RecipeFactory recipeFactory) {
        this.httpGateway = httpGateway;
        this.recipeFactory = recipeFactory;
    }
    
    @Override
    public List<Recipe> searchRecipes(String ingredientsCsv,
                                      SearchFilters filters,
                                      int numberOfResults) throws Exception {
        boolean hasFilters = filters != null && !filters.isEmpty();
        String trimmedIngredients = ingredientsCsv != null ? ingredientsCsv.trim() : "";
        
        // if no filters, use the more permissive findByIngredients endpoint to maximize matches
        if (!hasFilters) {
            return searchByIngredientsFallback(trimmedIngredients, numberOfResults);
        }
        
        String url = buildUrl(trimmedIngredients, filters, numberOfResults);
        String response = httpGateway.get(url);
        List<Recipe> recipes = parseRecipes(response);
        recipes = filterByFilters(recipes, filters);
        
        // if complex search returns nothing but we have include ingredients, fall back to regular permissive search
        if (recipes.isEmpty() && !trimmedIngredients.isEmpty()) {
            List<Recipe> fallback = searchByIngredientsFallback(trimmedIngredients, numberOfResults);
            recipes.addAll(filterByFilters(fallback, filters));
        }
        
        recipes.sort(Comparator.comparing(this::cookingTimeOrMax)
                .thenComparing(Recipe::getTitle, String.CASE_INSENSITIVE_ORDER));
        return recipes;
    }
    
    private String buildUrl(String ingredientsCsv, SearchFilters filters, int numberOfResults) {
        List<String> params = new ArrayList<>();
        params.add("addRecipeInformation=true");
        params.add("fillIngredients=true");
        
        int safeNumber = numberOfResults > 0 ? numberOfResults : 5;
        params.add("number=" + safeNumber);
        
        if (ingredientsCsv != null && !ingredientsCsv.trim().isEmpty()) {
            params.add("includeIngredients=" + encodeCsv(ingredientsCsv));
        }
        
        if (filters != null) {
            if (filters.getMaxCookingTimeMinutes() != null) {
                params.add("maxReadyTime=" + filters.getMaxCookingTimeMinutes());
            }
            if (filters.getDiet() != null) {
                params.add("diet=" + encode(filters.getDiet()));
            }
            if (filters.getAllergens() != null && !filters.getAllergens().isEmpty()) {
                params.add("intolerances=" + encodeCsv(String.join(",", filters.getAllergens())));
            }
            if (filters.getCuisine() != null) {
                params.add("cuisine=" + encode(filters.getCuisine()));
            }
            if (filters.getMealType() != null) {
                params.add("type=" + encode(filters.getMealType()));
            }
        }
        
        return BASE_URL + "?" + String.join("&", params);
    }
    
    private List<Recipe> searchByIngredientsFallback(String ingredientsCsv, int numberOfResults) throws Exception {
        if (ingredientsCsv == null || ingredientsCsv.isEmpty()) {
            return List.of();
        }
        String normalized = normalizeIngredients(ingredientsCsv);
        if (normalized.isEmpty()) {
            return List.of();
        }
        int safeNumber = numberOfResults > 0 ? numberOfResults : 5;
        String url = "https://api.spoonacular.com/recipes/findByIngredients"
                + "?ingredients=" + encodeCsv(normalized)
                + "&number=" + safeNumber;
        
        String response = httpGateway.get(url);
        return parseFindByIngredients(response);
    }
    
    private List<Recipe> parseRecipes(String responseBody) {
        List<Recipe> recipes = new ArrayList<>();
        JSONObject json = new JSONObject(responseBody);
        JSONArray results = json.optJSONArray("results");
        if (results == null) {
            return recipes;
        }
        
        for (int i = 0; i < results.length(); i++) {
            JSONObject recipeJson = results.getJSONObject(i);
            int id = recipeJson.optInt("id");
            String title = recipeJson.optString("title", "Untitled Recipe");
            Integer readyInMinutes = recipeJson.has("readyInMinutes") ?
                    recipeJson.optInt("readyInMinutes") : null;
            
            List<String> ingredients = new ArrayList<>();
            extractIngredientNames(recipeJson.optJSONArray("extendedIngredients"), ingredients);
            
            if (ingredients.isEmpty()) {
                ingredients.add("Ingredients provided in response");
            }
            
            Recipe recipe = recipeFactory.create(
                    id,
                    title,
                    ingredients,
                    recipeJson.optString("instructions", ""),
                    extractFirst(recipeJson.optJSONArray("cuisines")),
                    readyInMinutes,
                    extractFirst(recipeJson.optJSONArray("dishTypes")),
                    null
            );
            recipes.add(recipe);
        }
        
        return recipes;
    }
    
    private List<Recipe> parseFindByIngredients(String responseBody) {
        List<Recipe> recipes = new ArrayList<>();
        JSONArray results = new JSONArray(responseBody);
        
        for (int i = 0; i < results.length(); i++) {
            JSONObject recipeJson = results.getJSONObject(i);
            
            int id = recipeJson.optInt("id");
            String title = recipeJson.optString("title", "Untitled Recipe");
            
            List<String> ingredients = new ArrayList<>();
            extractIngredientNames(recipeJson.optJSONArray("usedIngredients"), ingredients);
            extractIngredientNames(recipeJson.optJSONArray("missedIngredients"), ingredients);
            
            if (ingredients.isEmpty()) {
                ingredients.add("Ingredients provided in response");
            }
            
            Recipe recipe = recipeFactory.create(
                    id,
                    title,
                    ingredients,
                    "", // instructions not provided by this endpoint
                    null,
                    null,
                    null,
                    null
            );
            recipes.add(recipe);
        }
        return recipes;
    }
    
    private List<Recipe> filterOutExcluded(List<Recipe> recipes, SearchFilters filters) {
        if (filters == null) {
            return recipes;
        }
        List<String> excludes = new ArrayList<>();
        if (filters.getAllergens() != null) {
            excludes.addAll(filters.getAllergens());
        }
        if (excludes.isEmpty()) {
            return recipes;
        }
        List<String> lowered = excludes.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        
        return recipes.stream()
                .filter(r -> r.getIngredients().stream()
                        .map(String::toLowerCase)
                        .noneMatch(lowered::contains))
                .collect(Collectors.toList());
    }
    
    private List<Recipe> filterByFilters(List<Recipe> recipes, SearchFilters filters) {
        if (filters == null) {
            return recipes;
        }
        List<Recipe> filtered = filterOutExcluded(recipes, filters);
        if (filters.getMealType() != null) {
            String desiredType = filters.getMealType().toLowerCase();
            filtered = filtered.stream()
                    .filter(r -> r.getMealType() != null
                            && r.getMealType().toLowerCase().contains(desiredType))
                    .collect(Collectors.toList());
        }
        return filtered;
    }
    
    private Integer cookingTimeOrMax(Recipe recipe) {
        return recipe.getCookingTime() != null ? recipe.getCookingTime() : Integer.MAX_VALUE;
    }
    
    private String extractFirst(JSONArray array) {
        if (array == null || array.isEmpty()) {
            return null;
        }
        String value = array.optString(0, null);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }
    
    private void extractIngredientNames(JSONArray ingredientsArray, List<String> ingredients) {
        if (ingredientsArray == null) {
            return;
        }
        for (int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject ingredientJson = ingredientsArray.optJSONObject(i);
            if (ingredientJson != null) {
                String name = ingredientJson.optString("name", "").trim();
                if (!name.isEmpty()) {
                    ingredients.add(name);
                }
            }
        }
    }
    
    private String encodeCsv(String csv) {
        List<String> parts = new ArrayList<>();
        for (String part : csv.split(",")) {
            String cleaned = part.trim();
            if (!cleaned.isEmpty()) {
                parts.add(cleaned);
            }
        }
        return parts.stream()
                .map(this::encode)
                .collect(Collectors.joining(","));
    }
    
    private String normalizeIngredients(String ingredientsCsv) {
        List<String> cleaned = new ArrayList<>();
        for (String part : ingredientsCsv.split(",")) {
            String ingredient = part.trim();
            if (!ingredient.isEmpty()) {
                cleaned.add(ingredient);
            }
        }
        return String.join(",", cleaned);
    }
    
    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
