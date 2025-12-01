package use_case.generate_meal_plan;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MealPlanOutputDataTest {
    
    @Test
    void constructorAndGettersWorkWithValidPlan() {
        // Given
        Ingredient ingredient = new Ingredient("Tomato", "2", "pieces");
        Tag tag = new Tag(1, "healthy");
        Recipe recipe = new Recipe(
                1,
                List.of(ingredient),
                "Tomato Salad",
                "Mix tomatoes",
                "Mediterranean",
                15,
                "Lunch",
                2,
                List.of(tag)
        );
        
        Map<String, List<Recipe>> expectedWeeklyPlan = new HashMap<>();
        expectedWeeklyPlan.put("monday", List.of(recipe));
        expectedWeeklyPlan.put("tuesday", List.of(recipe));
        
        String expectedErrorMessage = null;
        
        // When
        MealPlanOutputData outputData = new MealPlanOutputData(expectedWeeklyPlan, expectedErrorMessage);
        
        // Then
        assertEquals(expectedWeeklyPlan, outputData.getWeeklyPlan());
        assertNull(outputData.getErrorMessage());
        assertEquals(2, outputData.getWeeklyPlan().size());
    }
    
    @Test
    void constructorAndGettersWorkWithErrorMessage() {
        // Given
        Map<String, List<Recipe>> expectedWeeklyPlan = null;
        String expectedErrorMessage = "Failed to generate meal plan";
        
        // When
        MealPlanOutputData outputData = new MealPlanOutputData(expectedWeeklyPlan, expectedErrorMessage);
        
        // Then
        assertNull(outputData.getWeeklyPlan());
        assertEquals(expectedErrorMessage, outputData.getErrorMessage());
    }
    
    @Test
    void constructorHandlesEmptyPlan() {
        // Given
        Map<String, List<Recipe>> expectedWeeklyPlan = new HashMap<>();
        String expectedErrorMessage = null;
        
        // When
        MealPlanOutputData outputData = new MealPlanOutputData(expectedWeeklyPlan, expectedErrorMessage);
        
        // Then
        assertEquals(expectedWeeklyPlan, outputData.getWeeklyPlan());
        assertTrue(outputData.getWeeklyPlan().isEmpty());
        assertNull(outputData.getErrorMessage());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        MealPlanOutputData outputData = new MealPlanOutputData(null, null);
        
        // Then
        assertNull(outputData.getWeeklyPlan());
        assertNull(outputData.getErrorMessage());
    }
}
