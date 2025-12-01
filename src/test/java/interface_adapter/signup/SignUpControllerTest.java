package interface_adapter.signup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.signup.SignUpInputBoundary;
import use_case.signup.SignUpInputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SignUpController.
 * Tests the controller layer for signup functionality, ensuring proper
 * handling of user input and delegation to the use case interactor.
 */
class SignUpControllerTest {

    private MockSignUpInteractor mockInteractor;
    private SignUpController controller;

    @BeforeEach
    void setUp() {
        mockInteractor = new MockSignUpInteractor();
        controller = new SignUpController(mockInteractor);
    }

    /**
     * Test execute method with valid inputs.
     * Verifies that valid user data is properly passed to the interactor.
     */
    @Test
    void testExecute_ValidInputs() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = "test@email.com";

        // Act
        controller.execute(username, password, email);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(password, mockInteractor.inputData.getPassword());
        assertEquals(email, mockInteractor.inputData.getEmail());
    }

    /**
     * Test execute method with null email.
     * Verifies that null email is handled gracefully by the controller.
     */
    @Test
    void testExecute_NullEmail() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = null;

        // Act
        controller.execute(username, password, email);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(password, mockInteractor.inputData.getPassword());
        assertNull(mockInteractor.inputData.getEmail());
    }

    /**
     * Test execute method with empty string inputs.
     * Verifies that empty strings are properly handled by the controller.
     */
    @Test
    void testExecute_EmptyStrings() {
        // Arrange
        String username = "";
        String password = "";
        String email = "";

        // Act
        controller.execute(username, password, email);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(password, mockInteractor.inputData.getPassword());
        assertEquals(email, mockInteractor.inputData.getEmail());
    }

    /**
     * Test execute method with null username and password.
     * Verifies that null inputs are properly handled by the controller.
     */
    @Test
    void testExecute_NullInputs() {
        // Arrange
        String username = null;
        String password = null;
        String email = null;

        // Act
        controller.execute(username, password, email);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertNull(mockInteractor.inputData.getUsername());
        assertNull(mockInteractor.inputData.getPassword());
        assertNull(mockInteractor.inputData.getEmail());
    }

    // Mock Classes
    
    /**
     * Mock implementation of SignUpInputBoundary for testing.
     * Records method calls and input data for verification.
     */
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
