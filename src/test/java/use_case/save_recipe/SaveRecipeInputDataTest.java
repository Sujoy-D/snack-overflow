package use_case.save_recipe;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveRecipeInputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        Ingredient ingredient = new Ingredient("Tomato", "2", "pieces");
        Tag tag = new Tag(1, "healthy");
        Recipe expectedRecipe = new Recipe(
                1,
                List.of(ingredient),
                "Tomato Salad",
                "Chop tomatoes and serve",
                "Mediterranean",
                15,
                "Lunch",
                2,
                List.of(tag)
        );
        
        // When
        SaveRecipeInputData inputData = new SaveRecipeInputData(expectedUsername, expectedRecipe);
        
        // Then
        assertEquals(expectedUsername, inputData.getUsername());
        assertEquals(expectedRecipe, inputData.getRecipe());
        assertEquals("Tomato Salad", inputData.getRecipe().getTitle());
    }
    
    @Test
    void constructorHandlesNullUsername() {
        // Given
        Ingredient ingredient = new Ingredient("Bread", "2", "slices");
        Recipe recipe = new Recipe(
                2,
                List.of(ingredient),
                "Toast",
                "Toast the bread",
                "American",
                5,
                "Breakfast",
                1,
                List.of()
        );
        
        // When
        SaveRecipeInputData inputData = new SaveRecipeInputData(null, recipe);
        
        // Then
        assertNull(inputData.getUsername());
        assertEquals(recipe, inputData.getRecipe());
    }
    
    @Test
    void constructorHandlesNullRecipe() {
        // Given
        String username = "testuser";
        
        // When
        SaveRecipeInputData inputData = new SaveRecipeInputData(username, null);
        
        // Then
        assertEquals(username, inputData.getUsername());
        assertNull(inputData.getRecipe());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        SaveRecipeInputData inputData = new SaveRecipeInputData(null, null);
        
        // Then
        assertNull(inputData.getUsername());
        assertNull(inputData.getRecipe());
    }
}
