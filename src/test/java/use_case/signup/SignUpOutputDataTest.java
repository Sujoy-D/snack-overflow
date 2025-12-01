package use_case.signup;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SignUpOutputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        boolean expectedSuccess = true;
        String expectedMessage = "Account created successfully!";
        
        // When
        SignUpOutputData outputData = new SignUpOutputData(expectedUsername, expectedSuccess, expectedMessage);
        
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
        String expectedMessage = "Sign up failed";
        
        // When
        SignUpOutputData outputData = new SignUpOutputData(expectedUsername, expectedSuccess, expectedMessage);
        
        // Then
        assertEquals(expectedUsername, outputData.getUsername());
        assertEquals(expectedSuccess, outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        SignUpOutputData outputData = new SignUpOutputData(null, false, null);
        
        // Then
        assertNull(outputData.getUsername());
        assertFalse(outputData.isSuccess());
        assertNull(outputData.getMessage());
    }
}
