package use_case.logout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogoutOutputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        boolean expectedSuccess = true;
        String expectedMessage = "Logged out successfully";
        
        // When
        LogoutOutputData outputData = new LogoutOutputData(expectedUsername, expectedSuccess, expectedMessage);
        
        // Then
        assertEquals(expectedUsername, outputData.getUsername());
        assertEquals(expectedSuccess, outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesFailureCase() {
        // Given
        String expectedUsername = "testuser";
        boolean expectedSuccess = false;
        String expectedMessage = "Logout failed";
        
        // When
        LogoutOutputData outputData = new LogoutOutputData(expectedUsername, expectedSuccess, expectedMessage);
        
        // Then
        assertEquals(expectedUsername, outputData.getUsername());
        assertEquals(expectedSuccess, outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        LogoutOutputData outputData = new LogoutOutputData(null, false, null);
        
        // Then
        assertNull(outputData.getUsername());
        assertFalse(outputData.isSuccess());
        assertNull(outputData.getMessage());
    }
}
