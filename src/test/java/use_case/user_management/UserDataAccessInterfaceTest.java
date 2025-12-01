package use_case.user_management;

import entity.User;
import entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for UserDataAccessInterface.
 * Tests the contract and expected behavior of user data access operations
 * through a mock implementation, ensuring all interface methods work correctly.
 * 
 * @author Test Suite
 * @version 1.0
 */
@DisplayName("UserDataAccessInterface Tests")
public class UserDataAccessInterfaceTest {
    
    private MockUserDataAccess userDataAccess;
    
    @BeforeEach
    void setUp() {
        userDataAccess = new MockUserDataAccess();
    }
    
    @Test
    @DisplayName("Should save new user successfully")
    void testSaveUser() {
        // Act
        User user = userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Assert
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertTrue(userDataAccess.userExists("testUser"));
    }
    
    @Test
    @DisplayName("Should throw exception when saving duplicate username")
    void testSaveUserDuplicateUsername() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            userDataAccess.saveUser("testUser", "differentPassword", "different@example.com"));
        
        assertEquals("Username already exists", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should find user by username")
    void testFindUserByUsername() {
        // Arrange
        User savedUser = userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act
        User foundUser = userDataAccess.findUserByUsername("testUser");
        
        // Assert
        assertNotNull(foundUser);
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getEmail(), foundUser.getEmail());
    }
    
    @Test
    @DisplayName("Should return null for non-existent user")
    void testFindUserByUsernameNotFound() {
        // Act
        User user = userDataAccess.findUserByUsername("nonExistentUser");
        
        // Assert
        assertNull(user);
    }
    
    @Test
    @DisplayName("Should validate correct login credentials")
    void testValidateLoginCorrectCredentials() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act
        boolean isValid = userDataAccess.validateLogin("testUser", "password123");
        
        // Assert
        assertTrue(isValid);
    }
    
    @Test
    @DisplayName("Should reject incorrect login credentials")
    void testValidateLoginIncorrectCredentials() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act
        boolean isValid = userDataAccess.validateLogin("testUser", "wrongPassword");
        
        // Assert
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should reject login for non-existent user")
    void testValidateLoginNonExistentUser() {
        // Act
        boolean isValid = userDataAccess.validateLogin("nonExistentUser", "password123");
        
        // Assert
        assertFalse(isValid);
    }
    
    @Test
    @DisplayName("Should check if user exists")
    void testUserExists() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act & Assert
        assertTrue(userDataAccess.userExists("testUser"));
        assertFalse(userDataAccess.userExists("nonExistentUser"));
    }
    
    @Test
    @DisplayName("Should update last login timestamp")
    void testUpdateLastLogin() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act
        userDataAccess.updateLastLogin("testUser");
        
        // Assert
        assertTrue(userDataAccess.wasLastLoginUpdated("testUser"));
    }
    
    @Test
    @DisplayName("Should save recipe for user")
    void testSaveRecipeForUser() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        Recipe recipe = createMockRecipe(123, "Test Recipe");
        
        // Act
        boolean result = userDataAccess.saveRecipeForUser("testUser", recipe);
        
        // Assert
        assertTrue(result);
        assertTrue(userDataAccess.isRecipeSaved("testUser", 123));
    }
    
    @Test
    @DisplayName("Should return false when saving recipe for non-existent user")
    void testSaveRecipeForNonExistentUser() {
        // Arrange
        Recipe recipe = createMockRecipe(123, "Test Recipe");
        
        // Act
        boolean result = userDataAccess.saveRecipeForUser("nonExistentUser", recipe);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should check if recipe is saved by user")
    void testIsRecipeSaved() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        Recipe recipe = createMockRecipe(123, "Test Recipe");
        userDataAccess.saveRecipeForUser("testUser", recipe);
        
        // Act & Assert
        assertTrue(userDataAccess.isRecipeSaved("testUser", 123));
        assertFalse(userDataAccess.isRecipeSaved("testUser", 456));
    }
    
    @Test
    @DisplayName("Should return false for recipe saved check for non-existent user")
    void testIsRecipeSavedForNonExistentUser() {
        // Act
        boolean result = userDataAccess.isRecipeSaved("nonExistentUser", 123);
        
        // Assert
        assertFalse(result);
    }
    
    @Test
    @DisplayName("Should get saved recipes for user")
    void testGetSavedRecipesForUser() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        Recipe recipe1 = createMockRecipe(123, "Recipe 1");
        Recipe recipe2 = createMockRecipe(456, "Recipe 2");
        userDataAccess.saveRecipeForUser("testUser", recipe1);
        userDataAccess.saveRecipeForUser("testUser", recipe2);
        
        // Act
        List<Recipe> savedRecipes = userDataAccess.getSavedRecipesForUser("testUser");
        
        // Assert
        assertNotNull(savedRecipes);
        assertEquals(2, savedRecipes.size());
        assertTrue(savedRecipes.stream().anyMatch(r -> r.getRecipeId().equals(123)));
        assertTrue(savedRecipes.stream().anyMatch(r -> r.getRecipeId().equals(456)));
    }
    
    @Test
    @DisplayName("Should return empty list for user with no saved recipes")
    void testGetSavedRecipesForUserWithNoRecipes() {
        // Arrange
        userDataAccess.saveUser("testUser", "password123", "test@example.com");
        
        // Act
        List<Recipe> savedRecipes = userDataAccess.getSavedRecipesForUser("testUser");
        
        // Assert
        assertNotNull(savedRecipes);
        assertTrue(savedRecipes.isEmpty());
    }
    
    @Test
    @DisplayName("Should return empty list for non-existent user")
    void testGetSavedRecipesForNonExistentUser() {
        // Act
        List<Recipe> savedRecipes = userDataAccess.getSavedRecipesForUser("nonExistentUser");
        
        // Assert
        assertNotNull(savedRecipes);
        assertTrue(savedRecipes.isEmpty());
    }
    
    @Test
    @DisplayName("Should handle null inputs gracefully")
    void testHandleNullInputs() {
        // Test null username in various methods
        assertThrows(IllegalArgumentException.class, () ->
            userDataAccess.saveUser(null, "password", "email@test.com"));
        
        assertNull(userDataAccess.findUserByUsername(null));
        assertFalse(userDataAccess.validateLogin(null, "password"));
        assertFalse(userDataAccess.userExists(null));
        
        // Test null recipe
        assertFalse(userDataAccess.saveRecipeForUser("testUser", null));
    }
    
    @Test
    @DisplayName("Should handle empty string inputs")
    void testHandleEmptyStringInputs() {
        // Test empty username
        assertThrows(IllegalArgumentException.class, () ->
            userDataAccess.saveUser("", "password", "email@test.com"));
        
        assertNull(userDataAccess.findUserByUsername(""));
        assertFalse(userDataAccess.validateLogin("", "password"));
        assertFalse(userDataAccess.userExists(""));
    }
    
    @Test
    @DisplayName("Should handle special characters in usernames")
    void testHandleSpecialCharactersInUsernames() {
        // Test various special character usernames
        String[] specialUsernames = {"user@domain.com", "user.name", "user_123", "user-name"};
        
        for (String username : specialUsernames) {
            User user = userDataAccess.saveUser(username, "password123", "test@example.com");
            assertNotNull(user, "Should handle username: " + username);
            assertEquals(username, user.getUsername());
            assertTrue(userDataAccess.userExists(username));
        }
    }
    
    private Recipe createMockRecipe(Integer id, String name) {
        // Create a mock recipe for testing with at least one ingredient (required by Recipe constructor)
        List<entity.Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new entity.Ingredient("Test Ingredient", "1", "unit"));
        
        return new Recipe(id, ingredients, name, "Test instructions", 
                         "Test Cuisine", 30, "Dinner", 4, new ArrayList<>());
    }
    
    /**
     * Mock implementation of UserDataAccessInterface for testing.
     * Provides in-memory storage to simulate database operations.
     */
    private static class MockUserDataAccess implements UserDataAccessInterface {
        private final List<User> users = new ArrayList<>();
        private final List<String> lastLoginUpdates = new ArrayList<>();
        private final List<UserRecipePair> savedRecipes = new ArrayList<>();
        
        @Override
        public User saveUser(String username, String plainPassword, String email) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be null or empty");
            }
            
            if (userExists(username)) {
                throw new RuntimeException("Username already exists");
            }
            
            User user = new User(users.size() + 1, username, email, plainPassword);
            users.add(user);
            return user;
        }
        
        @Override
        public User findUserByUsername(String username) {
            if (username == null) return null;
            
            return users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        }
        
        @Override
        public boolean validateLogin(String username, String plainPassword) {
            if (username == null || plainPassword == null) return false;
            
            User user = findUserByUsername(username);
            return user != null && user.getPasswordHash().equals(plainPassword);
        }
        
        @Override
        public boolean userExists(String username) {
            if (username == null) return false;
            return findUserByUsername(username) != null;
        }
        
        @Override
        public void updateLastLogin(String username) {
            if (userExists(username)) {
                lastLoginUpdates.add(username);
            }
        }
        
        @Override
        public boolean saveRecipeForUser(String username, Recipe recipe) {
            if (recipe == null || !userExists(username)) {
                return false;
            }
            
            savedRecipes.add(new UserRecipePair(username, recipe));
            return true;
        }
        
        @Override
        public boolean isRecipeSaved(String username, Integer recipeId) {
            if (username == null || recipeId == null) return false;
            
            return savedRecipes.stream()
                    .anyMatch(pair -> pair.username.equals(username) && 
                             pair.recipe.getRecipeId().equals(recipeId));
        }
        
        @Override
        public List<Recipe> getSavedRecipesForUser(String username) {
            if (username == null) return new ArrayList<>();
            
            return savedRecipes.stream()
                    .filter(pair -> pair.username.equals(username))
                    .map(pair -> pair.recipe)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }
        
        public boolean wasLastLoginUpdated(String username) {
            return lastLoginUpdates.contains(username);
        }
        
        private static class UserRecipePair {
            final String username;
            final Recipe recipe;
            
            UserRecipePair(String username, Recipe recipe) {
                this.username = username;
                this.recipe = recipe;
            }
        }
    }
}
