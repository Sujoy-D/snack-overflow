package use_case.add_recipe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddRecipeOutputDataTest {
    
    @Test
    void constructorAndGettersWorkForSuccess() {
        // Given
        boolean expectedSuccess = true;
        String expectedMessage = "Recipe added successfully!";
        
        // When
        AddRecipeOutputData outputData = new AddRecipeOutputData(expectedSuccess, expectedMessage);
        
        // Then
        assertTrue(outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
    
    @Test
    void constructorAndGettersWorkForFailure() {
        // Given
        boolean expectedSuccess = false;
        String expectedMessage = "Failed to add recipe: Title is required";
        
        // When
        AddRecipeOutputData outputData = new AddRecipeOutputData(expectedSuccess, expectedMessage);
        
        // Then
        assertFalse(outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesNullMessage() {
        // Given
        boolean expectedSuccess = true;
        String expectedMessage = null;
        
        // When
        AddRecipeOutputData outputData = new AddRecipeOutputData(expectedSuccess, expectedMessage);
        
        // Then
        assertTrue(outputData.isSuccess());
        assertNull(outputData.getMessage());
    }
    
    @Test
    void constructorHandlesEmptyMessage() {
        // Given
        boolean expectedSuccess = false;
        String expectedMessage = "";
        
        // When
        AddRecipeOutputData outputData = new AddRecipeOutputData(expectedSuccess, expectedMessage);
        
        // Then
        assertFalse(outputData.isSuccess());
        assertEquals("", outputData.getMessage());
    }
    
    @Test
    void constructorHandlesDetailedErrorMessage() {
        // Given
        boolean expectedSuccess = false;
        String expectedMessage = "Failed to add recipe: Invalid cooking time, must be positive number";
        
        // When
        AddRecipeOutputData outputData = new AddRecipeOutputData(expectedSuccess, expectedMessage);
        
        // Then
        assertFalse(outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
}
