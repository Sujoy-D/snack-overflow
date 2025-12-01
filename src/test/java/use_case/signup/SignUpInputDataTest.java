package use_case.signup;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for SignUpInputData data transfer object.
 * 
 * This class verifies that the SignUpInputData class correctly stores and retrieves
 * username, password, and email data for signup operations.
 * 
 * Edge cases tested:
 * - Normal string values with all fields
 * - Null values for any field  
 * - Empty strings
 * - Special characters and long strings
 * - Mixed null and valid values
 */
class SignUpInputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given - Valid input data with all fields
        String expectedUsername = "testuser";
        String expectedPassword = "testpassword";
        String expectedEmail = "test@example.com";
        
        // When
        SignUpInputData inputData = new SignUpInputData(expectedUsername, expectedPassword, expectedEmail);
        
        // Then - All values should be stored correctly
        assertEquals(expectedUsername, inputData.getUsername());
        assertEquals(expectedPassword, inputData.getPassword());
        assertEquals(expectedEmail, inputData.getEmail());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // Given - All null values
        
        // When
        SignUpInputData inputData = new SignUpInputData(null, null, null);
        
        // Then - Should handle null values gracefully
        assertNull(inputData.getUsername());
        assertNull(inputData.getPassword());
        assertNull(inputData.getEmail());
    }
    
    @Test
    void constructorHandlesEmptyValues() {
        // Given - Empty string values
        
        // When
        SignUpInputData inputData = new SignUpInputData("", "", "");
        
        // Then - Should preserve empty strings
        assertEquals("", inputData.getUsername());
        assertEquals("", inputData.getPassword());
        assertEquals("", inputData.getEmail());
    }
    
    @Test
    void constructorHandlesNullEmailWithValidOtherFields() {
        // Given - Null email but valid other fields
        String expectedUsername = "testuser";
        String expectedPassword = "testpassword";
        
        // When
        SignUpInputData inputData = new SignUpInputData(expectedUsername, expectedPassword, null);
        
        // Then - Should handle mixed null and valid values
        assertEquals(expectedUsername, inputData.getUsername());
        assertEquals(expectedPassword, inputData.getPassword());
        assertNull(inputData.getEmail());
    }
    
    @Test
    void constructorHandlesSpecialCharacters() {
        // Given - Various special characters in all fields
        String usernameWithSpecialChars = "user@domain.com!#$%^&*()";
        String passwordWithSpecialChars = "P@ssw0rd!@#$%^&*()_+=-[]{}|;':\"<>?,./";
        String emailWithSpecialChars = "test+tag@sub-domain.example.com";
        
        // When
        SignUpInputData inputData = new SignUpInputData(usernameWithSpecialChars, passwordWithSpecialChars, emailWithSpecialChars);
        
        // Then - Should handle special characters correctly
        assertEquals(usernameWithSpecialChars, inputData.getUsername());
        assertEquals(passwordWithSpecialChars, inputData.getPassword());
        assertEquals(emailWithSpecialChars, inputData.getEmail());
    }
    
    @Test
    void constructorHandlesLongStrings() {
        // Given - Very long strings for all fields
        String longUsername = "verylongusername".repeat(10) + "@domain.com";
        String longPassword = "verylongpassword".repeat(20);
        String longEmail = "verylongemailaddress".repeat(5) + "@domain.com";
        
        // When
        SignUpInputData inputData = new SignUpInputData(longUsername, longPassword, longEmail);
        
        // Then - Should handle long strings
        assertEquals(longUsername, inputData.getUsername());
        assertEquals(longPassword, inputData.getPassword());
        assertEquals(longEmail, inputData.getEmail());
    }
    
    @Test
    void constructorHandlesWhitespaceCharacters() {
        // Given - Strings with various whitespace characters
        String usernameWithWhitespace = " \t\n\r username \t\n\r ";
        String passwordWithWhitespace = " \t\n\r password \t\n\r ";
        String emailWithWhitespace = " \t\n\r email@domain.com \t\n\r ";
        
        // When
        SignUpInputData inputData = new SignUpInputData(usernameWithWhitespace, passwordWithWhitespace, emailWithWhitespace);
        
        // Then - Should preserve whitespace exactly
        assertEquals(usernameWithWhitespace, inputData.getUsername());
        assertEquals(passwordWithWhitespace, inputData.getPassword());
        assertEquals(emailWithWhitespace, inputData.getEmail());
    }
    
    @Test
    void constructorHandlesUnicodeCharacters() {
        // Given - Unicode characters in all fields
        String usernameWithUnicode = "用户名@domain.com";
        String passwordWithUnicode = "密码123";
        String emailWithUnicode = "测试@example.com";
        
        // When
        SignUpInputData inputData = new SignUpInputData(usernameWithUnicode, passwordWithUnicode, emailWithUnicode);
        
        // Then - Should handle Unicode characters
        assertEquals(usernameWithUnicode, inputData.getUsername());
        assertEquals(passwordWithUnicode, inputData.getPassword());
        assertEquals(emailWithUnicode, inputData.getEmail());
    }
    
    @Test
    void constructorHandlesMixedValidAndInvalidValues() {
        // Test various combinations of null, empty, and valid values
        
        // When - null username, valid password, empty email
        SignUpInputData inputData1 = new SignUpInputData(null, "validpassword", "");
        
        // Then
        assertNull(inputData1.getUsername());
        assertEquals("validpassword", inputData1.getPassword());
        assertEquals("", inputData1.getEmail());
        
        // When - valid username, null password, valid email
        SignUpInputData inputData2 = new SignUpInputData("validuser", null, "valid@email.com");
        
        // Then
        assertEquals("validuser", inputData2.getUsername());
        assertNull(inputData2.getPassword());
        assertEquals("valid@email.com", inputData2.getEmail());
        
        // When - empty username, empty password, null email
        SignUpInputData inputData3 = new SignUpInputData("", "", null);
        
        // Then
        assertEquals("", inputData3.getUsername());
        assertEquals("", inputData3.getPassword());
        assertNull(inputData3.getEmail());
    }
}
