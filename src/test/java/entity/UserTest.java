package entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for User entity.
 * Tests all functionality of the User class including constructors,
 * getters, setters, recipe management, and string representation.
 */
class UserTest {

    @Test
    void testConstructor_ValidInputs() {
        // Arrange & Act
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");

        // Assert
        assertEquals(Integer.valueOf(1), user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("hashedpassword", user.getPasswordHash());
        assertNotNull(user.getSavedRecipes());
        assertTrue(user.getSavedRecipes().isEmpty());
        assertNotNull(user.getCustomTags());
        assertTrue(user.getCustomTags().isEmpty());
        assertNotNull(user.getMealPlan());
        assertTrue(user.getMealPlan().isEmpty());
    }

    @Test
    void testConstructor_NullEmail() {
        // Arrange & Act
        User user = new User(1, "testuser", null, "hashedpassword");

        // Assert
        assertEquals(Integer.valueOf(1), user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertNull(user.getEmail());
        assertEquals("hashedpassword", user.getPasswordHash());
    }

    @Test
    void testConstructor_EmptyUsername() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, "", "test@email.com", "hashedpassword");
        });

        assertEquals("Username cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_NullUsername() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, null, "test@email.com", "hashedpassword");
        });

        assertEquals("Username cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_WhitespaceOnlyUsername() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, "   ", "test@email.com", "hashedpassword");
        });

        assertEquals("Username cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_EmptyPassword() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, "testuser", "test@email.com", "");
        });

        assertEquals("Password cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_NullPassword() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, "testuser", "test@email.com", null);
        });

        assertEquals("Password cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_WhitespaceOnlyPassword() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new User(1, "testuser", "test@email.com", "   ");
        });

        assertEquals("Password cannot be empty", thrown.getMessage());
    }

    @Test
    void testAddRecipe() {
        // Arrange
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");
        List<Ingredient> ingredients = Arrays.asList(new Ingredient("Tomato", "2", "pcs"));
        Recipe recipe = new Recipe(1, ingredients, "Test Recipe", "Instructions", "Italian", 30, "Dinner", 1, null);

        // Act
        user.addRecipe(recipe);

        // Assert
        assertEquals(1, user.getSavedRecipes().size());
        assertTrue(user.getSavedRecipes().contains(recipe));
    }

    @Test
    void testAddRecipe_DuplicateRecipe() {
        // Arrange
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");
        List<Ingredient> ingredients = Arrays.asList(new Ingredient("Tomato", "2", "pcs"));
        Recipe recipe = new Recipe(1, ingredients, "Test Recipe", "Instructions", "Italian", 30, "Dinner", 1, null);

        // Act
        user.addRecipe(recipe);
        user.addRecipe(recipe); // Add same recipe again

        // Assert
        assertEquals(1, user.getSavedRecipes().size()); // Should still be 1
        assertTrue(user.getSavedRecipes().contains(recipe));
    }

    @Test
    void testAddCustomTag() {
        // Arrange
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");
        Tag tag = new Tag(1, "custom-tag");

        // Act
        user.addCustomTag(tag);

        // Assert
        assertEquals(1, user.getCustomTags().size());
        assertTrue(user.getCustomTags().contains(tag));
    }

    @Test
    void testAddMultipleCustomTags() {
        // Arrange
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");
        Tag tag1 = new Tag(1, "tag1");
        Tag tag2 = new Tag(2, "tag2");

        // Act
        user.addCustomTag(tag1);
        user.addCustomTag(tag2);

        // Assert
        assertEquals(2, user.getCustomTags().size());
        assertTrue(user.getCustomTags().contains(tag1));
        assertTrue(user.getCustomTags().contains(tag2));
    }

    @Test
    void testToString() {
        // Arrange
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");
        List<Ingredient> ingredients = Arrays.asList(new Ingredient("Tomato", "2", "pcs"));
        Recipe recipe = new Recipe(1, ingredients, "Test Recipe", "Instructions", "Italian", 30, "Dinner", 1, null);
        Tag tag = new Tag(1, "test-tag");
        user.addRecipe(recipe);
        user.addCustomTag(tag);

        // Act
        String result = user.toString();

        // Assert
        assertTrue(result.contains("username='testuser'"));
        assertTrue(result.contains("savedRecipes=1"));
        assertTrue(result.contains("customTags=1"));
    }

    @Test
    void testGetMealPlan_InitiallyEmpty() {
        // Arrange & Act
        User user = new User(1, "testuser", "test@email.com", "hashedpassword");

        // Assert
        assertNotNull(user.getMealPlan());
        assertTrue(user.getMealPlan().isEmpty());
    }
}
