package use_case.add_recipe;

import entity.Ingredient;
import entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddRecipeInputDataTest {
    
    @Test
    void constructorAndGettersWorkWithAllFields() {
        // Given
        Integer expectedRecipeId = 123;
        String expectedTitle = "Spaghetti Carbonara";
        Ingredient ingredient1 = new Ingredient("Pasta", "200", "g");
        Ingredient ingredient2 = new Ingredient("Eggs", "2", "pieces");
        List<Ingredient> expectedIngredients = List.of(ingredient1, ingredient2);
        String expectedInstructions = "1. Boil pasta. 2. Mix eggs. 3. Combine.";
        String expectedCuisine = "Italian";
        Integer expectedCookingTime = 25;
        String expectedMealType = "Dinner";
        Integer expectedServingSize = 4;
        Tag tag1 = new Tag(1, "easy");
        Tag tag2 = new Tag(2, "pasta");
        List<Tag> expectedTags = List.of(tag1, tag2);
        
        // When
        AddRecipeInputData inputData = new AddRecipeInputData(
                expectedRecipeId, expectedTitle, expectedIngredients, expectedInstructions,
                expectedCuisine, expectedCookingTime, expectedMealType, expectedServingSize, expectedTags
        );
        
        // Then
        assertEquals(expectedRecipeId, inputData.getRecipeId());
        assertEquals(expectedTitle, inputData.getTitle());
        assertEquals(expectedIngredients, inputData.getIngredients());
        assertEquals(expectedInstructions, inputData.getInstructions());
        assertEquals(expectedCuisine, inputData.getCuisine());
        assertEquals(expectedCookingTime, inputData.getCookingTime());
        assertEquals(expectedMealType, inputData.getMealType());
        assertEquals(expectedServingSize, inputData.getServingSize());
        assertEquals(expectedTags, inputData.getTags());
    }
    
    @Test
    void constructorHandlesMinimalData() {
        // Given
        String expectedTitle = "Simple Recipe";
        List<Ingredient> expectedIngredients = List.of(new Ingredient("Salt", "1", "pinch"));
        String expectedInstructions = "Add salt";
        
        // When
        AddRecipeInputData inputData = new AddRecipeInputData(
                null, expectedTitle, expectedIngredients, expectedInstructions,
                null, null, null, null, null
        );
        
        // Then
        assertNull(inputData.getRecipeId());
        assertEquals(expectedTitle, inputData.getTitle());
        assertEquals(expectedIngredients, inputData.getIngredients());
        assertEquals(expectedInstructions, inputData.getInstructions());
        assertNull(inputData.getCuisine());
        assertNull(inputData.getCookingTime());
        assertNull(inputData.getMealType());
        assertNull(inputData.getServingSize());
        assertNull(inputData.getTags());
    }
    
    @Test
    void constructorHandlesEmptyCollections() {
        // Given
        List<Ingredient> emptyIngredients = List.of();
        List<Tag> emptyTags = List.of();
        
        // When
        AddRecipeInputData inputData = new AddRecipeInputData(
                1, "Empty Recipe", emptyIngredients, "No instructions",
                "Unknown", 0, "Snack", 1, emptyTags
        );
        
        // Then
        assertTrue(inputData.getIngredients().isEmpty());
        assertTrue(inputData.getTags().isEmpty());
        assertEquals("Empty Recipe", inputData.getTitle());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        AddRecipeInputData inputData = new AddRecipeInputData(
                null, null, null, null, null, null, null, null, null
        );
        
        // Then
        assertNull(inputData.getRecipeId());
        assertNull(inputData.getTitle());
        assertNull(inputData.getIngredients());
        assertNull(inputData.getInstructions());
        assertNull(inputData.getCuisine());
        assertNull(inputData.getCookingTime());
        assertNull(inputData.getMealType());
        assertNull(inputData.getServingSize());
        assertNull(inputData.getTags());
    }
}
