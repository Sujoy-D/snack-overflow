package use_case.login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for LoginInputData data transfer object.
 * 
 * This class verifies that the LoginInputData class correctly stores and retrieves
 * username and password data for login operations.
 * 
 * Edge cases tested:
 * - Normal string values
 * - Null values
 * - Empty strings
 * - Special characters and long strings
 */
class LoginInputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        String expectedPassword = "testpassword";
        
        // When
        LoginInputData inputData = new LoginInputData(expectedUsername, expectedPassword);
        
        // Then
        assertEquals(expectedUsername, inputData.getUsername());
        assertEquals(expectedPassword, inputData.getPassword());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        LoginInputData inputData = new LoginInputData(null, null);
        
        // Then
        assertNull(inputData.getUsername());
        assertNull(inputData.getPassword());
    }
    
    @Test
    void constructorHandlesEmptyValues() {
        // When
        LoginInputData inputData = new LoginInputData("", "");
        
        // Then
        assertEquals("", inputData.getUsername());
        assertEquals("", inputData.getPassword());
    }
    
    @Test
    void constructorHandlesSpecialCharacters() {
        // Given - Test with various special characters
        String usernameWithSpecialChars = "user@domain.com!#$%^&*()";
        String passwordWithSpecialChars = "P@ssw0rd!@#$%^&*()_+=-[]{}|;':\"<>?,./";
        
        // When
        LoginInputData inputData = new LoginInputData(usernameWithSpecialChars, passwordWithSpecialChars);
        
        // Then
        assertEquals(usernameWithSpecialChars, inputData.getUsername());
        assertEquals(passwordWithSpecialChars, inputData.getPassword());
    }
    
    @Test
    void constructorHandlesLongStrings() {
        // Given - Test with very long strings
        String longUsername = "verylongusername".repeat(10) + "@domain.com";
        String longPassword = "verylongpassword".repeat(20);
        
        // When
        LoginInputData inputData = new LoginInputData(longUsername, longPassword);
        
        // Then
        assertEquals(longUsername, inputData.getUsername());
        assertEquals(longPassword, inputData.getPassword());
    }
    
    @Test
    void constructorHandlesWhitespaceCharacters() {
        // Given - Test with various whitespace characters
        String usernameWithWhitespace = " \t\n\r username \t\n\r ";
        String passwordWithWhitespace = " \t\n\r password \t\n\r ";
        
        // When
        LoginInputData inputData = new LoginInputData(usernameWithWhitespace, passwordWithWhitespace);
        
        // Then
        assertEquals(usernameWithWhitespace, inputData.getUsername());
        assertEquals(passwordWithWhitespace, inputData.getPassword());
    }
    
    @Test
    void constructorHandlesUnicodeCharacters() {
        // Given - Test with Unicode characters
        String usernameWithUnicode = "用户名@domain.com";
        String passwordWithUnicode = "密码123";
        
        // When
        LoginInputData inputData = new LoginInputData(usernameWithUnicode, passwordWithUnicode);
        
        // Then
        assertEquals(usernameWithUnicode, inputData.getUsername());
        assertEquals(passwordWithUnicode, inputData.getPassword());
    }
    
    @Test
    void constructorHandlesMixedNullAndValidValues() {
        // Given - Test mixed null and valid values
        String validUsername = "testuser";
        
        // When - null password with valid username
        LoginInputData inputData1 = new LoginInputData(validUsername, null);
        
        // Then
        assertEquals(validUsername, inputData1.getUsername());
        assertNull(inputData1.getPassword());
        
        // When - null username with valid password
        LoginInputData inputData2 = new LoginInputData(null, "password");
        
        // Then
        assertNull(inputData2.getUsername());
        assertEquals("password", inputData2.getPassword());
    }
}
