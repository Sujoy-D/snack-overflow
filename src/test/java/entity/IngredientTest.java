package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Ingredient entity.
 * Tests all functionality of the Ingredient class including constructors,
 * getters, validation, toString, equals, and hashCode methods.
 */
class IngredientTest {

    @Test
    void testConstructor_ValidInputs() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Tomato", "2", "pcs");

        // Assert
        assertEquals("Tomato", ingredient.getName());
        assertEquals("2", ingredient.getQuantity());
        assertEquals("pcs", ingredient.getUnit());
    }

    @Test
    void testConstructor_EmptyName() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient("", "2", "pcs");
        });

        assertEquals("Name required", thrown.getMessage());
    }

    @Test
    void testConstructor_NullName() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Ingredient(null, "2", "pcs");
        });

        assertEquals("Name required", thrown.getMessage());
    }

    @Test
    void testConstructor_ValidWithNullQuantity() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Tomato", null, "pcs");

        // Assert
        assertEquals("Tomato", ingredient.getName());
        assertNull(ingredient.getQuantity());
        assertEquals("pcs", ingredient.getUnit());
    }

    @Test
    void testConstructor_ValidWithNullUnit() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Tomato", "2", null);

        // Assert
        assertEquals("Tomato", ingredient.getName());
        assertEquals("2", ingredient.getQuantity());
        assertNull(ingredient.getUnit());
    }

    @Test
    void testConstructor_ValidWithEmptyQuantity() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Tomato", "", "pcs");

        // Assert
        assertEquals("Tomato", ingredient.getName());
        assertEquals("", ingredient.getQuantity());
        assertEquals("pcs", ingredient.getUnit());
    }

    @Test
    void testConstructor_ValidWithEmptyUnit() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Tomato", "2", "");

        // Assert
        assertEquals("Tomato", ingredient.getName());
        assertEquals("2", ingredient.getQuantity());
        assertEquals("", ingredient.getUnit());
    }

    @Test
    void testConstructor_ValidWithSpecialCharacters() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Cherry-Tomatoes", "1.5", "kg");

        // Assert
        assertEquals("Cherry-Tomatoes", ingredient.getName());
        assertEquals("1.5", ingredient.getQuantity());
        assertEquals("kg", ingredient.getUnit());
    }

    @Test
    void testConstructor_ValidWithNumbers() {
        // Arrange & Act
        Ingredient ingredient = new Ingredient("Ingredient123", "10.25", "ml");

        // Assert
        assertEquals("Ingredient123", ingredient.getName());
        assertEquals("10.25", ingredient.getQuantity());
        assertEquals("ml", ingredient.getUnit());
    }

    @Test
    void testToString() {
        // Arrange
        Ingredient ingredient = new Ingredient("Tomato", "2", "pcs");

        // Act
        String result = ingredient.toString();

        // Assert
        assertTrue(result.contains("Tomato"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("pcs"));
    }

    @Test
    void testEquals() {
        // Arrange
        Ingredient ingredient1 = new Ingredient("Tomato", "2", "pcs");
        Ingredient ingredient2 = new Ingredient("Tomato", "2", "pcs");
        Ingredient ingredient3 = new Ingredient("Onion", "1", "pcs");

        // Act & Assert
        assertEquals(ingredient1, ingredient2);
        assertNotEquals(ingredient1, ingredient3);
        assertNotEquals(ingredient1, null);
        assertNotEquals(ingredient1, "not an ingredient");
    }

    @Test
    void testHashCode() {
        // Arrange
        Ingredient ingredient1 = new Ingredient("Tomato", "2", "pcs");
        Ingredient ingredient2 = new Ingredient("Tomato", "2", "pcs");

        // Act & Assert
        assertEquals(ingredient1.hashCode(), ingredient2.hashCode());
    }
}
