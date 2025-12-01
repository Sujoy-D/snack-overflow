package interface_adapter.signup;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.signup.SignUpOutputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SignUpPresenter.
 * Tests the presenter layer for signup functionality, ensuring proper
 * view model updates and navigation handling.
 */
class SignUpPresenterTest {

    private SignUpViewModel viewModel;
    private NavigationController navigationController;
    private SignUpPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new SignUpViewModel();
        NavigationViewModel navViewModel = new NavigationViewModel();
        navigationController = new NavigationController(navViewModel);
        presenter = new SignUpPresenter(viewModel, navigationController);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        SignUpOutputData outputData = new SignUpOutputData("testuser", true, "Account created successfully!");

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        SignUpState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getErrorMessage());
        assertEquals("Account created successfully!", state.getSuccessMessage());
        assertFalse(state.isSignUpInProgress());
        
        // Note: Timer-based navigation test would require more complex setup
        // Testing that timer is created would require dependency injection of Timer factory
    }

    @Test
    void testPrepareFailView() {
        // Arrange
        String errorMessage = "Username already exists";
        SignUpState currentState = new SignUpState();
        currentState.setUsername("testuser");
        currentState.setEmail("test@email.com");
        currentState.setPassword("password123");
        viewModel.setState(currentState);

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        SignUpState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("testuser", state.getUsername());
        assertEquals("test@email.com", state.getEmail());
        assertEquals("", state.getPassword()); // Should be cleared for security
        assertEquals(errorMessage, state.getErrorMessage());
        assertNull(state.getSuccessMessage());
        assertFalse(state.isSignUpInProgress());
    }

    @Test
    void testPrepareFailView_WithEmptyCurrentState() {
        // Arrange
        String errorMessage = "System error";
        SignUpState currentState = new SignUpState(); // Empty state
        viewModel.setState(currentState);

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        SignUpState state = viewModel.getState();
        assertNotNull(state);
        assertEquals("", state.getUsername()); // Default empty string from current state
        assertEquals("", state.getEmail()); // Default empty string from current state
        assertEquals("", state.getPassword());
        assertEquals(errorMessage, state.getErrorMessage());
        assertNull(state.getSuccessMessage());
        assertFalse(state.isSignUpInProgress());
    }
}
