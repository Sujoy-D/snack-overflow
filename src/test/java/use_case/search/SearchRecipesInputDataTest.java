package use_case.search;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SearchRecipesInputDataTest {

    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedIngredients = "tomato,cheese,bread";
        int expectedNumberOfResults = 10;
        SearchFilters expectedFilters = new SearchFilters(30, "vegetarian", List.of("dairy"), "italian", "dinner");

        // When
        SearchRecipesInputData inputData = new SearchRecipesInputData(expectedIngredients, expectedNumberOfResults, expectedFilters);

        // Then
        assertEquals(expectedIngredients, inputData.getIngredientsCsv());
        assertEquals(expectedNumberOfResults, inputData.getNumberOfResults());
        assertEquals(expectedFilters, inputData.getFilters());
    }

    @Test
    void constructorHandlesEmptyIngredients() {
        // Given
        String expectedIngredients = "";
        int expectedNumberOfResults = 5;
        SearchFilters expectedFilters = new SearchFilters(null, null, null, null, null);

        // When
        SearchRecipesInputData inputData = new SearchRecipesInputData(expectedIngredients, expectedNumberOfResults, expectedFilters);

        // Then
        assertEquals(expectedIngredients, inputData.getIngredientsCsv());
        assertEquals(expectedNumberOfResults, inputData.getNumberOfResults());
        assertEquals(expectedFilters, inputData.getFilters());
    }

    @Test
    void constructorHandlesSingleIngredient() {
        // Given
        String expectedIngredients = "chicken";
        int expectedNumberOfResults = 20;
        SearchFilters expectedFilters = new SearchFilters(60, null, null, "american", "lunch");

        // When
        SearchRecipesInputData inputData = new SearchRecipesInputData(expectedIngredients, expectedNumberOfResults, expectedFilters);

        // Then
        assertEquals(expectedIngredients, inputData.getIngredientsCsv());
        assertEquals(expectedNumberOfResults, inputData.getNumberOfResults());
        assertEquals(expectedFilters, inputData.getFilters());
    }

    @Test
    void constructorHandlesNullValues() {
        // When
        SearchRecipesInputData inputData = new SearchRecipesInputData(null, 0, null);

        // Then
        assertNull(inputData.getIngredientsCsv());
        assertEquals(0, inputData.getNumberOfResults());
        assertNull(inputData.getFilters());
    }
}