package use_case.login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for LoginInteractor.
 * 
 * This test class verifies the business logic for user login functionality,
 * including successful authentication, validation errors, and system error handling.
 * 
 * Test Categories:
 * - Successful login scenarios
 * - Input validation failures
 * - Authentication failures
 * - System error handling
 * 
 * All tests use mock implementations to isolate the unit under test.
 */
class LoginInteractorTest {
    
    @Test
    void executeSuccessfulLogin() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        dataAccess.setValidCredentials("testuser", "password123");
        LoginInputData inputData = new LoginInputData("testuser", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isSuccessCalled());
        assertFalse(presenter.isFailureCalled());
        assertEquals("testuser", presenter.getOutputData().getUsername());
        assertTrue(presenter.getOutputData().isSuccess());
        assertEquals("Login successful", presenter.getOutputData().getMessage());
        
        // Verify data access methods were called
        assertTrue(dataAccess.isValidateLoginCalled());
        assertTrue(dataAccess.isUpdateLastLoginCalled());
        assertEquals("testuser", dataAccess.getLastUpdateUser());
    }
    
    @Test
    void executeFailsWithInvalidCredentials() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        dataAccess.setValidCredentials("testuser", "wrongpassword");
        LoginInputData inputData = new LoginInputData("testuser", "differentpassword");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Invalid username or password", presenter.getErrorMessage());
        
        // Verify data access methods were called correctly
        assertTrue(dataAccess.isValidateLoginCalled());
        assertFalse(dataAccess.isUpdateLastLoginCalled());
    }
    
    @Test
    void executeFailsWithEmptyUsername() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Username cannot be empty", presenter.getErrorMessage());
        
        // Data access should not be called for validation errors
        assertFalse(dataAccess.isValidateLoginCalled());
        assertFalse(dataAccess.isUpdateLastLoginCalled());
    }
    
    @Test
    void executeFailsWithNullUsername() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData(null, "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Username cannot be empty", presenter.getErrorMessage());
    }
    
    @Test
    void executeFailsWithWhitespaceUsername() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("   ", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Username cannot be empty", presenter.getErrorMessage());
    }
    
    @Test
    void executeFailsWithEmptyPassword() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("testuser", "");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Password cannot be empty", presenter.getErrorMessage());
        
        // Data access should not be called for validation errors
        assertFalse(dataAccess.isValidateLoginCalled());
        assertFalse(dataAccess.isUpdateLastLoginCalled());
    }
    
    @Test
    void executeFailsWithNullPassword() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("testuser", null);
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Password cannot be empty", presenter.getErrorMessage());
    }
    
    @Test
    void executeFailsWithWhitespacePassword() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("testuser", "   ");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Password cannot be empty", presenter.getErrorMessage());
    }
    
    @Test
    void executeHandlesDataAccessException() {
        // Given
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        dataAccess.setThrowException(true);
        LoginInputData inputData = new LoginInputData("testuser", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Login failed due to system error", presenter.getErrorMessage());
    }

    // Additional Edge Case Tests
    
    @Test
    void executeSuccessfulLoginWithSpecialCharacters() {
        // Given - Test usernames with special characters
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        String usernameWithSpecialChars = "user@domain.com";
        String passwordWithSpecialChars = "P@ssw0rd!#$";
        dataAccess.setValidCredentials(usernameWithSpecialChars, passwordWithSpecialChars);
        LoginInputData inputData = new LoginInputData(usernameWithSpecialChars, passwordWithSpecialChars);
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isSuccessCalled());
        assertFalse(presenter.isFailureCalled());
        assertEquals(usernameWithSpecialChars, presenter.getOutputData().getUsername());
        assertTrue(dataAccess.isUpdateLastLoginCalled());
    }
    
    @Test
    void executeSuccessfulLoginWithLongCredentials() {
        // Given - Test with very long but valid credentials
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        String longUsername = "a".repeat(100) + "@example.com";
        String longPassword = "SuperLongPassword" + "1".repeat(100);
        dataAccess.setValidCredentials(longUsername, longPassword);
        LoginInputData inputData = new LoginInputData(longUsername, longPassword);
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isSuccessCalled());
        assertEquals(longUsername, presenter.getOutputData().getUsername());
    }
    
    @Test
    void executeFailsWithUsernameContainingOnlySpaces() {
        // Given - Test username that contains only spaces (not just empty)
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("     ", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertEquals("Username cannot be empty", presenter.getErrorMessage());
        assertFalse(dataAccess.isValidateLoginCalled());
    }
    
    @Test
    void executeFailsWithPasswordContainingOnlySpaces() {
        // Given - Test password that contains only spaces
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("testuser", "     ");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertEquals("Password cannot be empty", presenter.getErrorMessage());
        assertFalse(dataAccess.isValidateLoginCalled());
    }
    
    @Test
    void executeFailsWithTabsAndNewlines() {
        // Given - Test with whitespace characters other than spaces
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        LoginInputData inputData = new LoginInputData("\t\n\r", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertEquals("Username cannot be empty", presenter.getErrorMessage());
    }
    
    @Test
    void executeFailsWhenDataAccessReturnsNull() {
        // Given - Test when data access throws specific exceptions
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        dataAccess.setThrowNullPointerException(true);
        LoginInputData inputData = new LoginInputData("testuser", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertEquals("Login failed due to system error", presenter.getErrorMessage());
    }
    
    @Test
    void executeHandlesRuntimeExceptionDuringValidation() {
        // Given - Test runtime exception during validation
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        dataAccess.setThrowRuntimeException(true);
        LoginInputData inputData = new LoginInputData("testuser", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertEquals("Login failed due to system error", presenter.getErrorMessage());
    }
    
    @Test
    void executeHandlesExceptionDuringLastLoginUpdate() {
        // Given - Test exception during last login update
        MockLoginDataAccess dataAccess = new MockLoginDataAccess();
        MockLoginPresenter presenter = new MockLoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        
        dataAccess.setValidCredentials("testuser", "password123");
        dataAccess.setThrowExceptionOnUpdate(true);
        LoginInputData inputData = new LoginInputData("testuser", "password123");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertEquals("Login failed due to system error", presenter.getErrorMessage());
        assertTrue(dataAccess.isValidateLoginCalled());
    }

    /**
     * Mock implementation of LoginDataAccessInterface for testing
     */
    private static class MockLoginDataAccess implements LoginDataAccessInterface {
        private boolean validateLoginCalled = false;
        private boolean updateLastLoginCalled = false;
        private String validUsername;
        private String validPassword;
        private String lastUpdateUser;
        private boolean throwException = false;
        private boolean throwNullPointerException = false;
        private boolean throwRuntimeException = false;
        
        public void setValidCredentials(String username, String password) {
            this.validUsername = username;
            this.validPassword = password;
        }
        
        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }
        
        public void setThrowNullPointerException(boolean throwNullPointerException) {
            this.throwNullPointerException = throwNullPointerException;
        }
        
        public void setThrowRuntimeException(boolean throwRuntimeException) {
            this.throwRuntimeException = throwRuntimeException;
        }
        
        private boolean throwExceptionOnUpdate = false;
        
        public void setThrowExceptionOnUpdate(boolean throwExceptionOnUpdate) {
            this.throwExceptionOnUpdate = throwExceptionOnUpdate;
        }
        
        @Override
        public boolean validateLogin(String username, String password) {
            validateLoginCalled = true;
            if (throwException || throwRuntimeException) {
                throw new RuntimeException("Database connection error");
            }
            if (throwNullPointerException) {
                throw new NullPointerException("Null value encountered");
            }
            return validUsername != null && validUsername.equals(username) && 
                   validPassword != null && validPassword.equals(password);
        }
        
        @Override
        public void updateLastLogin(String username) {
            updateLastLoginCalled = true;
            lastUpdateUser = username;
            if (throwException || throwExceptionOnUpdate) {
                throw new RuntimeException("Database update error");
            }
        }
        
        // Getters for verification
        public boolean isValidateLoginCalled() { return validateLoginCalled; }
        public boolean isUpdateLastLoginCalled() { return updateLastLoginCalled; }
        public String getLastUpdateUser() { return lastUpdateUser; }
    }

    /**
     * Mock implementation of LoginOutputBoundary for testing
     */
    private static class MockLoginPresenter implements LoginOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private LoginOutputData outputData;
        private String errorMessage;
        
        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            successCalled = true;
            this.outputData = outputData;
        }
        
        @Override
        public void prepareFailView(String errorMessage) {
            failureCalled = true;
            this.errorMessage = errorMessage;
        }
        
        // Getters for verification
        public boolean isSuccessCalled() { return successCalled; }
        public boolean isFailureCalled() { return failureCalled; }
        public LoginOutputData getOutputData() { return outputData; }
        public String getErrorMessage() { return errorMessage; }
    }
}
