package interface_adapter.logout;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.logout.LogoutOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LogoutPresenterTest {

    private LogoutViewModel viewModel;
    private NavigationController navigationController;
    private LogoutPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new LogoutViewModel();
        NavigationViewModel navViewModel = new NavigationViewModel();
        navigationController = new NavigationController(navViewModel);
        presenter = new LogoutPresenter(viewModel, navigationController);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        LogoutOutputData outputData = new LogoutOutputData("testuser", true, "Logged out successfully");

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        LogoutState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("Logged out successfully", state.getMessage());
        assertFalse(state.isLogoutInProgress());
        
        // Navigation should occur (though timer-based testing is complex)
    }

    @Test
    void testPrepareFailView() {
        // Arrange
        String errorMessage = "No user is currently logged in";

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        LogoutState state = viewModel.getState();
        assertNotNull(state);
        assertEquals(errorMessage, state.getMessage());
        assertFalse(state.isLogoutInProgress());
    }

    @Test
    void testPrepareFailView_WithDifferentErrorMessage() {
        // Arrange
        String errorMessage = "System error";

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        LogoutState state = viewModel.getState();
        assertNotNull(state);
        assertEquals(errorMessage, state.getMessage());
        assertFalse(state.isLogoutInProgress());
    }

    @Test
    void testPrepareFailView_OverwritesPreviousMessage() {
        // Arrange
        String firstErrorMessage = "First error";
        String secondErrorMessage = "Second error";
        
        // Act
        presenter.prepareFailView(firstErrorMessage);
        LogoutState firstState = viewModel.getState();
        assertEquals(firstErrorMessage, firstState.getMessage());
        
        presenter.prepareFailView(secondErrorMessage);
        LogoutState secondState = viewModel.getState();

        // Assert
        assertEquals(secondErrorMessage, secondState.getMessage());
        assertFalse(secondState.isLogoutInProgress());
    }

    @Test
    void testPrepareSuccessView_OverwritesPreviousErrorMessage() {
        // Arrange
        presenter.prepareFailView("Previous error");
        LogoutOutputData outputData = new LogoutOutputData("testuser", true, "Logged out successfully");

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        LogoutState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("Logged out successfully", state.getMessage()); // Success message overwrites error
        assertFalse(state.isLogoutInProgress());
    }

    @Test
    void testPrepareSuccessView_HandlesNullMessage() {
        // Arrange
        LogoutOutputData outputData = new LogoutOutputData("testuser", true, null);

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        LogoutState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getMessage()); // Null message should be preserved
        assertFalse(state.isLogoutInProgress());
    }

    @Test
    void testPrepareFailView_HandlesNullErrorMessage() {
        // Arrange
        String errorMessage = null;

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        LogoutState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getMessage()); // Null error message should be preserved
        assertFalse(state.isLogoutInProgress());
    }
}
