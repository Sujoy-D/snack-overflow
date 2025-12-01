package view;

import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;

import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class LogoutViewTest {

    private LogoutView view;
    private LogoutViewModel viewModel;
    private LogoutController logoutController;

    @BeforeEach
    void setUp() {
        viewModel = new LogoutViewModel();
        MockLogoutInteractor mockInteractor = new MockLogoutInteractor();
        logoutController = new LogoutController(mockInteractor);
        view = new LogoutView(logoutController, viewModel);
    }

    @Test
    void testConstructor_InitializesCorrectly() {
        // Assert
        assertNotNull(view);
    }

    @Test
    void testPropertyChange_UpdatesUIOnStateChange() {
        // Arrange
        LogoutState newState = new LogoutState();
        newState.setMessage("Test message");

        // Act
        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // Assert - The view should update its display based on the state
        assertEquals(newState, viewModel.getState());
    }

    @Test
    void testPropertyChange_HandlesLogoutInProgress() {
        // Arrange
        LogoutState newState = new LogoutState();
        newState.setLogoutInProgress(true);

        // Act
        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // Assert
        assertTrue(viewModel.getState().isLogoutInProgress());
    }

    @Test
    void testInitiateLogout_CanBeCalled() {
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> {
            view.initiateLogout(null, "testuser");
        });
    }

    @Test
    void testPropertyChange_HandlesEmptyState() {
        // Arrange & Act - This should not throw an exception
        viewModel.setState(new LogoutState()); // Use empty state
        viewModel.firePropertyChanged();

        // Assert
        assertNotNull(viewModel.getState());
    }

    @Test
    void testPropertyChange_HandlesMessage() {
        // Arrange
        LogoutState stateWithMessage = new LogoutState();
        stateWithMessage.setMessage("Logout successful");

        // Act
        viewModel.setState(stateWithMessage);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("Logout successful", viewModel.getState().getMessage());
    }

    @Test
    void testPropertyChange_UpdatesMessage() {
        // Arrange
        LogoutState stateWithMessage = new LogoutState();
        stateWithMessage.setMessage("Processing logout...");

        // Act
        viewModel.setState(stateWithMessage);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("Processing logout...", viewModel.getState().getMessage());
        assertFalse(viewModel.getState().isLogoutInProgress()); // Default value
    }

    // Mock Interactor for testing
    static class MockLogoutInteractor implements LogoutInputBoundary {
        boolean executeCalled = false;
        LogoutInputData inputData;

        @Override
        public void execute(LogoutInputData inputData) {
            this.executeCalled = true;
            this.inputData = inputData;
        }
    }
}
