package use_case.save_recipe;

import entity.Recipe;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Comprehensive test suite for SaveRecipeInteractor.
 * 
 * This test class verifies the business logic for saving recipes to user collections,
 * including successful saves, duplicate detection, and error handling.
 * 
 * Test Categories:
 * - Successful recipe saving
 * - Duplicate recipe detection
 * - System error handling (data access exceptions)
 * - Edge cases with null/invalid data
 * 
 * All tests use mock implementations to isolate the unit under test.
 */
class SaveRecipeInteractorTest {

    @Test
    void executeSuccessfulRecipeSave() {
        // Given - Valid recipe and user
        MockSaveRecipeDataAccess dataAccess = new MockSaveRecipeDataAccess();
        MockSaveRecipePresenter presenter = new MockSaveRecipePresenter();
        SaveRecipeInteractor interactor = new SaveRecipeInteractor(dataAccess, presenter);

        Recipe recipe = createTestRecipe();
        String username = "testuser";

        dataAccess.setIsRecipeSaved(false);
        dataAccess.setSaveSuccess(true);

        SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);

        // When
        interactor.execute(inputData);

        // Then - Verify successful save
        assertTrue(presenter.isSuccessCalled(), "Success presenter should be called");
        assertFalse(presenter.isFailureCalled(), "Failure presenter should not be called");
        
        SaveRecipeOutputData outputData = presenter.getOutputData();
        assertNotNull(outputData, "Output data should not be null");
        assertTrue(outputData.isSuccess());
        assertEquals("Recipe saved successfully!", outputData.getMessage());
        assertEquals("Test Recipe", outputData.getRecipeName());

