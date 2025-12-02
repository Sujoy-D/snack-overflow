package interface_adapter.login;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private MockLoginInteractor mockInteractor;
    private LoginController controller;

    @BeforeEach
    void setUp() {
        mockInteractor = new MockLoginInteractor();
        controller = new LoginController(mockInteractor);
    }

    @Test
    void testExecute_ValidInputs() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        // Act
        controller.execute(username, password);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(password, mockInteractor.inputData.getPassword());
    }

    @Test
    void testExecute_EmptyStrings() {
        // Arrange
        String username = "";
        String password = "";

        // Act
        controller.execute(username, password);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(password, mockInteractor.inputData.getPassword());
    }

    @Test
    void testExecute_NullInputs() {
        // Arrange
        String username = null;
        String password = null;

        // Act
        controller.execute(username, password);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertNull(mockInteractor.inputData.getUsername());
        assertNull(mockInteractor.inputData.getPassword());
    }

    @Test
    void testExecute_WhitespaceInputs() {
        // Arrange
        String username = "   ";
        String password = "   ";

        // Act
        controller.execute(username, password);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
        assertEquals(password, mockInteractor.inputData.getPassword());
    }

    // Mock Class
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
