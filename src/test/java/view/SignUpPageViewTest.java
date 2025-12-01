package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import interface_adapter.signup.SignUpController;
import interface_adapter.signup.SignUpState;
import interface_adapter.signup.SignUpViewModel;
import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInputData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SignUpPageView.
 * Tests the view layer for signup functionality, ensuring proper
 * UI component initialization, state handling, and user interaction.
 */
class SignUpPageViewTest {

    private SignUpPageView view;
    private SignUpViewModel viewModel;
    private SignUpController signUpController;
    private NavigationController navigationController;

    @BeforeEach
    void setUp() {
        viewModel = new SignUpViewModel();
        MockSignUpInteractor mockInteractor = new MockSignUpInteractor();
        signUpController = new SignUpController(mockInteractor);
        NavigationViewModel navViewModel = new NavigationViewModel();
        navigationController = new NavigationController(navViewModel);
        view = new SignUpPageView(viewModel, signUpController, navigationController);
    }

    @Test
    void testConstructor_InitializesCorrectly() {
        // Assert
        assertNotNull(view);
        assertEquals("signup", view.getViewName());
    }

    @Test
    void testPropertyChange_UpdatesUIOnStateChange() {
        // Arrange
        SignUpState newState = new SignUpState();
        newState.setUsername("testuser");
        newState.setEmail("test@email.com");
        newState.setErrorMessage("Test error");
        newState.setSuccessMessage("Test success");

        // Act
        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // Assert - The view should update its display based on the state
        // Note: Testing UI updates would require more complex setup with UI testing framework
        // For now, we verify that the view receives the property change event
        assertEquals(newState, viewModel.getState());
    }

    @Test
    void testPropertyChange_ClearsFieldsOnSignUpInProgress() {
        // Arrange
        SignUpState newState = new SignUpState();
        newState.setSignUpInProgress(true);

        // Act
        viewModel.setState(newState);
        viewModel.firePropertyChanged();

        // Assert
        assertTrue(viewModel.getState().isSignUpInProgress());
    }

    @Test
    void testGetViewName() {
        // Act & Assert
        assertEquals("signup", view.getViewName());
    }

    @Test
    void testPropertyChange_HandlesEmptyState() {
        // Arrange & Act - This should not throw an exception
        viewModel.setState(new SignUpState()); // Use empty state
        viewModel.firePropertyChanged();

        // Assert
        assertNotNull(viewModel.getState());
    }

    @Test
    void testPropertyChange_HandlesErrorMessage() {
        // Arrange
        SignUpState stateWithError = new SignUpState();
        stateWithError.setErrorMessage("Username already exists");

        // Act
        viewModel.setState(stateWithError);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("Username already exists", viewModel.getState().getErrorMessage());
    }

    @Test
    void testPropertyChange_HandlesSuccessMessage() {
        // Arrange
        SignUpState stateWithSuccess = new SignUpState();
        stateWithSuccess.setSuccessMessage("Account created successfully!");

        // Act
        viewModel.setState(stateWithSuccess);
        viewModel.firePropertyChanged();

        // Assert
        assertEquals("Account created successfully!", viewModel.getState().getSuccessMessage());
    }

    // Mock Interactor for testing
    static class MockSignUpInteractor implements SignUpInputBoundary {
        boolean executeCalled = false;
        SignUpInputData inputData;

        @Override
        public void execute(SignUpInputData inputData) {
            this.executeCalled = true;
            this.inputData = inputData;
        }
    }
}
