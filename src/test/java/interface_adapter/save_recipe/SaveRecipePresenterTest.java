package interface_adapter.save_recipe;

import entity.Recipe;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.save_recipe.SaveRecipeOutputData;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SaveRecipePresenterTest {

    private SaveRecipeViewModel viewModel;
    private SaveRecipePresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new SaveRecipeViewModel();
        presenter = new SaveRecipePresenter(viewModel);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        Recipe recipe = createTestRecipe();
        SaveRecipeOutputData outputData = new SaveRecipeOutputData(true, "Recipe saved successfully!", recipe.getTitle());

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("Recipe saved successfully!", state.getMessage());
        assertEquals(recipe.getTitle(), state.getRecipeName());
        assertTrue(state.isSuccess());
    }

    @Test
    void testPrepareFailView() {
        // Arrange
        String errorMessage = "Recipe already saved";

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        SaveRecipeState state = viewModel.getState();
        assertNotNull(state);
        assertEquals(errorMessage, state.getMessage());
        assertFalse(state.isSuccess());
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

    // Helper method to create test recipe
    private Recipe createTestRecipe() {
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "1", "pc"));
        return new Recipe(1, ingredients, "Test Recipe", "Test instructions", "Italian", 30, "Dinner", 1, null);
    }
}
