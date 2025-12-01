package data_access;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class LogoutDataAccessObjectTest {

    private UserDataAccessObject userDAO;
    private LogoutDataAccessObject logoutDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDataAccessObject();
        logoutDAO = new LogoutDataAccessObject(userDAO);
    }

    @Test
    void testClearUserSession_Success() {
        // Arrange
        String username = "testuser";

        // Act & Assert - Should not throw an exception
        assertDoesNotThrow(() -> {
            logoutDAO.clearUserSession(username);
        });
    }

    @Test
    void testClearUserSession_NullUsername() {
        // Arrange
        String username = null;

        // Act & Assert - Should not throw an exception
        assertDoesNotThrow(() -> {
            logoutDAO.clearUserSession(username);
        });
    }

    @Test
    void testClearUserSession_EmptyUsername() {
        // Arrange
        String username = "";

        // Act & Assert - Should not throw an exception
        assertDoesNotThrow(() -> {
            logoutDAO.clearUserSession(username);
        });
    }

    @Test
    void testUpdateLastLogout_Success() {
        // Arrange
        String username = "testuser";

        // Act & Assert - Should not throw an exception
        assertDoesNotThrow(() -> {
            logoutDAO.updateLastLogout(username);
        });
    }

    @Test
    void testUpdateLastLogout_NullUsername() {
        // Arrange
        String username = null;

        // Act & Assert - Should not throw an exception
        assertDoesNotThrow(() -> {
            logoutDAO.updateLastLogout(username);
        });
    }

    @Test
    void testUpdateLastLogout_EmptyUsername() {
        // Arrange
        String username = "";

        // Act & Assert - Should not throw an exception
        assertDoesNotThrow(() -> {
            logoutDAO.updateLastLogout(username);
        });
    }

    @Test
    void testLogoutDataAccessObjectInstantiation() {
        // Arrange & Act & Assert
        assertNotNull(logoutDAO);
        assertDoesNotThrow(() -> {
            new LogoutDataAccessObject(userDAO);
        });
    }
}
