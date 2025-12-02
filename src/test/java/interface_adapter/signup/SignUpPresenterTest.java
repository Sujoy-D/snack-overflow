package interface_adapter.signup;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.signup.SignUpOutputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Mock NavigationController that tracks navigation calls for testing.
 */
class MockNavigationController extends NavigationController {
    private String lastView = null;
    private String lastUsername = null;
    private boolean executeCalled = false;

    public MockNavigationController(NavigationViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void execute(String viewName, String username) {
        this.lastView = viewName;
        this.lastUsername = username;
        this.executeCalled = true;
    }

    public String getLastView() { return lastView; }
    public String getLastUsername() { return lastUsername; }
    public boolean wasExecuteCalled() { return executeCalled; }
    public void reset() {
        lastView = null;
        lastUsername = null;
        executeCalled = false;
    }
}

/**
 * Test implementation for delayed navigation that executes immediately for testing.
 */
class TestSignUpDelayedNavigator implements SignUpDelayedNavigator {
    private boolean navigateAfterDelayCalled = false;
    private int lastDelayMs = -1;
    private Runnable lastNavigationAction = null;

    @Override
    public void navigateAfterDelay(int delayMs, Runnable navigationAction) {
        this.navigateAfterDelayCalled = true;
        this.lastDelayMs = delayMs;
        this.lastNavigationAction = navigationAction;
        // Execute immediately for testing
        navigationAction.run();
    }

    public boolean wasNavigateAfterDelayCalled() { return navigateAfterDelayCalled; }
    public int getLastDelayMs() { return lastDelayMs; }
    public void reset() {
        navigateAfterDelayCalled = false;
        lastDelayMs = -1;
        lastNavigationAction = null;
    }
}

/**
 * Test class for SignUpPresenter.
 * Tests the presenter layer for signup functionality, ensuring proper
 * view model updates and navigation handling.
 */
class SignUpPresenterTest {

    private SignUpViewModel viewModel;
    private MockNavigationController mockNavigationController;
    private TestSignUpDelayedNavigator testDelayedNavigator;
    private SignUpPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new SignUpViewModel();
        NavigationViewModel navViewModel = new NavigationViewModel();
        mockNavigationController = new MockNavigationController(navViewModel);
        testDelayedNavigator = new TestSignUpDelayedNavigator();
        presenter = new SignUpPresenter(viewModel, mockNavigationController, testDelayedNavigator);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        SignUpOutputData outputData = new SignUpOutputData("testuser", true, "Account created successfully!");

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert - State should be updated correctly
        SignUpState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getErrorMessage());
        assertEquals("Account created successfully!", state.getSuccessMessage());
        assertFalse(state.isSignUpInProgress());
        
        // Assert - Delayed navigation should have been triggered
        assertTrue(testDelayedNavigator.wasNavigateAfterDelayCalled());
        assertEquals(2000, testDelayedNavigator.getLastDelayMs());
        
        // Assert - Navigation should have been executed (by the test navigator)
        assertTrue(mockNavigationController.wasExecuteCalled());
        assertEquals("login", mockNavigationController.getLastView());
        assertNull(mockNavigationController.getLastUsername());
    }

    @Test
    void testPrepareSuccessView_DefaultConstructor() {
        // Test with the default constructor that uses the real timer
        SignUpPresenter defaultPresenter = new SignUpPresenter(viewModel, mockNavigationController);
        SignUpOutputData outputData = new SignUpOutputData("testuser", true, "Account created successfully!");

        // Reset mock navigation controller
        mockNavigationController.reset();

        // Act
        defaultPresenter.prepareSuccessView(outputData);

        // Assert - State should be updated correctly
        SignUpState state = viewModel.getState();
        assertNotNull(state);
        assertNull(state.getErrorMessage());
        assertEquals("Account created successfully!", state.getSuccessMessage());
        assertFalse(state.isSignUpInProgress());
        
        // Navigation should not have been called immediately with default constructor
        assertFalse(mockNavigationController.wasExecuteCalled());
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
