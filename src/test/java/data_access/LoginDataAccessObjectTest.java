package data_access;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LoginDataAccessObject.
 * Tests data access layer functionality for user login operations,
 * including login validation, user data retrieval, and error handling.
 */
class LoginDataAccessObjectTest {

    private MockUserDataAccessObject mockUserDAO;
    private LoginDataAccessObject loginDAO;

    @BeforeEach
    void setUp() {
        mockUserDAO = new MockUserDataAccessObject();
        loginDAO = new LoginDataAccessObject(mockUserDAO);
    }

    @Test
    void testValidateLogin_Success() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        mockUserDAO.setValidCredentials(username, password);

        // Act
        boolean result = loginDAO.validateLogin(username, password);

        // Assert
        assertTrue(result);
        assertTrue(mockUserDAO.validateUserCalled);
        assertEquals(username, mockUserDAO.validatedUsername);
        assertEquals(password, mockUserDAO.validatedPassword);
    }

    @Test
    void testValidateLogin_InvalidCredentials() {
        // Arrange
        String username = "testuser";
        String password = "wrongpassword";
        mockUserDAO.setValidCredentials(username, "correctpassword");

        // Act
        boolean result = loginDAO.validateLogin(username, password);

        // Assert
        assertFalse(result);
        assertTrue(mockUserDAO.validateUserCalled);
        assertEquals(username, mockUserDAO.validatedUsername);
        assertEquals(password, mockUserDAO.validatedPassword);
    }

    @Test
    void testValidateLogin_UserNotExists() {
        // Arrange
        String username = "nonexistentuser";
        String password = "password123";
        mockUserDAO.setUserNotExists();

        // Act
        boolean result = loginDAO.validateLogin(username, password);

        // Assert
        assertFalse(result);
        assertTrue(mockUserDAO.validateUserCalled);
        assertEquals(username, mockUserDAO.validatedUsername);
        assertEquals(password, mockUserDAO.validatedPassword);
    }

    @Test
    void testValidateLogin_Exception() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        mockUserDAO.setShouldThrowException(true);

        // Act
        boolean result = loginDAO.validateLogin(username, password);

        // Assert
        assertFalse(result); // Should return false when exception occurs
        assertTrue(mockUserDAO.validateUserCalled);
    }

    @Test
    void testUpdateLastLogin_Success() {
        // Arrange
        String username = "testuser";

        // Act
        loginDAO.updateLastLogin(username);

        // Assert
        assertTrue(mockUserDAO.updateLastLoginCalled);
        assertEquals(username, mockUserDAO.lastLoginUsername);
    }

    @Test
    void testUpdateLastLogin_Exception() {
        // Arrange
        String username = "testuser";
        mockUserDAO.setShouldThrowExceptionOnUpdate(true);

        // Act
        // Should not throw exception, just handle it silently
        assertDoesNotThrow(() -> {
            loginDAO.updateLastLogin(username);
        });

        // Assert
        assertTrue(mockUserDAO.updateLastLoginCalled);
    }

    // Mock Class
    static class MockUserDataAccessObject extends UserDataAccessObject {
        boolean validateUserCalled = false;
        boolean updateLastLoginCalled = false;
        String validatedUsername;
        String validatedPassword;
        String lastLoginUsername;
        String correctUsername;
        String correctPassword;
        boolean userExists = true;
        boolean shouldThrowException = false;
        boolean shouldThrowExceptionOnUpdate = false;

        @Override
        public boolean validateLogin(String username, String password) {
            this.validateUserCalled = true;
            this.validatedUsername = username;
            this.validatedPassword = password;

            if (shouldThrowException) {
                throw new RuntimeException("Database connection error");
            }
            
            if (!userExists) {
                return false;
            }

            return username.equals(correctUsername) && password.equals(correctPassword);
        }

        @Override
        public void updateLastLogin(String username) {
            this.updateLastLoginCalled = true;
            this.lastLoginUsername = username;

            if (shouldThrowExceptionOnUpdate) {
                throw new RuntimeException("Update failed");
            }
        }

        public void setValidCredentials(String username, String password) {
            this.correctUsername = username;
            this.correctPassword = password;
            this.userExists = true;
        }

        public void setUserNotExists() {
            this.userExists = false;
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        public void setShouldThrowExceptionOnUpdate(boolean shouldThrow) {
            this.shouldThrowExceptionOnUpdate = shouldThrow;
        }
    }
}
