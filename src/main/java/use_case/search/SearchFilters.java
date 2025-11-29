package use_case.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Value object representing search filters
 */
public class SearchFilters {
    private final Integer maxCookingTimeMinutes;
    private final String diet;
    private final List<String> allergens;
    private final String cuisine;
    private final String mealType;
    
    public SearchFilters(Integer maxCookingTimeMinutes,
                         String diet,
                         List<String> allergens,
                         String cuisine,
                         String mealType) {
        this.maxCookingTimeMinutes = maxCookingTimeMinutes;
        this.diet = normalizeString(diet);
        this.allergens = normalizeList(allergens);
        this.cuisine = normalizeString(cuisine);
        this.mealType = normalizeString(mealType);
    }
    
    private String normalizeString(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    
    private List<String> normalizeList(List<String> values) {
        if (values == null) {
            return Collections.emptyList();
        }
        return values.stream()
                .map(String::trim)
                .filter(v -> !v.isEmpty())
                .map(v -> v.toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public Integer getMaxCookingTimeMinutes() {
        return maxCookingTimeMinutes;
    }
    
    public String getDiet() {
        return diet;
    }
    
    public List<String> getAllergens() {
        return Collections.unmodifiableList(allergens);
    }
    
    public String getCuisine() {
        return cuisine;
    }
    
    public String getMealType() {
        return mealType;
    }
    
    public boolean isEmpty() {
        return maxCookingTimeMinutes == null
                && diet == null
                && (allergens == null || allergens.isEmpty())
                && cuisine == null
                && mealType == null;
    }
}
