package use_case.signup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SignUpInteractor.
 * Tests the business logic for user sign up functionality including
 * success scenarios, failure scenarios, and edge cases.
 */
class SignUpInteractorTest {

    private MockSignUpDataAccessInterface mockDataAccess;
    private MockSignUpPresenter mockPresenter;
    private SignUpInteractor interactor;

    @BeforeEach
    void setUp() {
        mockDataAccess = new MockSignUpDataAccessInterface();
        mockPresenter = new MockSignUpPresenter();
        interactor = new SignUpInteractor(mockDataAccess, mockPresenter);
    }

    /**
     * Test successful user signup execution.
     * Verifies that when valid input data is provided, the signup process
     * completes successfully and the presenter is called with success data.
     */
    @Test
    void testExecute_Success() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("testuser", "password123", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.successViewCalled);
        assertFalse(mockPresenter.failViewCalled);
        assertNotNull(mockPresenter.outputData);
        assertEquals("testuser", mockPresenter.outputData.getUsername());
        assertTrue(mockPresenter.outputData.isSuccess());
        assertEquals("Account created successfully!", mockPresenter.outputData.getMessage());
        assertTrue(mockDataAccess.saveUserCalled);
        assertEquals("testuser", mockDataAccess.savedUsername);
        assertEquals("password123", mockDataAccess.savedPassword);
        assertEquals("test@email.com", mockDataAccess.savedEmail);
    }

    /**
     * Test signup execution with empty username.
     * Verifies that an empty username results in validation failure.
     */
    @Test
    void testExecute_EmptyUsername() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("", "password123", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Username cannot be empty", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution with null username.
     * Verifies that a null username results in validation failure.
     */
    @Test
    void testExecute_NullUsername() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData(null, "password123", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Username cannot be empty", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution with empty password.
     * Verifies that an empty password results in validation failure.
     */
    @Test
    void testExecute_EmptyPassword() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("testuser", "", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Password cannot be empty", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution with null password.
     * Verifies that a null password results in validation failure.
     */
    @Test
    void testExecute_NullPassword() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("testuser", null, "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Password cannot be empty", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution with username that's too short.
     * Verifies that usernames below minimum length requirement fail validation.
     */
    @Test
    void testExecute_UsernameTooShort() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("ab", "password123", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Username must be at least 3 characters long", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution with password that's too short.
     * Verifies that passwords below minimum length requirement fail validation.
     */
    @Test
    void testExecute_PasswordTooShort() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("testuser", "12345", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Password must be at least 6 characters long", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution when user already exists.
     * Verifies that attempting to create a duplicate user results in failure.
     */
    @Test
    void testExecute_UserAlreadyExists() {
        // Arrange
        mockDataAccess.setUserExists(true);
        SignUpInputData inputData = new SignUpInputData("existinguser", "password123", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Username already exists", mockPresenter.errorMessage);
        assertFalse(mockDataAccess.saveUserCalled);
    }

    /**
     * Test signup execution when data access layer throws exception.
     * Verifies that data access exceptions are properly handled and communicated.
     */
    @Test
    void testExecute_DataAccessException() {
        // Arrange
        mockDataAccess.setShouldThrowException(true);
        SignUpInputData inputData = new SignUpInputData("testuser", "password123", "test@email.com");
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.failViewCalled);
        assertFalse(mockPresenter.successViewCalled);
        assertEquals("Sign up failed due to system error", mockPresenter.errorMessage);
    }

    /**
     * Test signup execution with null email.
     * Verifies that null email is handled gracefully and defaults to empty string.
     */
    @Test
    void testExecute_NullEmailDefaultsToEmpty() {
        // Arrange
        SignUpInputData inputData = new SignUpInputData("testuser", "password123", null);
        
        // Act
        interactor.execute(inputData);
        
        // Assert
        assertTrue(mockPresenter.successViewCalled);
        assertFalse(mockPresenter.failViewCalled);
        assertTrue(mockDataAccess.saveUserCalled);
        assertEquals("", mockDataAccess.savedEmail);
    }

    // Mock Classes
    
    /**
     * Mock implementation of SignUpDataAccessInterface for testing.
     * Allows control over return values and tracking of method calls.
     */
    static class MockSignUpDataAccessInterface implements SignUpDataAccessInterface {
        boolean saveUserCalled = false;
        String savedUsername;
        String savedPassword;
        String savedEmail;
        boolean userExists = false;
        boolean shouldThrowException = false;

        @Override
        public void saveUser(String username, String password, String email) {
            if (shouldThrowException) {
                throw new RuntimeException("Database error");
            }
            this.saveUserCalled = true;
            this.savedUsername = username;
            this.savedPassword = password;
            this.savedEmail = email;
        }

        @Override
        public boolean userExists(String username) {
            if (shouldThrowException) {
                throw new RuntimeException("Database error");
            }
            return userExists;
        }

        public void setUserExists(boolean exists) {
            this.userExists = exists;
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }
    }

    /**
     * Mock implementation of SignUpOutputBoundary for testing.
     * Tracks method calls and captures output data for verification.
     */
    static class MockSignUpPresenter implements SignUpOutputBoundary {
        boolean successViewCalled = false;
        boolean failViewCalled = false;
        SignUpOutputData outputData;
        String errorMessage;

        @Override
        public void prepareSuccessView(SignUpOutputData outputData) {
            this.successViewCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failViewCalled = true;
            this.errorMessage = errorMessage;
        }
    }
}
