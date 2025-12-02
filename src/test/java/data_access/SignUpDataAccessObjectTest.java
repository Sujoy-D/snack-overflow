package data_access;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import entity.User;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SignUpDataAccessObject.
 * Tests data access layer functionality for user signup operations,
 * including user creation, existence checks, and error handling.
 */
class SignUpDataAccessObjectTest {

    private MockUserDataAccessObject mockUserDAO;
    private SignUpDataAccessObject signUpDAO;

    @BeforeEach
    void setUp() {
        mockUserDAO = new MockUserDataAccessObject();
        signUpDAO = new SignUpDataAccessObject(mockUserDAO);
    }

    @Test
    void testSaveUser_Success() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = "test@email.com";

        // Act
        signUpDAO.saveUser(username, password, email);

        // Assert
        assertTrue(mockUserDAO.saveUserCalled);
        assertEquals(username, mockUserDAO.savedUsername);
        assertEquals(password, mockUserDAO.savedPassword);
        assertEquals(email, mockUserDAO.savedEmail);
    }

    @Test
    void testSaveUser_RuntimeException() {
        // Arrange
        mockUserDAO.setShouldThrowRuntimeException(true);
        String username = "testuser";
        String password = "password123";
        String email = "test@email.com";

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            signUpDAO.saveUser(username, password, email);
        });

        assertEquals("Test runtime exception", thrown.getMessage());
    }

    @Test
    void testSaveUser_CheckedException() {
        // Arrange
        mockUserDAO.setShouldThrowCheckedException(true);
        String username = "testuser";
        String password = "password123";
        String email = "test@email.com";

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            signUpDAO.saveUser(username, password, email);
        });

        // The mock will throw a RuntimeException wrapping an Exception
        assertTrue(thrown.getCause() instanceof Exception);
    }

    @Test
    void testUserExists_True() {
        // Arrange
        mockUserDAO.setUserExists(true);
        String username = "existinguser";

        // Act
        boolean result = signUpDAO.userExists(username);

        // Assert
        assertTrue(result);
        assertTrue(mockUserDAO.userExistsCalled);
        assertEquals(username, mockUserDAO.checkedUsername);
    }

    @Test
    void testUserExists_False() {
        // Arrange
        mockUserDAO.setUserExists(false);
        String username = "newuser";

        // Act
        boolean result = signUpDAO.userExists(username);

        // Assert
        assertFalse(result);
        assertTrue(mockUserDAO.userExistsCalled);
        assertEquals(username, mockUserDAO.checkedUsername);
    }

    @Test
    void testUserExists_Exception() {
        // Arrange
        mockUserDAO.setShouldThrowExceptionOnUserExists(true);
        String username = "testuser";

        // Act
        boolean result = signUpDAO.userExists(username);

        // Assert
        assertFalse(result); // Should return false when exception occurs
        assertTrue(mockUserDAO.userExistsCalled);
        assertEquals(username, mockUserDAO.checkedUsername);
    }

    // Mock Class
    static class MockUserDataAccessObject extends UserDataAccessObject {
        boolean saveUserCalled = false;
        boolean userExistsCalled = false;
        String savedUsername;
        String savedPassword;
        String savedEmail;
        String checkedUsername;
        boolean userExistsResult = false;
        boolean shouldThrowRuntimeException = false;
        boolean shouldThrowCheckedException = false;
        boolean shouldThrowExceptionOnUserExists = false;

        @Override
        public User saveUser(String username, String password, String email) {
            this.saveUserCalled = true;
            this.savedUsername = username;
            this.savedPassword = password;
            this.savedEmail = email;

            if (shouldThrowRuntimeException) {
                throw new RuntimeException("Test runtime exception");
            }
            if (shouldThrowCheckedException) {
                // Simulate a checked exception that would be caught and wrapped by SignUpDataAccessObject
                throw new RuntimeException(new Exception("Test checked exception"));
            }
            
            // Return a mock user
            return new User(1, username, email, password);
        }

        @Override
        public boolean userExists(String username) {
            this.userExistsCalled = true;
            this.checkedUsername = username;

            if (shouldThrowExceptionOnUserExists) {
                throw new RuntimeException("Database connection error");
            }
            
            return userExistsResult;
        }

        public void setUserExists(boolean exists) {
            this.userExistsResult = exists;
        }

        public void setShouldThrowRuntimeException(boolean shouldThrow) {
            this.shouldThrowRuntimeException = shouldThrow;
        }

        public void setShouldThrowCheckedException(boolean shouldThrow) {
            this.shouldThrowCheckedException = shouldThrow;
        }

        public void setShouldThrowExceptionOnUserExists(boolean shouldThrow) {
            this.shouldThrowExceptionOnUserExists = shouldThrow;
        }
    }
}
