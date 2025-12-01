package use_case.search;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchFiltersTest {

    @Test
    void constructorAndGettersWorkWithAllFilters() {
        // Given
        Integer expectedMaxCookingTime = 45;
        String expectedDiet = "vegan";
        List<String> expectedAllergens = List.of("nuts", "gluten");
        String expectedCuisine = "thai";
        String expectedMealType = "dinner";

        // When
        SearchFilters filters = new SearchFilters(expectedMaxCookingTime, expectedDiet, expectedAllergens, expectedCuisine, expectedMealType);

        // Then
        assertEquals(expectedMaxCookingTime, filters.getMaxCookingTimeMinutes());
        assertEquals(expectedDiet, filters.getDiet());
        assertEquals(2, filters.getAllergens().size());
        assertTrue(filters.getAllergens().contains("nuts"));
        assertTrue(filters.getAllergens().contains("gluten"));
        assertEquals(expectedCuisine, filters.getCuisine());
        assertEquals(expectedMealType, filters.getMealType());
        assertFalse(filters.isEmpty());
    }

    @Test
    void constructorHandlesNullValues() {
        // When
        SearchFilters filters = new SearchFilters(null, null, null, null, null);

        // Then
        assertNull(filters.getMaxCookingTimeMinutes());
        assertNull(filters.getDiet());
        assertTrue(filters.getAllergens().isEmpty());
        assertNull(filters.getCuisine());
        assertNull(filters.getMealType());
        assertTrue(filters.isEmpty());
    }

    @Test
    void constructorNormalizesStringValues() {
        // Given
        String diet = "  vegetarian  ";
        String cuisine = " italian ";
        String mealType = "lunch   ";

        // When
        SearchFilters filters = new SearchFilters(30, diet, null, cuisine, mealType);

        // Then
        assertEquals("vegetarian", filters.getDiet());
        assertEquals("italian", filters.getCuisine());
        assertEquals("lunch", filters.getMealType());
    }

    @Test
    void constructorHandlesEmptyStrings() {
        // When
        SearchFilters filters = new SearchFilters(null, "", null, "  ", "");

        // Then
        assertNull(filters.getDiet());
        assertNull(filters.getCuisine());
        assertNull(filters.getMealType());
        assertTrue(filters.isEmpty());
    }

    @Test
    void constructorNormalizesAllergensList() {
        // Given
        List<String> allergens = List.of("  NUTS  ", "Gluten", "", "  dairy  ");

        // When
        SearchFilters filters = new SearchFilters(null, null, allergens, null, null);

        // Then
        assertEquals(3, filters.getAllergens().size());
        assertTrue(filters.getAllergens().contains("nuts"));
        assertTrue(filters.getAllergens().contains("gluten"));
        assertTrue(filters.getAllergens().contains("dairy"));
        assertFalse(filters.getAllergens().contains(""));
    }

    @Test
    void getAllergensReturnsUnmodifiableList() {
        // Given
        List<String> allergens = new ArrayList<>(List.of("nuts"));
        SearchFilters filters = new SearchFilters(null, null, allergens, null, null);

        // When & Then
        List<String> returnedAllergens = filters.getAllergens();
        assertThrows(UnsupportedOperationException.class, () -> 
            returnedAllergens.add("new allergen"));
    }

    @Test
    void isEmptyReturnsTrueWhenAllFieldsAreNull() {
        // When
        SearchFilters filters = new SearchFilters(null, null, null, null, null);

        // Then
        assertTrue(filters.isEmpty());
    }

    @Test
    void isEmptyReturnsTrueWhenAllergensIsEmpty() {
        // When
        SearchFilters filters = new SearchFilters(null, null, new ArrayList<>(), null, null);

        // Then
        assertTrue(filters.isEmpty());
    }

    @Test
    void isEmptyReturnsFalseWhenAnyFieldIsSet() {
        // Test each field individually
        assertFalse(new SearchFilters(30, null, null, null, null).isEmpty());
        assertFalse(new SearchFilters(null, "vegan", null, null, null).isEmpty());
        assertFalse(new SearchFilters(null, null, List.of("nuts"), null, null).isEmpty());
        assertFalse(new SearchFilters(null, null, null, "italian", null).isEmpty());
        assertFalse(new SearchFilters(null, null, null, null, "dinner").isEmpty());
    }
}