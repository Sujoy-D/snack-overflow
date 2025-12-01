package use_case.checkout_recipe;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutRecipeInputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedUsername = "testuser";
        Ingredient ingredient = new Ingredient("Chicken", "500", "g");
        Tag tag = new Tag(1, "protein");
        Recipe expectedRecipe = new Recipe(
                123,
                List.of(ingredient),
                "Grilled Chicken",
                "Grill the chicken until cooked",
                "American",
                30,
                "Dinner",
                4,
                List.of(tag)
        );
        
        // When
        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(expectedRecipe, expectedUsername);
        
        // Then
        assertEquals(expectedRecipe, inputData.getRecipe());
        assertEquals(expectedUsername, inputData.getUsername());
        assertEquals(Integer.valueOf(123), inputData.getRecipeId());
        assertEquals("Grilled Chicken", inputData.getRecipe().getTitle());
    }
    
    @Test
    void getRecipeIdReturnsNullWhenRecipeIsNull() {
        // Given
        String username = "testuser";
        
        // When
        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(null, username);
        
        // Then
        assertNull(inputData.getRecipe());
        assertEquals(username, inputData.getUsername());
        assertNull(inputData.getRecipeId());
    }
    
    @Test
    void constructorHandlesNullUsername() {
        // Given
        Ingredient ingredient = new Ingredient("Rice", "200", "g");
        Recipe recipe = new Recipe(
                456,
                List.of(ingredient),
                "Fried Rice",
                "Fry the rice",
                "Asian",
                20,
                "Lunch",
                2,
                List.of()
        );
        
        // When
        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(recipe, null);
        
        // Then
        assertEquals(recipe, inputData.getRecipe());
        assertNull(inputData.getUsername());
        assertEquals(Integer.valueOf(456), inputData.getRecipeId());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(null, null);
        
        // Then
        assertNull(inputData.getRecipe());
        assertNull(inputData.getUsername());
        assertNull(inputData.getRecipeId());
    }
    
    @Test
    void getRecipeIdWorksWithDifferentRecipeIds() {
        // Given
        Ingredient ingredient = new Ingredient("Salt", "1", "pinch");
        Recipe recipe1 = new Recipe(1, List.of(ingredient), "Recipe 1", "Instructions", "Cuisine", 10, "Meal", 1, List.of());
        Recipe recipe2 = new Recipe(999, List.of(ingredient), "Recipe 2", "Instructions", "Cuisine", 15, "Meal", 2, List.of());
        
        // When
        CheckoutRecipeInputData inputData1 = new CheckoutRecipeInputData(recipe1, "user");
        CheckoutRecipeInputData inputData2 = new CheckoutRecipeInputData(recipe2, "user");
        
        // Then
        assertEquals(Integer.valueOf(1), inputData1.getRecipeId());
        assertEquals(Integer.valueOf(999), inputData2.getRecipeId());
    }
}
