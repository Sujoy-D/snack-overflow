package use_case.tagging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for AddTagOutputData.
 * Tests the output data transfer object for the Add Tag use case,
 * ensuring proper data encapsulation and getter functionality.
 *
 * @author Test Suite
 * @version 1.0
 */
@DisplayName("AddTagOutputData Tests")
public class AddTagOutputDataTest {

    @Test
    @DisplayName("Should create output data with valid parameters")
    void testCreateOutputDataWithValidParameters() {
        // Arrange
        int recipeId = 123;
        String newTag = "breakfast";
        List<String> allTags = Arrays.asList("breakfast", "lunch", "dinner");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals(newTag, outputData.getNewTag());
        assertEquals(allTags, outputData.getAllTags());
        assertEquals(3, outputData.getAllTags().size());
    }

    @Test
    @DisplayName("Should handle null new tag")
    void testCreateOutputDataWithNullNewTag() {
        // Arrange
        int recipeId = 123;
        String newTag = null;
        List<String> allTags = Arrays.asList("lunch", "dinner");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertNull(outputData.getNewTag());
        assertEquals(allTags, outputData.getAllTags());
    }

    @Test
    @DisplayName("Should handle null tags list")
    void testCreateOutputDataWithNullTagsList() {
        // Arrange
        int recipeId = 123;
        String newTag = "breakfast";
        List<String> allTags = null;

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals(newTag, outputData.getNewTag());
        assertNull(outputData.getAllTags());
    }

    @Test
    @DisplayName("Should handle empty tags list")
    void testCreateOutputDataWithEmptyTagsList() {
        // Arrange
        int recipeId = 123;
        String newTag = "breakfast";
        List<String> allTags = new ArrayList<>();

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals(newTag, outputData.getNewTag());
        assertEquals(allTags, outputData.getAllTags());
        assertTrue(outputData.getAllTags().isEmpty());
    }

    @Test
    @DisplayName("Should handle tags list with null elements")
    void testCreateOutputDataWithNullElementsInTagsList() {
        // Arrange
        int recipeId = 456;
        String newTag = "snack";
        List<String> allTags = Arrays.asList("breakfast", null, "lunch", null, "dinner");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals(newTag, outputData.getNewTag());
        assertEquals(5, outputData.getAllTags().size());
        assertTrue(outputData.getAllTags().contains(null));
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testCreateOutputDataWithEmptyStrings() {
        // Arrange
        int recipeId = 789;
        String newTag = "";
        List<String> allTags = Arrays.asList("", "lunch", "");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals("", outputData.getNewTag());
        assertEquals(3, outputData.getAllTags().size());
        assertTrue(outputData.getAllTags().contains(""));
    }

    @Test
    @DisplayName("Should handle whitespace strings")
    void testCreateOutputDataWithWhitespaceStrings() {
        // Arrange
        int recipeId = 101;
        String newTag = "   ";
        List<String> allTags = Arrays.asList("\t", "\n", "   ");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals("   ", outputData.getNewTag());
        assertEquals(3, outputData.getAllTags().size());
    }

    @Test
    @DisplayName("Should handle special characters in strings")
    void testCreateOutputDataWithSpecialCharacters() {
        // Arrange
        int recipeId = 202;
        String newTag = "tag#with@special$chars";
        List<String> allTags = Arrays.asList("tag@name", "tag#hash", "tag$dollar");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals("tag#with@special$chars", outputData.getNewTag());
        assertEquals(3, outputData.getAllTags().size());
        assertTrue(outputData.getAllTags().contains("tag@name"));
    }

    @Test
    @DisplayName("Should handle long strings")
    void testCreateOutputDataWithLongStrings() {
        // Arrange
        int recipeId = 303;
        String newTag = "a".repeat(1000);
        List<String> allTags = Arrays.asList("b".repeat(500), "c".repeat(1000));

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals(1000, outputData.getNewTag().length());
        assertEquals(2, outputData.getAllTags().size());
    }

    @Test
    @DisplayName("Should handle various recipe IDs")
    void testCreateOutputDataWithVariousRecipeIds() {
        // Test with different recipe ID values
        int[] recipeIds = {0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE};

        for (int recipeId : recipeIds) {
            // Act
            AddTagOutputData outputData = new AddTagOutputData(recipeId, "tag", Arrays.asList("tag1", "tag2"));

            // Assert
            assertEquals(recipeId, outputData.getRecipeId(), "Failed for recipe ID: " + recipeId);
            assertEquals("tag", outputData.getNewTag());
            assertEquals(2, outputData.getAllTags().size());
        }
    }

    @Test
    @DisplayName("Should handle unicode characters")
    void testCreateOutputDataWithUnicodeCharacters() {
        // Arrange
        int recipeId = 404;
        String newTag = "新标签";
        List<String> allTags = Arrays.asList("标签1", "태그2", "タグ3");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals("新标签", outputData.getNewTag());
        assertEquals(3, outputData.getAllTags().size());
        assertTrue(outputData.getAllTags().contains("태그2"));
    }

    @Test
    @DisplayName("Should handle large tags list")
    void testCreateOutputDataWithLargeTagsList() {
        // Arrange
        int recipeId = 505;
        String newTag = "newTag";
        List<String> allTags = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            allTags.add("tag" + i);
        }

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals(newTag, outputData.getNewTag());
        assertEquals(1000, outputData.getAllTags().size());
        assertTrue(outputData.getAllTags().contains("tag500"));
    }

    @Test
    @DisplayName("Should maintain immutability of data")
    void testDataImmutability() {
        // Arrange
        int originalRecipeId = 123;
        String originalNewTag = "breakfast";
        List<String> originalAllTags = Arrays.asList("breakfast", "lunch");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(originalRecipeId, originalNewTag, originalAllTags);

        // Try to modify original list (should not affect output data)
        List<String> mutableList = new ArrayList<>(originalAllTags);
        mutableList.add("dinner");

        // Assert - outputData should retain original values
        assertEquals(123, outputData.getRecipeId());
        assertEquals("breakfast", outputData.getNewTag());
        assertEquals(2, outputData.getAllTags().size()); // Should still be 2, not 3
    }

    @Test
    @DisplayName("Should handle single tag in list")
    void testCreateOutputDataWithSingleTag() {
        // Arrange
        int recipeId = 606;
        String newTag = "onlyTag";
        List<String> allTags = Arrays.asList("onlyTag");

        // Act
        AddTagOutputData outputData = new AddTagOutputData(recipeId, newTag, allTags);

        // Assert
        assertEquals(recipeId, outputData.getRecipeId());
        assertEquals("onlyTag", outputData.getNewTag());
        assertEquals(1, outputData.getAllTags().size());
        assertEquals("onlyTag", outputData.getAllTags().get(0));
    }
}