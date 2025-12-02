package use_case.logout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogoutInputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        
        // When
        LogoutInputData inputData = new LogoutInputData(expectedUsername);
        
        // Then
        assertEquals(expectedUsername, inputData.getUsername());
    }
    
    @Test
    void constructorHandlesNullValue() {
        // When
        LogoutInputData inputData = new LogoutInputData(null);
        
        // Then
        assertNull(inputData.getUsername());
    }
    
    @Test
    void constructorHandlesEmptyValue() {
        // When
        LogoutInputData inputData = new LogoutInputData("");
        
        // Then
        assertEquals("", inputData.getUsername());
    }
}
