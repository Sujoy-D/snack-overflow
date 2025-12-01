package use_case.similar_recipes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SimilarRecipesOutputDataTest {
    
    @Test
    void constructorAndGetterWorkWithRecipes() {
        // Given
        ArrayList<Integer> expectedSimilarRecipes = new ArrayList<>();
        expectedSimilarRecipes.add(101);
        expectedSimilarRecipes.add(202);
        expectedSimilarRecipes.add(303);
        
        // When
        SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(expectedSimilarRecipes);
        
        // Then
        assertEquals(expectedSimilarRecipes, outputData.getSimilarRecipes());
        assertEquals(3, outputData.getSimilarRecipes().size());
        assertTrue(outputData.getSimilarRecipes().contains(101));
        assertTrue(outputData.getSimilarRecipes().contains(202));
        assertTrue(outputData.getSimilarRecipes().contains(303));
    }
    
    @Test
    void constructorHandlesEmptyList() {
        // Given
        ArrayList<Integer> expectedSimilarRecipes = new ArrayList<>();
        
        // When
        SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(expectedSimilarRecipes);
        
        // Then
        assertEquals(expectedSimilarRecipes, outputData.getSimilarRecipes());
        assertTrue(outputData.getSimilarRecipes().isEmpty());
    }
    
    @Test
    void constructorHandlesSingleRecipe() {
        // Given
        ArrayList<Integer> expectedSimilarRecipes = new ArrayList<>();
        expectedSimilarRecipes.add(456);
        
        // When
        SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(expectedSimilarRecipes);
        
        // Then
        assertEquals(expectedSimilarRecipes, outputData.getSimilarRecipes());
        assertEquals(1, outputData.getSimilarRecipes().size());
        assertEquals(Integer.valueOf(456), outputData.getSimilarRecipes().get(0));
    }
    
    @Test
    void constructorHandlesNullList() {
        // When
        SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(null);
        
        // Then
        assertNull(outputData.getSimilarRecipes());
    }
    
    @Test
    void constructorHandlesDuplicateRecipeIds() {
        // Given
        ArrayList<Integer> expectedSimilarRecipes = new ArrayList<>();
        expectedSimilarRecipes.add(123);
        expectedSimilarRecipes.add(123);
        expectedSimilarRecipes.add(456);
        expectedSimilarRecipes.add(123);
        
        // When
        SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(expectedSimilarRecipes);
        
        // Then
        assertEquals(expectedSimilarRecipes, outputData.getSimilarRecipes());
        assertEquals(4, outputData.getSimilarRecipes().size());
        // Verify duplicates are preserved
        assertEquals(3, outputData.getSimilarRecipes().stream()
                .mapToInt(Integer::intValue)
                .filter(id -> id == 123)
                .toArray().length);
    }
    
    @Test
    void constructorHandlesLargeList() {
        // Given
        ArrayList<Integer> expectedSimilarRecipes = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            expectedSimilarRecipes.add(i * 10);
        }
        
        // When
        SimilarRecipesOutputData outputData = new SimilarRecipesOutputData(expectedSimilarRecipes);
        
        // Then
        assertEquals(expectedSimilarRecipes, outputData.getSimilarRecipes());
        assertEquals(100, outputData.getSimilarRecipes().size());
        assertEquals(Integer.valueOf(10), outputData.getSimilarRecipes().get(0));
        assertEquals(Integer.valueOf(1000), outputData.getSimilarRecipes().get(99));
    }
}
