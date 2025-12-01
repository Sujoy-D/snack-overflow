package use_case.tagging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for AddTagInputData.
 * Tests the input data transfer object for the Add Tag use case,
 * ensuring proper data encapsulation and getter functionality.
 * 
 * @author Test Suite
 * @version 1.0
 */
@DisplayName("AddTagInputData Tests")
public class AddTagInputDataTest {
    
    @Test
    @DisplayName("Should create input data with valid parameters")
    void testCreateInputDataWithValidParameters() {
        // Arrange
        String username = "testUser";
        int recipeId = 123;
        String tagName = "breakfast";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals(username, inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals(tagName, inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle null username")
    void testCreateInputDataWithNullUsername() {
        // Arrange
        String username = null;
        int recipeId = 123;
        String tagName = "breakfast";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertNull(inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals(tagName, inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle null tag name")
    void testCreateInputDataWithNullTagName() {
        // Arrange
        String username = "testUser";
        int recipeId = 123;
        String tagName = null;
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals(username, inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertNull(inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle empty strings")
    void testCreateInputDataWithEmptyStrings() {
        // Arrange
        String username = "";
        int recipeId = 456;
        String tagName = "";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals("", inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals("", inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle whitespace strings")
    void testCreateInputDataWithWhitespaceStrings() {
        // Arrange
        String username = "   ";
        int recipeId = 789;
        String tagName = "\t\n ";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals("   ", inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals("\t\n ", inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle special characters in strings")
    void testCreateInputDataWithSpecialCharacters() {
        // Arrange
        String username = "user@domain.com";
        int recipeId = 101;
        String tagName = "tag#name$special";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals("user@domain.com", inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals("tag#name$special", inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle long strings")
    void testCreateInputDataWithLongStrings() {
        // Arrange
        String username = "a".repeat(1000);
        int recipeId = 999;
        String tagName = "b".repeat(1000);
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals(username, inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals(tagName, inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should handle various recipe IDs")
    void testCreateInputDataWithVariousRecipeIds() {
        // Test with different recipe ID values
        int[] recipeIds = {0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        
        for (int recipeId : recipeIds) {
            // Act
            AddTagInputData inputData = new AddTagInputData("testUser", recipeId, "tag");
            
            // Assert
            assertEquals(recipeId, inputData.getRecipeId(), "Failed for recipe ID: " + recipeId);
            assertEquals("testUser", inputData.getUsername());
            assertEquals("tag", inputData.getTagName());
        }
    }
    
    @Test
    @DisplayName("Should handle unicode characters")
    void testCreateInputDataWithUnicodeCharacters() {
        // Arrange
        String username = "用户名";
        int recipeId = 42;
        String tagName = "标签名称";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        
        // Assert
        assertEquals("用户名", inputData.getUsername());
        assertEquals(recipeId, inputData.getRecipeId());
        assertEquals("标签名称", inputData.getTagName());
    }
    
    @Test
    @DisplayName("Should maintain immutability of data")
    void testDataImmutability() {
        // Arrange
        String originalUsername = "testUser";
        int originalRecipeId = 123;
        String originalTagName = "breakfast";
        
        // Act
        AddTagInputData inputData = new AddTagInputData(originalUsername, originalRecipeId, originalTagName);
        
        // Modify original variables
        originalUsername = "modifiedUser";
        originalTagName = "modifiedTag";
        
        // Assert - inputData should retain original values
        assertEquals("testUser", inputData.getUsername());
        assertEquals(123, inputData.getRecipeId());
        assertEquals("breakfast", inputData.getTagName());
    }
}
