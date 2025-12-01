package entity;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testConstructor_ValidInputs() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));
        List<Tag> tags = List.of(new Tag(1, "italian"));

        // Act
        Recipe recipe = new Recipe(1, ingredients, "Pasta", "Boil water", "Italian", 30, "Dinner", 4, tags);

        // Assert
        assertEquals(Integer.valueOf(1), recipe.getRecipeId());
        assertEquals("Pasta", recipe.getTitle());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals("Boil water", recipe.getInstructions());
        assertEquals("Italian", recipe.getCuisine());
        assertEquals(Integer.valueOf(30), recipe.getCookingTime());
        assertEquals("Dinner", recipe.getMealType());
        assertEquals(Integer.valueOf(4), recipe.getServingSize());
        assertEquals(tags, recipe.getTags());
    }

    @Test
    void testConstructor_EmptyTitle() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(1, ingredients, "", "Boil water", "Italian", 30, "Dinner", 4, null);
        });

        assertEquals("Recipe title cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_NullTitle() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(1, ingredients, null, "Boil water", "Italian", 30, "Dinner", 4, null);
        });

        assertEquals("Recipe title cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_EmptyIngredients() {
        // Arrange
        List<Ingredient> emptyIngredients = List.of();

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(1, emptyIngredients, "Pasta", "Boil water", "Italian", 30, "Dinner", 4, null);
        });

        assertEquals("Ingredients list cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_NullIngredients() {
        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new Recipe(1, null, "Pasta", "Boil water", "Italian", 30, "Dinner", 4, null);
        });

        assertEquals("Ingredients list cannot be empty", thrown.getMessage());
    }

    @Test
    void testConstructor_NullTags() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));

        // Act
        Recipe recipe = new Recipe(1, ingredients, "Pasta", "Boil water", "Italian", 30, "Dinner", 4, null);

        // Assert
        assertNotNull(recipe.getTags());
        assertTrue(recipe.getTags().isEmpty());
    }

    @Test
    void testConstructor_NullRecipeId() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));

        // Act
        Recipe recipe = new Recipe(null, ingredients, "Pasta", "Boil water", "Italian", 30, "Dinner", 4, null);

        // Assert
        assertNull(recipe.getRecipeId());
        assertEquals("Pasta", recipe.getTitle());
    }

    @Test
    void testConstructor_NullOptionalFields() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));

        // Act
        Recipe recipe = new Recipe(1, ingredients, "Pasta", null, null, null, null, null, null);

        // Assert
        assertEquals(Integer.valueOf(1), recipe.getRecipeId());
        assertEquals("Pasta", recipe.getTitle());
        assertEquals(ingredients, recipe.getIngredients());
        assertNull(recipe.getInstructions());
        assertNull(recipe.getCuisine());
        assertNull(recipe.getCookingTime());
        assertNull(recipe.getMealType());
        assertNull(recipe.getServingSize());
        assertNotNull(recipe.getTags());
        assertTrue(recipe.getTags().isEmpty());
    }

    @Test
    void testToString() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));
        Recipe recipe = new Recipe(1, ingredients, "Pasta", "Boil water", "Italian", 30, "Dinner", 4, null);

        // Act
        String result = recipe.toString();

        // Assert
        assertTrue(result.contains("Pasta"));
        assertTrue(result.contains("30 min"));
        assertTrue(result.contains("Italian"));
    }

    @Test
    void testToString_NullCookingTime() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));
        Recipe recipe = new Recipe(1, ingredients, "Pasta", "Boil water", "Italian", null, "Dinner", 4, null);

        // Act
        String result = recipe.toString();

        // Assert
        assertTrue(result.contains("Pasta"));
        assertTrue(result.contains("null min"));
        assertTrue(result.contains("Italian"));
    }

    @Test
    void testToString_NullCuisine() {
        // Arrange
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "2", "pcs"));
        Recipe recipe = new Recipe(1, ingredients, "Pasta", "Boil water", null, 30, "Dinner", 4, null);

        // Act
        String result = recipe.toString();

        // Assert
        assertTrue(result.contains("Pasta"));
        assertTrue(result.contains("30 min"));
        assertTrue(result.contains("null"));
    }
}