        // Verify data access calls
        assertTrue(dataAccess.isRecipeSavedCalled());
        assertTrue(dataAccess.isSaveRecipeForUserCalled());
        assertEquals(username, dataAccess.getCheckedUsername());
        assertEquals(recipe.getRecipeId(), dataAccess.getCheckedRecipeId());
        assertEquals(username, dataAccess.getSavedUsername());
        assertEquals(recipe, dataAccess.getSavedRecipe());
    }

    @Test
    void executeFailsWhenRecipeAlreadySaved() {
        // Given - Recipe already saved for user
        MockSaveRecipeDataAccess dataAccess = new MockSaveRecipeDataAccess();
        MockSaveRecipePresenter presenter = new MockSaveRecipePresenter();
        SaveRecipeInteractor interactor = new SaveRecipeInteractor(dataAccess, presenter);

        Recipe recipe = createTestRecipe();
        String username = "testuser";

        dataAccess.setIsRecipeSaved(true); // Recipe already saved

        SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);

        // When
        interactor.execute(inputData);

        // Then - Should fail with appropriate message
        assertTrue(presenter.isFailureCalled(), "Failure presenter should be called");
        assertFalse(presenter.isSuccessCalled(), "Success presenter should not be called");
        
        String errorMessage = presenter.getErrorMessage();
        assertTrue(errorMessage.contains("Recipe 'Test Recipe' is already saved!"),
                  "Error message should indicate recipe already saved");

        // Verify only check was called, not save
        assertTrue(dataAccess.isRecipeSavedCalled());
        assertFalse(dataAccess.isSaveRecipeForUserCalled(), 
                   "Should not attempt save if already saved");
    }

    @Test
    void executeFailsWhenDataAccessSaveFails() {
        // Given - Data access save operation fails
        MockSaveRecipeDataAccess dataAccess = new MockSaveRecipeDataAccess();
        MockSaveRecipePresenter presenter = new MockSaveRecipePresenter();
        SaveRecipeInteractor interactor = new SaveRecipeInteractor(dataAccess, presenter);

        Recipe recipe = createTestRecipe();
        String username = "testuser";

        dataAccess.setIsRecipeSaved(false);
        dataAccess.setSaveSuccess(false); // Save fails

        SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);

        // When
        interactor.execute(inputData);

        // Then - Should fail with generic error message
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Failed to save recipe. Please try again.", presenter.getErrorMessage());

        // Verify both operations were attempted
        assertTrue(dataAccess.isRecipeSavedCalled());
        assertTrue(dataAccess.isSaveRecipeForUserCalled());
    }

    @Test
    void executeHandlesExceptionDuringCheck() {
        // Given - Exception during recipe saved check
        MockSaveRecipeDataAccess dataAccess = new MockSaveRecipeDataAccess();
        MockSaveRecipePresenter presenter = new MockSaveRecipePresenter();
        SaveRecipeInteractor interactor = new SaveRecipeInteractor(dataAccess, presenter);

        Recipe recipe = createTestRecipe();
        String username = "testuser";

        dataAccess.setThrowExceptionOnCheck(true);

        SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);

        // When
        interactor.execute(inputData);

        // Then - Should handle exception gracefully
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        
        String errorMessage = presenter.getErrorMessage();
        assertTrue(errorMessage.contains("Error saving recipe:"));
        assertTrue(errorMessage.contains("Check operation failed"));
    }

    @Test
    void executeHandlesExceptionDuringSave() {
        // Given - Exception during save operation
        MockSaveRecipeDataAccess dataAccess = new MockSaveRecipeDataAccess();
        MockSaveRecipePresenter presenter = new MockSaveRecipePresenter();
        SaveRecipeInteractor interactor = new SaveRecipeInteractor(dataAccess, presenter);

        Recipe recipe = createTestRecipe();
        String username = "testuser";

        dataAccess.setIsRecipeSaved(false);
        dataAccess.setThrowExceptionOnSave(true);

        SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);

        // When
        interactor.execute(inputData);

        // Then - Should handle exception gracefully
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        
        String errorMessage = presenter.getErrorMessage();
        assertTrue(errorMessage.contains("Error saving recipe:"));
        assertTrue(errorMessage.contains("Save operation failed"));
    }

    @Test
    void executeSuccessfulSaveWithDifferentRecipeTypes() {
        // Given - Different types of recipes
        MockSaveRecipeDataAccess dataAccess = new MockSaveRecipeDataAccess();
        MockSaveRecipePresenter presenter = new MockSaveRecipePresenter();
        SaveRecipeInteractor interactor = new SaveRecipeInteractor(dataAccess, presenter);

        String username = "testuser";
        
        // Test with recipe with minimal data
        Recipe minimalRecipe = new Recipe(999, Collections.singletonList(
            new Ingredient("Salt", "1", "pinch")), 
            "Simple Salt", null, null, null, null, null, Collections.emptyList());

        dataAccess.setIsRecipeSaved(false);
        dataAccess.setSaveSuccess(true);

        SaveRecipeInputData inputData = new SaveRecipeInputData(username, minimalRecipe);

        // When
        interactor.execute(inputData);

        // Then - Should succeed with minimal recipe
        assertTrue(presenter.isSuccessCalled());
        assertEquals("Simple Salt", presenter.getOutputData().getRecipeName());
    }

    private Recipe createTestRecipe() {
        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("Flour", "2", "cups"),
            new Ingredient("Sugar", "1", "cup")
        );
        return new Recipe(123, ingredients, "Test Recipe", "Mix and bake", 
                         "American", 30, "Dessert", 8, Collections.emptyList());
    }

    /**
     * Mock implementation of SaveRecipeDataAccessInterface for testing.
     */
    private static class MockSaveRecipeDataAccess implements SaveRecipeDataAccessInterface {
        private boolean isRecipeSavedCalled = false;
        private boolean saveRecipeForUserCalled = false;
        private boolean throwExceptionOnCheck = false;
        private boolean throwExceptionOnSave = false;
        private boolean isRecipeSaved = false;
        private boolean saveSuccess = true;
        
        // For verification
        private String checkedUsername;
        private Integer checkedRecipeId;
        private String savedUsername;
        private Recipe savedRecipe;

        public void setIsRecipeSaved(boolean isRecipeSaved) {
            this.isRecipeSaved = isRecipeSaved;
        }

        public void setSaveSuccess(boolean saveSuccess) {
            this.saveSuccess = saveSuccess;
        }

        public void setThrowExceptionOnCheck(boolean throwException) {
            this.throwExceptionOnCheck = throwException;
        }

        public void setThrowExceptionOnSave(boolean throwException) {
            this.throwExceptionOnSave = throwException;
        }

        @Override
        public boolean isRecipeSaved(String username, Integer recipeId) {
            isRecipeSavedCalled = true;
            checkedUsername = username;
            checkedRecipeId = recipeId;
            if (throwExceptionOnCheck) {
                throw new RuntimeException("Check operation failed");
            }
            return isRecipeSaved;
        }

        @Override
        public boolean saveRecipeForUser(String username, Recipe recipe) {
            saveRecipeForUserCalled = true;
            savedUsername = username;
            savedRecipe = recipe;
            if (throwExceptionOnSave) {
                throw new RuntimeException("Save operation failed");
            }
            return saveSuccess;
        }

        // Getters for verification
        public boolean isRecipeSavedCalled() { return isRecipeSavedCalled; }
        public boolean isSaveRecipeForUserCalled() { return saveRecipeForUserCalled; }
        public String getCheckedUsername() { return checkedUsername; }
        public Integer getCheckedRecipeId() { return checkedRecipeId; }
        public String getSavedUsername() { return savedUsername; }
        public Recipe getSavedRecipe() { return savedRecipe; }
    }

    /**
     * Mock implementation of SaveRecipeOutputBoundary for testing.
     */
    private static class MockSaveRecipePresenter implements SaveRecipeOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private SaveRecipeOutputData outputData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(SaveRecipeOutputData outputData) {
            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failureCalled = true;
            this.errorMessage = errorMessage;
        }

        // Getters for verification
        public boolean isSuccessCalled() { return successCalled; }
        public boolean isFailureCalled() { return failureCalled; }
        public SaveRecipeOutputData getOutputData() { return outputData; }
        public String getErrorMessage() { return errorMessage; }
    }
}
