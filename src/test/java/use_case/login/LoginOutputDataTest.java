package use_case.login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for LoginOutputData data transfer object.
 * 
 * This class verifies that the LoginOutputData class correctly stores and retrieves
 * the results of login operations, including username, success status, and messages.
 * 
 * Test scenarios:
 * - Successful login responses
 * - Failed login responses  
 * - Edge cases with null values
 * - Various message types and special characters
 */
class LoginOutputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        boolean expectedSuccess = true;
        String expectedMessage = "Login successful";
        
        // When
        LoginOutputData outputData = new LoginOutputData(expectedUsername, expectedSuccess, expectedMessage);
        
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
        String expectedMessage = "Login failed";
        
        // When
        LoginOutputData outputData = new LoginOutputData(expectedUsername, expectedSuccess, expectedMessage);
        
        // Then
        assertEquals(expectedUsername, outputData.getUsername());
        assertEquals(expectedSuccess, outputData.isSuccess());
        assertEquals(expectedMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        LoginOutputData outputData = new LoginOutputData(null, false, null);
        
        // Then
        assertNull(outputData.getUsername());
        assertFalse(outputData.isSuccess());
        assertNull(outputData.getMessage());
    }
    
    @Test
    void constructorHandlesEmptyStrings() {
        // Given - Test with empty string values
        String emptyUsername = "";
        String emptyMessage = "";
        
        // When
        LoginOutputData outputData = new LoginOutputData(emptyUsername, true, emptyMessage);
        
        // Then
        assertEquals(emptyUsername, outputData.getUsername());
        assertTrue(outputData.isSuccess());
        assertEquals(emptyMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesLongMessages() {
        // Given - Test with very long messages
        String username = "testuser";
        String longMessage = "This is a very long error message that might occur in real scenarios ".repeat(10);
        
        // When
        LoginOutputData outputData = new LoginOutputData(username, false, longMessage);
        
        // Then
        assertEquals(username, outputData.getUsername());
        assertFalse(outputData.isSuccess());
        assertEquals(longMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesSpecialCharactersInMessages() {
        // Given - Test with special characters in messages
        String username = "user@domain.com";
        String messageWithSpecialChars = "Error: Invalid credentials! @#$%^&*()_+{}|:<>?[]\\;'\"";
        
        // When
        LoginOutputData outputData = new LoginOutputData(username, false, messageWithSpecialChars);
        
        // Then
        assertEquals(username, outputData.getUsername());
        assertFalse(outputData.isSuccess());
        assertEquals(messageWithSpecialChars, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesUnicodeCharacters() {
        // Given - Test with Unicode characters
        String unicodeUsername = "用户名";
        String unicodeMessage = "登录成功";
        
        // When
        LoginOutputData outputData = new LoginOutputData(unicodeUsername, true, unicodeMessage);
        
        // Then
        assertEquals(unicodeUsername, outputData.getUsername());
        assertTrue(outputData.isSuccess());
        assertEquals(unicodeMessage, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesWhitespaceInMessages() {
        // Given - Test with whitespace characters in messages
        String username = "testuser";
        String messageWithWhitespace = " \t\n\r Login successful \t\n\r ";
        
        // When
        LoginOutputData outputData = new LoginOutputData(username, true, messageWithWhitespace);
        
        // Then
        assertEquals(username, outputData.getUsername());
        assertTrue(outputData.isSuccess());
        assertEquals(messageWithWhitespace, outputData.getMessage());
    }
    
    @Test
    void constructorHandlesMixedNullAndValidValues() {
        // Given - Test with mixed null and valid values
        String validUsername = "testuser";
        String validMessage = "Login successful";
        
        // When - null username with valid message and success
        LoginOutputData outputData1 = new LoginOutputData(null, true, validMessage);
        
        // Then
        assertNull(outputData1.getUsername());
        assertTrue(outputData1.isSuccess());
        assertEquals(validMessage, outputData1.getMessage());
        
        // When - valid username with null message and failure
        LoginOutputData outputData2 = new LoginOutputData(validUsername, false, null);
        
        // Then
        assertEquals(validUsername, outputData2.getUsername());
        assertFalse(outputData2.isSuccess());
        assertNull(outputData2.getMessage());
    }
}
