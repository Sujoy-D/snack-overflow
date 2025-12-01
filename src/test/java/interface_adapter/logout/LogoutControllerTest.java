package interface_adapter.logout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

import static org.junit.jupiter.api.Assertions.*;

class LogoutControllerTest {

    private MockLogoutInteractor mockInteractor;
    private LogoutController controller;

    @BeforeEach
    void setUp() {
        mockInteractor = new MockLogoutInteractor();
        controller = new LogoutController(mockInteractor);
    }

    @Test
    void testExecute_ValidUsername() {
        // Arrange
        String username = "testuser";

        // Act
        controller.execute(username);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
    }

    @Test
    void testExecute_EmptyUsername() {
        // Arrange
        String username = "";

        // Act
        controller.execute(username);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
    }

    @Test
    void testExecute_NullUsername() {
        // Arrange
        String username = null;

        // Act
        controller.execute(username);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertNull(mockInteractor.inputData.getUsername());
    }

    @Test
    void testExecute_WhitespaceUsername() {
        // Arrange
        String username = "   ";

        // Act
        controller.execute(username);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
    }

    @Test
    void testExecute_LongUsername() {
        // Arrange
        String username = "verylongusernamethatmightcauseproblemsinvalidation";

        // Act
        controller.execute(username);

        // Assert
        assertTrue(mockInteractor.executeCalled);
        assertNotNull(mockInteractor.inputData);
        assertEquals(username, mockInteractor.inputData.getUsername());
    }

    // Mock Class
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
