package use_case.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Value object representing search filters.
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
        String result = null;
        if (value != null) {
            final String trimmed = value.trim();
            if (!trimmed.isEmpty()) {
                result = trimmed;
            }
        }
        return result;
    }
    
    private List<String> normalizeList(List<String> values) {
        final List<String> result;
        if (values == null) {
            result = Collections.emptyList();
        }
        else {
            result = values.stream()
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(value -> value.toLowerCase(Locale.ROOT))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return result;
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
