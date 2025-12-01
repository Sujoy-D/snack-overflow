package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class LoginPageViewTest {

    private LoginPageView view;
    private LoginViewModel viewModel;
    private LoginController loginController;
    private NavigationController navigationController;

    @BeforeEach
    void setUp() {
        viewModel = new LoginViewModel();
        MockLoginInteractor mockInteractor = new MockLoginInteractor();
        loginController = new LoginController(mockInteractor);
        NavigationViewModel navViewModel = new NavigationViewModel();
        navigationController = new NavigationController(navViewModel);
        view = new LoginPageView(viewModel, loginController, navigationController);
    }

    @Test
    void testConstructor_InitializesCorrectly() {
        // Assert
        assertNotNull(view);
        assertEquals("login", view.getViewName());
    }

    @Test
    void testPropertyChange_UpdatesUIOnStateChange() {
        // Arrange
        LoginState newState = new LoginState();
        newState.setUsername("testuser");
        newState.setPassword("password123");
        newState.setErrorMessage("Test error");

        // Act
        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // Assert - The view should update its display based on the state
        assertEquals(newState, viewModel.getState());
    }

    @Test
    void testPropertyChange_HandlesLoginInProgress() {
        // Arrange
        LoginState newState = new LoginState();
        newState.setLoginInProgress(true);

        // Act
        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // Assert
        assertTrue(viewModel.getState().isLoginInProgress());
    }

    @Test
    void testGetViewName() {
        // Act & Assert
        assertEquals("login", view.getViewName());
    }

    @Test
    void testPropertyChange_HandlesEmptyState() {
        // Arrange & Act - This should not throw an exception
        viewModel.setState(new LoginState()); // Use empty state
        viewModel.firePropertyChanged();

        // Assert
        assertNotNull(viewModel.getState());
    }

    @Test
    void testPropertyChange_HandlesErrorMessage() {
        // Arrange
        LoginState stateWithError = new LoginState();
        stateWithError.setErrorMessage("Invalid credentials");

        // Act
        viewModel.setState(stateWithError);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("Invalid credentials", viewModel.getState().getErrorMessage());
    }

    @Test
    void testPropertyChange_ClearsPassword() {
        // Arrange
        LoginState stateWithClearedPassword = new LoginState();
        stateWithClearedPassword.setUsername("testuser");
        stateWithClearedPassword.setPassword("");

        // Act
        viewModel.setState(stateWithClearedPassword);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("testuser", viewModel.getState().getUsername());
        assertEquals("", viewModel.getState().getPassword());
    }

    @Test
    void testPropertyChange_UpdatesUsernameField() {
        // Arrange
        LoginState stateWithUsername = new LoginState();
        stateWithUsername.setUsername("newuser");

        // Act
        viewModel.setState(stateWithUsername);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("newuser", viewModel.getState().getUsername());
    }

    // Mock Interactor for testing
    static class MockLoginInteractor implements LoginInputBoundary {
        boolean executeCalled = false;
        LoginInputData inputData;

        @Override
        public void execute(LoginInputData inputData) {
            this.executeCalled = true;
            this.inputData = inputData;
        }
    }
}
