package use_case.similar_recipes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimilarRecipesInputDataTest {
    
    @Test
    void constructorAndGetterWork() {
        // Given
        int expectedRecipeId = 12345;
        
        // When
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(expectedRecipeId);
        
        // Then
        assertEquals(expectedRecipeId, inputData.getRecipeID());
    }
    
    @Test
    void constructorHandlesZeroId() {
        // Given
        int expectedRecipeId = 0;
        
        // When
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(expectedRecipeId);
        
        // Then
        assertEquals(expectedRecipeId, inputData.getRecipeID());
    }
    
    @Test
    void constructorHandlesNegativeId() {
        // Given
        int expectedRecipeId = -1;
        
        // When
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(expectedRecipeId);
        
        // Then
        assertEquals(expectedRecipeId, inputData.getRecipeID());
    }
    
    @Test
    void constructorHandlesLargeId() {
        // Given
        int expectedRecipeId = Integer.MAX_VALUE;
        
        // When
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(expectedRecipeId);
        
        // Then
        assertEquals(expectedRecipeId, inputData.getRecipeID());
    }
}
