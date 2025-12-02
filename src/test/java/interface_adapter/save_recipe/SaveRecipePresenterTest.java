package interface_adapter.save_recipe;

import entity.Recipe;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.save_recipe.SaveRecipeOutputData;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test implementation that captures UI notifications without showing actual dialogs.
 */
class TestSaveRecipeUINotifier implements SaveRecipeUINotifier {
    private String lastSuccessMessage;
    private String lastErrorMessage;
    private int successCallCount = 0;
    private int errorCallCount = 0;

    @Override
    public void showSuccessMessage(String message) {
        lastSuccessMessage = message;
        successCallCount++;
    }

    @Override
    public void showErrorMessage(String message) {
        lastErrorMessage = message;
        errorCallCount++;
    }

    public String getLastSuccessMessage() { return lastSuccessMessage; }
    public String getLastErrorMessage() { return lastErrorMessage; }
    public int getSuccessCallCount() { return successCallCount; }
    public int getErrorCallCount() { return errorCallCount; }

    public void reset() {
        lastSuccessMessage = null;
        lastErrorMessage = null;
        successCallCount = 0;
        errorCallCount = 0;
    }
}

class SaveRecipePresenterTest {

    private SaveRecipeViewModel viewModel;
    private SaveRecipePresenter presenter;
    private TestSaveRecipeUINotifier testUINotifier;

    @BeforeEach
    void setUp() {
        viewModel = new SaveRecipeViewModel();
        testUINotifier = new TestSaveRecipeUINotifier();
        presenter = new SaveRecipePresenter(viewModel, testUINotifier);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        Recipe recipe = createTestRecipe();
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(true, "Recipe saved successfully!", recipe.getTitle());

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert - View Model State
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("Recipe saved successfully!", state.getMessage());
        assertEquals(recipe.getTitle(), state.getRecipeName());
        assertTrue(state.isSuccess());
        
        // Assert - UI Notification
        assertEquals("Recipe saved successfully!", testUINotifier.getLastSuccessMessage());
        assertEquals(1, testUINotifier.getSuccessCallCount());
        assertEquals(0, testUINotifier.getErrorCallCount());
    }

    @Test
    void testPrepareFailView() {
        // Arrange
        String errorMessage = "Recipe already saved";

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert - View Model State
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals(errorMessage, state.getMessage());
        assertFalse(state.isSuccess());
        
        // Assert - UI Notification
        assertEquals(errorMessage, testUINotifier.getLastErrorMessage());
        assertEquals(1, testUINotifier.getErrorCallCount());
        assertEquals(0, testUINotifier.getSuccessCallCount());
    }

    @Test
    void testPrepareFailView_WithDifferentErrorMessage() {
        // Arrange
        String errorMessage = "System error";

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals(errorMessage, state.getMessage());
        assertFalse(state.isSuccess());
    }

    @Test
    void testPrepareSuccessView_OverwritesPreviousFailure() {
        // Arrange
        presenter.prepareFailView("Previous error");
        SaveRecipeState firstState = viewModel.getState();
        assertEquals("Previous error", firstState.getMessage());
        assertFalse(firstState.isSuccess());
        
        Recipe recipe = createTestRecipe();
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(true, "Recipe saved successfully!", recipe.getTitle());

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("Recipe saved successfully!", state.getMessage()); // Success message overwrites error
        assertEquals(recipe.getTitle(), state.getRecipeName());
        assertTrue(state.isSuccess());
    }

    @Test
    void testPrepareFailView_OverwritesPreviousErrorMessage() {
        // Arrange
        String firstErrorMessage = "First error";
        String secondErrorMessage = "Second error";
        
        // Act
        presenter.prepareFailView(firstErrorMessage);
        SaveRecipeState firstState = viewModel.getState();
        assertEquals(firstErrorMessage, firstState.getMessage());
        
        presenter.prepareFailView(secondErrorMessage);
        SaveRecipeState secondState = viewModel.getState();

        // Assert
        assertEquals(secondErrorMessage, secondState.getMessage());
        assertFalse(secondState.isSuccess());
    }

    @Test
    void testPrepareSuccessView_HandlesNullMessage() {
        // Arrange
        Recipe recipe = createTestRecipe();
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(true, null, recipe.getTitle());

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getMessage()); // Null message should be preserved
        assertEquals(recipe.getTitle(), state.getRecipeName());
        assertTrue(state.isSuccess());
    }

    @Test
    void testPrepareFailView_HandlesNullErrorMessage() {
        // Arrange
        String errorMessage = null;

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getMessage()); // Null error message should be preserved
        assertFalse(state.isSuccess());
    }

    @Test
    void testPrepareSuccessView_HandlesNullRecipeName() {
        // Arrange
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(true, "Success message", null);

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("Success message", state.getMessage());
        assertNull(state.getRecipeName());
        assertTrue(state.isSuccess());
    }

    @Test
    void testUINotifierResetFunctionality() {
        // Arrange
        SaveRecipeOutputData successData = new SaveRecipeOutputData(true, "Success", "Recipe1");
        
        // Act
        presenter.prepareSuccessView(successData);
        presenter.prepareFailView("Error message");
        
        // Assert - Both notifications were called
        assertEquals("Success", testUINotifier.getLastSuccessMessage());
        assertEquals("Error message", testUINotifier.getLastErrorMessage());
        assertEquals(1, testUINotifier.getSuccessCallCount());
        assertEquals(1, testUINotifier.getErrorCallCount());
        
        // Reset and verify
        testUINotifier.reset();
        assertNull(testUINotifier.getLastSuccessMessage());
        assertNull(testUINotifier.getLastErrorMessage());
        assertEquals(0, testUINotifier.getSuccessCallCount());
        assertEquals(0, testUINotifier.getErrorCallCount());
    }



    // Helper method to create test recipe
    private Recipe createTestRecipe() {
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "1", "pc"));
        return new Recipe(1, ingredients, "Test Recipe", "Test instructions", "Italian", 30, "Dinner", 1, null);
    }
}
