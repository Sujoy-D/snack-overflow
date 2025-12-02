package use_case.logout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LogoutInteractor.
 * Tests the business logic for user logout functionality,
 * including successful logout and error handling scenarios.
 */
class LogoutInteractorTest {
    
    @Test
    void executeSuccessfulLogout() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        LogoutInputData inputData = new LogoutInputData("testuser");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isSuccessCalled());
        assertFalse(presenter.isFailureCalled());
        assertEquals("testuser", presenter.getOutputData().getUsername());
        assertTrue(presenter.getOutputData().isSuccess());
        assertEquals("Logged out successfully", presenter.getOutputData().getMessage());
        
        // Verify data access methods were called
        assertTrue(dataAccess.isClearUserSessionCalled());
        assertTrue(dataAccess.isUpdateLastLogoutCalled());
        assertEquals("testuser", dataAccess.getClearedSessionUser());
        assertEquals("testuser", dataAccess.getLastLogoutUser());
    }
    
    @Test
    void executeFailsWithEmptyUsername() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        LogoutInputData inputData = new LogoutInputData("");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("No user is currently logged in", presenter.getErrorMessage());
        
        // Data access should not be called for validation errors
        assertFalse(dataAccess.isClearUserSessionCalled());
        assertFalse(dataAccess.isUpdateLastLogoutCalled());
    }
    
    @Test
    void executeFailsWithNullUsername() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        LogoutInputData inputData = new LogoutInputData(null);
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("No user is currently logged in", presenter.getErrorMessage());
        
        // Data access should not be called for validation errors
        assertFalse(dataAccess.isClearUserSessionCalled());
        assertFalse(dataAccess.isUpdateLastLogoutCalled());
    }
    
    @Test
    void executeFailsWithWhitespaceUsername() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        LogoutInputData inputData = new LogoutInputData("   ");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("No user is currently logged in", presenter.getErrorMessage());
        
        // Data access should not be called for validation errors
        assertFalse(dataAccess.isClearUserSessionCalled());
        assertFalse(dataAccess.isUpdateLastLogoutCalled());
    }
    
    @Test
    void executeHandlesDataAccessExceptionDuringClearSession() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        dataAccess.setThrowExceptionOnClearSession(true);
        LogoutInputData inputData = new LogoutInputData("testuser");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Logout failed due to system error", presenter.getErrorMessage());
        
        // Verify clear session was attempted
        assertTrue(dataAccess.isClearUserSessionCalled());
    }
    
    @Test
    void executeHandlesDataAccessExceptionDuringUpdateLogout() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        dataAccess.setThrowExceptionOnUpdateLogout(true);
        LogoutInputData inputData = new LogoutInputData("testuser");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Logout failed due to system error", presenter.getErrorMessage());
        
        // Verify both methods were attempted
        assertTrue(dataAccess.isClearUserSessionCalled());
        assertTrue(dataAccess.isUpdateLastLogoutCalled());
    }
    
    @Test
    void executeHandlesGenericException() {
        // Given
        MockLogoutDataAccess dataAccess = new MockLogoutDataAccess();
        MockLogoutPresenter presenter = new MockLogoutPresenter();
        LogoutInteractor interactor = new LogoutInteractor(dataAccess, presenter);
        
        dataAccess.setThrowExceptionOnClearSession(true);
        dataAccess.setThrowExceptionOnUpdateLogout(true);
        LogoutInputData inputData = new LogoutInputData("testuser");
        
        // When
        interactor.execute(inputData);
        
        // Then
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        assertEquals("Logout failed due to system error", presenter.getErrorMessage());
    }

    /**
     * Mock implementation of LogoutDataAccessInterface for testing
     */
    private static class MockLogoutDataAccess implements LogoutDataAccessInterface {
        private boolean clearUserSessionCalled = false;
        private boolean updateLastLogoutCalled = false;
        private boolean throwExceptionOnClearSession = false;
        private boolean throwExceptionOnUpdateLogout = false;
        private String clearedSessionUser;
        private String lastLogoutUser;
        
        public void setThrowExceptionOnClearSession(boolean throwException) {
            this.throwExceptionOnClearSession = throwException;
        }
        
        public void setThrowExceptionOnUpdateLogout(boolean throwException) {
            this.throwExceptionOnUpdateLogout = throwException;
        }
        
        @Override
        public void clearUserSession(String username) {
            clearUserSessionCalled = true;
            clearedSessionUser = username;
            if (throwExceptionOnClearSession) {
                throw new RuntimeException("Session clear error");
            }
        }
        
        @Override
        public void updateLastLogout(String username) {
            updateLastLogoutCalled = true;
            lastLogoutUser = username;
            if (throwExceptionOnUpdateLogout) {
                throw new RuntimeException("Database update error");
            }
        }
        
        // Getters for verification
        public boolean isClearUserSessionCalled() { return clearUserSessionCalled; }
        public boolean isUpdateLastLogoutCalled() { return updateLastLogoutCalled; }
        public String getClearedSessionUser() { return clearedSessionUser; }
        public String getLastLogoutUser() { return lastLogoutUser; }
    }

    /**
     * Mock implementation of LogoutOutputBoundary for testing
     */
    private static class MockLogoutPresenter implements LogoutOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private LogoutOutputData outputData;
        private String errorMessage;
        
        @Override
        public void prepareSuccessView(LogoutOutputData outputData) {
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
        public LogoutOutputData getOutputData() { return outputData; }
        public String getErrorMessage() { return errorMessage; }
    }
}
