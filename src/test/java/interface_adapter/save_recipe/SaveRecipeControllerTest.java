package interface_adapter.save_recipe;

import entity.Recipe;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.save_recipe.SaveRecipeInputBoundary;
import use_case.save_recipe.SaveRecipeInputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveRecipeControllerTest {

    private MockSaveRecipeInteractor mockInteractor;
    private SaveRecipeController controller;

    @BeforeEach
    void setUp() {
        mockInteractor = new MockSaveRecipeInteractor();
        controller = new SaveRecipeController(mockInteractor);
    }

    @Test
    void testExecute_ValidInputs() {
        // Arrange
        String username = "testuser";
        Recipe recipe = createTestRecipe();

        // Act
        controller.execute(username, recipe);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(recipe, mockInteractor.inputData.getRecipe());
    }

    @Test
    void testExecute_NullUsername() {
        // Arrange
        String username = null;
        Recipe recipe = createTestRecipe();

        // Act
        controller.execute(username, recipe);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertNull(mockInteractor.inputData.getUsername());
        assertEquals(recipe, mockInteractor.inputData.getRecipe());
    }

    @Test
    void testExecute_NullRecipe() {
        // Arrange
        String username = "testuser";
        Recipe recipe = null;

        // Act
        controller.execute(username, recipe);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertNull(mockInteractor.inputData.getRecipe());
    }

    @Test
    void testExecute_EmptyUsername() {
        // Arrange
        String username = "";
        Recipe recipe = createTestRecipe();

        // Act
        controller.execute(username, recipe);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(recipe, mockInteractor.inputData.getRecipe());
    }

    @Test
    void testExecute_BothNull() {
        // Arrange
        String username = null;
        Recipe recipe = null;

        // Act
        controller.execute(username, recipe);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertNull(mockInteractor.inputData.getUsername());
        assertNull(mockInteractor.inputData.getRecipe());
    }

    // Helper method to create test recipe
    private Recipe createTestRecipe() {
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "1", "pc"));
        return new Recipe(1, ingredients, "Test Recipe", "Test instructions", "Italian", 30, "Dinner", 1, null);
    }

    // Mock Class
    static class MockSaveRecipeInteractor implements SaveRecipeInputBoundary {
        boolean executeCalled = false;
        SaveRecipeInputData inputData;

        @Override
        public void execute(SaveRecipeInputData inputData) {
            this.executeCalled = true;
            this.inputData = inputData;
        }
    }
}
