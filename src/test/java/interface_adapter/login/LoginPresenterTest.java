package interface_adapter.login;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.login.LoginOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LoginPresenterTest {

    private LoginViewModel viewModel;
    private NavigationController navigationController;
    private LoginPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new LoginViewModel();
        NavigationViewModel navViewModel = new NavigationViewModel();
        navigationController = new NavigationController(navViewModel);
        presenter = new LoginPresenter(viewModel, navigationController);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        LoginOutputData outputData = new LoginOutputData("testuser", true, "Login successful");

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        LoginState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getErrorMessage());
        assertEquals("", state.getUsername()); // New state is created, so username is empty
        assertFalse(state.isLoginInProgress());
        
        // Navigation should occur (though timer-based testing is complex)
    }

    @Test
    void testPrepareFailView() {
        // Arrange
        String errorMessage = "Invalid username or password";
        LoginState currentState = new LoginState();
        currentState.setUsername("testuser");
        currentState.setPassword("password123");
        viewModel.setState(currentState);

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        LoginState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("testuser", state.getUsername());
        assertEquals("", state.getPassword()); // Password should be cleared for security
        assertEquals(errorMessage, state.getErrorMessage());
        assertFalse(state.isLoginInProgress());
    }

    @Test
    void testPrepareFailView_WithEmptyCurrentState() {
        // Arrange
        String errorMessage = "System error";
        LoginState currentState = new LoginState(); // Empty state
        viewModel.setState(currentState);

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        LoginState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("", state.getUsername()); // Default empty string
        assertEquals("", state.getPassword());
        assertEquals(errorMessage, state.getErrorMessage());
        assertFalse(state.isLoginInProgress());
    }

    @Test
    void testPrepareFailView_PreservesUsername() {
        // Arrange
        String errorMessage = "Password too short";
        LoginState currentState = new LoginState();
        currentState.setUsername("preserveduser");
        currentState.setPassword("short");
        currentState.setErrorMessage("old error");
        viewModel.setState(currentState);

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        LoginState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("preserveduser", state.getUsername()); // Username preserved
        assertEquals("", state.getPassword()); // Password cleared
        assertEquals(errorMessage, state.getErrorMessage()); // New error message
        assertFalse(state.isLoginInProgress());
    }

    @Test
    void testPrepareSuccessView_ClearsErrorMessages() {
        // Arrange
        LoginState currentState = new LoginState();
        currentState.setErrorMessage("Previous error");
        viewModel.setState(currentState);
        
        LoginOutputData outputData = new LoginOutputData("testuser", true, "Login successful");

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        LoginState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getErrorMessage()); // Error message should be cleared
        assertEquals("", state.getUsername()); // New state is created, so username is empty
        assertFalse(state.isLoginInProgress());
    }
}
