package use_case.add_recipe;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

/**
 * Comprehensive test suite for AddRecipeInteractor.
 * 
 * This test class verifies the business logic for adding recipes,
 * including successful recipe creation, validation errors, and system error handling.
 * 
 * Test Categories:
 * - Successful recipe addition with various configurations
 * - Input validation failures (null/empty title, ingredients)
 * - Edge cases with different ingredient/tag combinations
 * - System error handling (data access exceptions)
 * 
 * All tests use mock implementations to isolate the unit under test.
 */
class AddRecipeInteractorTest {

    @Test
    void executeSuccessfulRecipeAddition() {
        // Given - Valid recipe data
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("Flour", "2.0", "cups"),
            new Ingredient("Sugar", "1.0", "cup")
        );
        List<Tag> tags = Arrays.asList(new Tag(1, "dessert"), new Tag(2, "easy"));

        AddRecipeInputData inputData = new AddRecipeInputData(
            123, "Chocolate Cake", ingredients, "Mix and bake", 
            "American", 45, "Dessert", 8, tags
        );

        // When
        interactor.execute(inputData);

        // Then - Verify successful addition
        assertTrue(presenter.isSuccessful(), "Recipe should be added successfully");
        assertEquals("Recipe added successfully!", presenter.getMessage());
        assertTrue(dataAccess.isSaveRecipeCalled(), "Should save recipe to data access");
        assertEquals(username, dataAccess.getSavedUsername());
        
        // Verify recipe details
        Recipe savedRecipe = dataAccess.getSavedRecipe();
        assertEquals("Chocolate Cake", savedRecipe.getTitle());
        assertEquals(ingredients.size(), savedRecipe.getIngredients().size());
        assertEquals("Mix and bake", savedRecipe.getInstructions());
        assertEquals(45, savedRecipe.getCookingTime());
    }

    @Test
    void executeSuccessfulRecipeAdditionWithMinimalData() {
        // Given - Minimal valid recipe data
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        List<Ingredient> ingredients = Collections.singletonList(
            new Ingredient("Bread", "2", "slices")
        );

        AddRecipeInputData inputData = new AddRecipeInputData(
            null, "Simple Toast", ingredients, null, 
            null, null, null, null, Collections.emptyList()
        );

        // When
        interactor.execute(inputData);

        // Then - Should succeed with minimal data
        assertTrue(presenter.isSuccessful());
        assertEquals("Recipe added successfully!", presenter.getMessage());
        assertTrue(dataAccess.isSaveRecipeCalled());
        
        Recipe savedRecipe = dataAccess.getSavedRecipe();
        assertEquals("Simple Toast", savedRecipe.getTitle());
        assertEquals(1, savedRecipe.getIngredients().size());
    }

    @Test
    void executeFailsWithEmptyTitle() {
        // Given - Empty title
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        List<Ingredient> ingredients = Collections.singletonList(
            new Ingredient("Flour", "1.0", "cup")
        );

        AddRecipeInputData inputData = new AddRecipeInputData(
            123, "", ingredients, "Instructions", 
            "Italian", 30, "Lunch", 4, Collections.emptyList()
        );

        // When
        interactor.execute(inputData);

        // Then - Should fail validation
        assertFalse(presenter.isSuccessful(), "Should fail with empty title");
        assertEquals("Recipe title cannot be empty", presenter.getMessage());
        assertFalse(dataAccess.isSaveRecipeCalled(), "Should not save recipe");
    }

    @Test
    void executeFailsWithNullTitle() {
        // Given - Null title
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        List<Ingredient> ingredients = Collections.singletonList(
            new Ingredient("Flour", "1.0", "cup")
        );

        AddRecipeInputData inputData = new AddRecipeInputData(
            123, null, ingredients, "Instructions", 
            "Italian", 30, "Lunch", 4, Collections.emptyList()
        );

        // When
        interactor.execute(inputData);

        // Then - Should fail validation
        assertFalse(presenter.isSuccessful());
        assertEquals("Recipe title cannot be empty", presenter.getMessage());
        assertFalse(dataAccess.isSaveRecipeCalled());
    }

    @Test
    void executeFailsWithEmptyIngredients() {
        // Given - Empty ingredients list
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        AddRecipeInputData inputData = new AddRecipeInputData(
            123, "Test Recipe", Collections.emptyList(), "Instructions", 
            "Italian", 30, "Lunch", 4, Collections.emptyList()
        );

        // When
        interactor.execute(inputData);

        // Then - Should fail validation
        assertFalse(presenter.isSuccessful());
        assertEquals("Ingredients cannot be empty", presenter.getMessage());
        assertFalse(dataAccess.isSaveRecipeCalled());
    }

    @Test
    void executeFailsWithNullIngredients() {
        // Given - Null ingredients
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        AddRecipeInputData inputData = new AddRecipeInputData(
            123, "Test Recipe", null, "Instructions", 
            "Italian", 30, "Lunch", 4, Collections.emptyList()
        );

        // When
        interactor.execute(inputData);

        // Then - Should fail validation
        assertFalse(presenter.isSuccessful());
        assertEquals("Ingredients cannot be empty", presenter.getMessage());
        assertFalse(dataAccess.isSaveRecipeCalled());
    }

    @Test
    void executeHandlesDataAccessException() {
        // Given - Data access that throws exception
        MockAddRecipeDataAccess dataAccess = new MockAddRecipeDataAccess();
        MockAddRecipePresenter presenter = new MockAddRecipePresenter();
        String username = "testuser";
        AddRecipeInteractor interactor = new AddRecipeInteractor(dataAccess, presenter, username);

        dataAccess.setThrowException(true);

        List<Ingredient> ingredients = Collections.singletonList(
            new Ingredient("Flour", "1.0", "cup")
        );

        AddRecipeInputData inputData = new AddRecipeInputData(
            123, "Test Recipe", ingredients, "Instructions", 
            "Italian", 30, "Lunch", 4, Collections.emptyList()
        );

        // When/Then - Should throw exception (not handled in current implementation)
        assertThrows(RuntimeException.class, () -> interactor.execute(inputData));
    }

    /**
     * Mock implementation of AddRecipeDataAccessInterface for testing.
     */
    private static class MockAddRecipeDataAccess implements AddRecipeDataAccessInterface {
        private boolean saveRecipeCalled = false;
        private boolean throwException = false;
        private String savedUsername;
        private Recipe savedRecipe;

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        @Override
        public void saveRecipe(String username, Recipe recipe) {
            saveRecipeCalled = true;
            savedUsername = username;
            savedRecipe = recipe;
            if (throwException) {
                throw new RuntimeException("Database save error");
            }
        }

        @Override
        public List<Recipe> loadRecipes(String username) {
            // Not used in these tests
            return Collections.emptyList();
        }

        // Getters for verification
        public boolean isSaveRecipeCalled() { return saveRecipeCalled; }
        public String getSavedUsername() { return savedUsername; }
        public Recipe getSavedRecipe() { return savedRecipe; }
    }

    /**
     * Mock implementation of AddRecipeOutputBoundary for testing.
     */
    private static class MockAddRecipePresenter implements AddRecipeOutputBoundary {
        private boolean presentCalled = false;
        private AddRecipeOutputData outputData;

        @Override
        public void present(AddRecipeOutputData outputData) {
            presentCalled = true;
            this.outputData = outputData;
        }

        // Getters for verification
        public boolean isPresentCalled() { return presentCalled; }
        public boolean isSuccessful() { return outputData != null && outputData.isSuccess(); }
        public String getMessage() { return outputData != null ? outputData.getMessage() : null; }
    }
}
