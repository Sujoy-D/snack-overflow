package use_case.save_recipe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SaveRecipeOutputDataTest {
    
    @Test
    void constructorAndGettersWorkForSuccess() {
        // Given
        boolean expectedSuccess = true;
        String expectedMessage = "Recipe saved successfully";
        String expectedRecipeName = "Chocolate Cake";
        
        // When
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(expectedSuccess, expectedMessage, expectedRecipeName);
        
        // Then
        assertTrue(outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
        assertEquals(expectedRecipeName, outputData.getRecipeName());
    }
    
    @Test
    void constructorAndGettersWorkForFailure() {
        // Given
        boolean expectedSuccess = false;
        String expectedMessage = "Failed to save recipe: Database error";
        String expectedRecipeName = "Failed Recipe";
        
        // When
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(expectedSuccess, expectedMessage, expectedRecipeName);
        
        // Then
        assertFalse(outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
        assertEquals(expectedRecipeName, outputData.getRecipeName());
    }
    
    @Test
    void constructorHandlesNullMessage() {
        // Given
        boolean expectedSuccess = true;
        String expectedRecipeName = "Simple Pasta";
        
        // When
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(expectedSuccess, null, expectedRecipeName);
        
        // Then
        assertTrue(outputData.isSuccess());
        assertNull(outputData.getMessage());
        assertEquals(expectedRecipeName, outputData.getRecipeName());
    }
    
    @Test
    void constructorHandlesNullRecipeName() {
        // Given
        boolean expectedSuccess = false;
        String expectedMessage = "Recipe name is required";
        
        // When
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(expectedSuccess, expectedMessage, null);
        
        // Then
        assertFalse(outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
        assertNull(outputData.getRecipeName());
    }
    
    @Test
    void constructorHandlesAllNullValues() {
        // When
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(false, null, null);
        
        // Then
        assertFalse(outputData.isSuccess());
        assertNull(outputData.getMessage());
        assertNull(outputData.getRecipeName());
    }
    
    @Test
    void constructorHandlesEmptyStrings() {
        // Given
        boolean expectedSuccess = true;
        String expectedMessage = "";
        String expectedRecipeName = "";
        
        // When
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(expectedSuccess, expectedMessage, expectedRecipeName);
        
        // Then
        assertTrue(outputData.isSuccess());
        assertEquals("", outputData.getMessage());
        assertEquals("", outputData.getRecipeName());
    }
}
