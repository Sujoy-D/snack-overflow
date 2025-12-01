package view;

import interface_adapter.save_recipe.SaveRecipeState;
import interface_adapter.save_recipe.SaveRecipeViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class focusing on SaveRecipeViewModel state management rather than the SavedRecipesView
 * since the view has complex UI dependencies that are difficult to test in isolation.
 */
class SavedRecipesViewModelTest {

    private SaveRecipeViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = new SaveRecipeViewModel();
    }

    @Test
    void testViewModel_InitializesCorrectly() {
        // Assert
        assertNotNull(viewModel);
        assertNotNull(viewModel.getState());
    }

    @Test
    void testSetState_UpdatesState() {
        // Arrange
        SaveRecipeState newState = new SaveRecipeState();
        newState.setMessage("Recipe saved");
        newState.setRecipeName("Test Recipe");

        // Act
        viewModel.setState(newState);

        // Assert
        assertEquals(newState, viewModel.getState());
        assertEquals("Recipe saved", viewModel.getState().getMessage());
        assertEquals("Test Recipe", viewModel.getState().getRecipeName());
    }

    @Test
    void testState_HandlesEmptyState() {
        // Arrange & Act
        SaveRecipeState emptyState = new SaveRecipeState();
        viewModel.setState(emptyState);

        // Assert
        assertNotNull(viewModel.getState());
        assertFalse(viewModel.getState().isSuccess());
        assertEquals("", viewModel.getState().getMessage());
    }

    @Test
    void testState_HandlesSuccessState() {
        // Arrange
        SaveRecipeState successState = new SaveRecipeState();
        successState.setSuccess(true);
        successState.setMessage("Recipe saved successfully");
        successState.setRecipeName("Pasta");

        // Act
        viewModel.setState(successState);

        // Assert
        assertTrue(viewModel.getState().isSuccess());
        assertEquals("Recipe saved successfully", viewModel.getState().getMessage());
        assertEquals("Pasta", viewModel.getState().getRecipeName());
    }

    @Test
    void testState_HandlesFailureState() {
        // Arrange
        SaveRecipeState failureState = new SaveRecipeState();
        failureState.setSuccess(false);
        failureState.setMessage("Recipe already saved");

        // Act
        viewModel.setState(failureState);

        // Assert
        assertFalse(viewModel.getState().isSuccess());
        assertEquals("Recipe already saved", viewModel.getState().getMessage());
    }

    @Test
    void testFirePropertyChanged_DoesNotThrow() {
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> {
            viewModel.firePropertyChanged();
        });
    }
}
