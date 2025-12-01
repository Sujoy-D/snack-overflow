package use_case.search;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SearchRecipesOutputDataTest {
    
    @Test
    void constructorAndGettersWorkWithRecipes() {
        // Given
        Ingredient ingredient1 = new Ingredient("Tomato", "2", "pieces");
        Ingredient ingredient2 = new Ingredient("Cheese", "100", "g");
        Tag tag = new Tag(1, "vegetarian");
        
        Recipe recipe1 = new Recipe(
                1,
                List.of(ingredient1),
                "Tomato Salad",
                "Chop tomatoes",
                "Mediterranean",
                15,
                "Lunch",
                2,
                List.of(tag)
        );
        
        Recipe recipe2 = new Recipe(
                2,
                List.of(ingredient2),
                "Cheese Toast",
                "Toast bread, add cheese",
                "American",
                10,
                "Breakfast",
                1,
                List.of(tag)
        );
        
        List<Recipe> expectedRecipes = List.of(recipe1, recipe2);
        
        // When
        SearchRecipesOutputData outputData = new SearchRecipesOutputData(expectedRecipes);
        
        // Then
        assertEquals(expectedRecipes, outputData.getRecipes());
        assertEquals(2, outputData.getRecipes().size());
        assertEquals("Tomato Salad", outputData.getRecipes().get(0).getTitle());
        assertEquals("Cheese Toast", outputData.getRecipes().get(1).getTitle());
    }
    
    @Test
    void constructorAndGettersWorkWithEmptyList() {
        // Given
        List<Recipe> expectedRecipes = new ArrayList<>();
        
        // When
        SearchRecipesOutputData outputData = new SearchRecipesOutputData(expectedRecipes);
        
        // Then
        assertEquals(expectedRecipes, outputData.getRecipes());
        assertTrue(outputData.getRecipes().isEmpty());
    }
    
    @Test
    void constructorAndGettersWorkWithSingleRecipe() {
        // Given
        Ingredient ingredient = new Ingredient("Pasta", "200", "g");
        Tag tag = new Tag(2, "italian");
        
        Recipe recipe = new Recipe(
                3,
                List.of(ingredient),
                "Simple Pasta",
                "Boil pasta",
                "Italian",
                20,
                "Dinner",
                3,
                List.of(tag)
        );
        
        List<Recipe> expectedRecipes = List.of(recipe);
        
        // When
        SearchRecipesOutputData outputData = new SearchRecipesOutputData(expectedRecipes);
        
        // Then
        assertEquals(expectedRecipes, outputData.getRecipes());
        assertEquals(1, outputData.getRecipes().size());
        assertEquals("Simple Pasta", outputData.getRecipes().get(0).getTitle());
    }
    
    @Test
    void constructorHandlesNullList() {
        // When
        SearchRecipesOutputData outputData = new SearchRecipesOutputData(null);
        
        // Then
        assertNull(outputData.getRecipes());
    }
}
