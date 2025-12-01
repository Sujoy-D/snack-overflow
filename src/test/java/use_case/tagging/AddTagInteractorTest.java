package use_case.tagging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for AddTagInteractor.
 * Tests the business logic for adding tags to recipes, including validation rules,
 * duplicate checking, and success scenarios.
 * 
 * @author Test Suite
 * @version 1.0
 */
@DisplayName("AddTagInteractor Tests")
public class AddTagInteractorTest {
    
    private AddTagInteractor interactor;
    private MockAddTagDataAccess mockDataAccess;
    private MockAddTagOutputBoundary mockPresenter;
    
    @BeforeEach
    void setUp() {
        mockDataAccess = new MockAddTagDataAccess();
        mockPresenter = new MockAddTagOutputBoundary();
        interactor = new AddTagInteractor(mockDataAccess, mockPresenter);
    }
    
    @Test
    @DisplayName("Should successfully add valid tag to recipe")
    void testAddValidTag() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "breakfast");
        mockDataAccess.setExistingTags(Arrays.asList("lunch", "dinner"));
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isSuccessViewCalled());
        assertFalse(mockPresenter.isFailureViewCalled());
        assertEquals("breakfast", mockDataAccess.getLastAddedTag());
        assertEquals("testUser", mockDataAccess.getLastUsername());
        assertEquals(123, mockDataAccess.getLastRecipeId());
        
        AddTagOutputData outputData = mockPresenter.getSuccessOutputData();
        assertNotNull(outputData);
        assertEquals(123, outputData.getRecipeId());
        assertEquals("breakfast", outputData.getNewTag());
        assertNotNull(outputData.getAllTags());
    }
    
    @Test
    @DisplayName("Should fail when tag name is empty")
    void testAddEmptyTag() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isFailureViewCalled());
        assertFalse(mockPresenter.isSuccessViewCalled());
        assertEquals("Tag name cannot be empty.", mockPresenter.getFailureMessage());
    }
    
    @Test
    @DisplayName("Should fail when tag name is only whitespace")
    void testAddWhitespaceOnlyTag() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "   ");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isFailureViewCalled());
        assertFalse(mockPresenter.isSuccessViewCalled());
        assertEquals("Tag name cannot be empty.", mockPresenter.getFailureMessage());
    }
    
    @Test
    @DisplayName("Should fail when tag name exceeds 20 characters")
    void testAddTooLongTag() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "verylongtagnamethatisgreaterthan20chars");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isFailureViewCalled());
        assertFalse(mockPresenter.isSuccessViewCalled());
        assertEquals("Invalid tag name, too long.", mockPresenter.getFailureMessage());
    }
    
    @Test
    @DisplayName("Should accept tag name with exactly 20 characters")
    void testAddExactly20CharacterTag() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "exactly20characters1"); // 20 chars
        mockDataAccess.setExistingTags(new ArrayList<>());
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isSuccessViewCalled());
        assertFalse(mockPresenter.isFailureViewCalled());
    }
    
    @Test
    @DisplayName("Should fail when tag contains symbols")
    void testAddTagWithSymbols() {
        // Test various symbols
        String[] invalidTags = {"tag@name", "tag#name", "tag$name", "tag%name", "tag&name", "tag*name"};
        
        for (String tagName : invalidTags) {
            setUp(); // Reset mocks
            AddTagInputData inputData = new AddTagInputData("testUser", 123, tagName);
            
            // Act
            interactor.execute(inputData);
            
            // Assert
            assertTrue(mockPresenter.isFailureViewCalled(), "Should fail for tag: " + tagName);
            assertEquals("Tag name cannot contain symbols.", mockPresenter.getFailureMessage());
        }
    }
    
    @Test
    @DisplayName("Should accept tag with valid characters (letters, numbers, spaces)")
    void testAddTagWithValidCharacters() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "Tag Name 123");
        mockDataAccess.setExistingTags(new ArrayList<>());
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isSuccessViewCalled());
        assertFalse(mockPresenter.isFailureViewCalled());
    }
    
    @Test
    @DisplayName("Should fail when tag already exists (case insensitive)")
    void testAddDuplicateTag() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "BREAKFAST");
        mockDataAccess.setExistingTags(Arrays.asList("breakfast", "lunch", "dinner"));
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isFailureViewCalled());
        assertFalse(mockPresenter.isSuccessViewCalled());
        assertEquals("Tag already exists.", mockPresenter.getFailureMessage());
    }
    
    @Test
    @DisplayName("Should fail when tag already exists with different case and spacing")
    void testAddDuplicateTagWithSpacing() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, " BREAKFAST ");
        mockDataAccess.setExistingTags(Arrays.asList("breakfast", "lunch"));
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isFailureViewCalled());
        assertEquals("Tag already exists.", mockPresenter.getFailureMessage());
    }
    
    @Test
    @DisplayName("Should handle null tags in existing tags list")
    void testHandleNullTagsInExistingList() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "breakfast");
        mockDataAccess.setExistingTags(Arrays.asList("lunch", null, "dinner"));
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isSuccessViewCalled());
        assertFalse(mockPresenter.isFailureViewCalled());
    }
    
    @Test
    @DisplayName("Should trim tag name before processing")
    void testTrimTagName() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "  breakfast  ");
        mockDataAccess.setExistingTags(new ArrayList<>());
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isSuccessViewCalled());
        assertEquals("breakfast", mockDataAccess.getLastAddedTag());
    }
    
    @Test
    @DisplayName("Should handle empty existing tags list")
    void testEmptyExistingTagsList() {
        // Arrange
        AddTagInputData inputData = new AddTagInputData("testUser", 123, "breakfast");
        mockDataAccess.setExistingTags(new ArrayList<>());
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.isSuccessViewCalled());
        assertFalse(mockPresenter.isFailureViewCalled());
    }
    
    @Test
    @DisplayName("Should handle various recipe IDs")
    void testVariousRecipeIds() {
        // Test with different recipe IDs
        int[] recipeIds = {1, 999999, 0, -1};
        
        for (int recipeId : recipeIds) {
            setUp(); // Reset mocks
            AddTagInputData inputData = new AddTagInputData("testUser", recipeId, "tag");
            mockDataAccess.setExistingTags(new ArrayList<>());
            
            // Act
            interactor.execute(inputData);
            
            // Assert
            assertTrue(mockPresenter.isSuccessViewCalled(), "Should succeed for recipe ID: " + recipeId);
            assertEquals(recipeId, mockDataAccess.getLastRecipeId());
        }
    }
    
    /**
     * Mock implementation of AddTagDataAccessInterface for testing.
     */
    private static class MockAddTagDataAccess implements AddTagDataAccessInterface {
        private List<String> existingTags = new ArrayList<>();
        private String lastUsername;
        private int lastRecipeId;
        private String lastAddedTag;
        
        public void setExistingTags(List<String> tags) {
            this.existingTags = tags;
        }
        
        public String getLastUsername() { return lastUsername; }
        public int getLastRecipeId() { return lastRecipeId; }
        public String getLastAddedTag() { return lastAddedTag; }
        
        @Override
        public List<String> getTagsForRecipe(String username, int recipeId) {
            this.lastUsername = username;
            this.lastRecipeId = recipeId;
            return new ArrayList<>(existingTags);
        }
        
        @Override
        public void addTagToRecipe(String username, int recipeId, String tagName) {
            this.lastUsername = username;
            this.lastRecipeId = recipeId;
            this.lastAddedTag = tagName;
            existingTags = new ArrayList<>(existingTags);
            existingTags.add(tagName);
        }
    }
    
    /**
     * Mock implementation of AddTagOutputBoundary for testing.
     */
    private static class MockAddTagOutputBoundary implements AddTagOutputBoundary {
        private boolean successViewCalled = false;
        private boolean failureViewCalled = false;
        private String failureMessage;
        private AddTagOutputData successOutputData;
        
        public boolean isSuccessViewCalled() { return successViewCalled; }
        public boolean isFailureViewCalled() { return failureViewCalled; }
        public String getFailureMessage() { return failureMessage; }
        public AddTagOutputData getSuccessOutputData() { return successOutputData; }
        
        @Override
        public void prepareSuccessView(AddTagOutputData outputData) {
            this.successViewCalled = true;
            this.successOutputData = outputData;
        }
        
        @Override
        public void prepareFailView(String errorMessage) {
            this.failureViewCalled = true;
            this.failureMessage = errorMessage;
        }
    }
}
