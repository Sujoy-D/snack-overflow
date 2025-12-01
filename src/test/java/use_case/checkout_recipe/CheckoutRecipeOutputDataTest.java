package use_case.checkout_recipe;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutRecipeOutputDataTest {
    
    @Test
    void constructorAndGettersWorkWithCompleteData() {
        // Given
        Map<String, String> expectedRecipeInfo = new HashMap<>();
        expectedRecipeInfo.put("title", "Pasta Carbonara");
        expectedRecipeInfo.put("cuisine", "Italian");
        expectedRecipeInfo.put("cookingTime", "25");
        expectedRecipeInfo.put("servingSize", "4");
        expectedRecipeInfo.put("mealType", "Dinner");
        expectedRecipeInfo.put("instructions", "1. Boil pasta. 2. Mix eggs and cheese. 3. Combine.");
        
        List<ArrayList<String>> expectedIngredients = List.of(
                new ArrayList<>(Arrays.asList("Pasta", "400", "g")),
                new ArrayList<>(Arrays.asList("Eggs", "3", "pieces")),
                new ArrayList<>(Arrays.asList("Parmesan Cheese", "100", "g"))
        );
        
        List<String> expectedTags = List.of("italian", "pasta", "eggs");
        
        // When
        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(
                expectedRecipeInfo, expectedIngredients, expectedTags
        );
        
        // Then
        assertEquals(expectedRecipeInfo, outputData.getRecipeInfo());
        assertEquals(expectedIngredients, outputData.getRecipeIngredients());
        assertEquals(expectedTags, outputData.getRecipeTags());
        assertEquals("Pasta Carbonara", outputData.getRecipeInfo().get("title"));
        assertEquals(3, outputData.getRecipeIngredients().size());
        assertEquals(3, outputData.getRecipeTags().size());
    }
    
    @Test
    void constructorHandlesEmptyCollections() {
        // Given
        Map<String, String> recipeInfo = new HashMap<>();
        List<ArrayList<String>> emptyIngredients = new ArrayList<>();
        List<String> emptyTags = new ArrayList<>();
        
        // When
        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(
                recipeInfo, emptyIngredients, emptyTags
        );
        
        // Then
        assertEquals(recipeInfo, outputData.getRecipeInfo());
        assertTrue(outputData.getRecipeInfo().isEmpty());
        assertTrue(outputData.getRecipeIngredients().isEmpty());
        assertTrue(outputData.getRecipeTags().isEmpty());
    }
    
    @Test
    void constructorHandlesSingleIngredientAndTag() {
        // Given
        Map<String, String> recipeInfo = Map.of("title", "Simple Toast");
        List<ArrayList<String>> singleIngredient = List.of(
                new ArrayList<>(Arrays.asList("Bread", "2", "slices"))
        );
        List<String> singleTag = List.of("simple");
        
        // When
        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(
                recipeInfo, singleIngredient, singleTag
        );
        
        // Then
        assertEquals(recipeInfo, outputData.getRecipeInfo());
        assertEquals(1, outputData.getRecipeIngredients().size());
        assertEquals(1, outputData.getRecipeTags().size());
        assertEquals("Simple Toast", outputData.getRecipeInfo().get("title"));
        assertEquals("Bread", outputData.getRecipeIngredients().get(0).get(0));
        assertEquals("simple", outputData.getRecipeTags().get(0));
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(null, null, null);
        
        // Then
        assertNull(outputData.getRecipeInfo());
        assertNull(outputData.getRecipeIngredients());
        assertNull(outputData.getRecipeTags());
    }
    
    @Test
    void constructorHandlesPartialNullValues() {
        // Given
        Map<String, String> recipeInfo = Map.of("title", "Test Recipe");
        
        // When
        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(recipeInfo, null, null);
        
        // Then
        assertEquals(recipeInfo, outputData.getRecipeInfo());
        assertNull(outputData.getRecipeIngredients());
        assertNull(outputData.getRecipeTags());
        assertEquals("Test Recipe", outputData.getRecipeInfo().get("title"));
    }
    
    @Test
    void constructorHandlesComplexIngredientData() {
        // Given
        Map<String, String> recipeInfo = Map.of("title", "Complex Dish");
        List<ArrayList<String>> complexIngredients = List.of(
                new ArrayList<>(Arrays.asList("Ingredient 1", "100.5", "ml")),
                new ArrayList<>(Arrays.asList("Ingredient 2", "2.25", "kg")),
                new ArrayList<>(Arrays.asList("Ingredient 3", "1", "pinch"))
        );
        List<String> multipleTags = List.of("complex", "gourmet", "time-consuming", "advanced");
        
        // When
        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(
                recipeInfo, complexIngredients, multipleTags
        );
        
        // Then
        assertEquals(3, outputData.getRecipeIngredients().size());
        assertEquals(4, outputData.getRecipeTags().size());
        assertEquals("100.5", outputData.getRecipeIngredients().get(0).get(1));
        assertEquals("ml", outputData.getRecipeIngredients().get(0).get(2));
        assertTrue(outputData.getRecipeTags().contains("gourmet"));
    }
}
