package use_case.generate_meal_plan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MealPlanInputDataTest {
    
    @Test
    void constructorAndGettersWork() {
        // Given
        String expectedDiet = "Vegetarian";
        String expectedCalorieLevel = "Medium";
        int expectedMealsPerDay = 3;
        
        // When
        MealPlanInputData inputData = new MealPlanInputData(expectedDiet, expectedCalorieLevel, expectedMealsPerDay);
        
        // Then
        assertEquals(expectedDiet, inputData.getDiet());
        assertEquals(expectedCalorieLevel, inputData.getCalorieLevel());
        assertEquals(expectedMealsPerDay, inputData.getMealsPerDay());
    }
    
    @Test
    void constructorHandlesNoneOption() {
        // Given
        String expectedDiet = "None";
        String expectedCalorieLevel = "Low";
        int expectedMealsPerDay = 1;
        
        // When
        MealPlanInputData inputData = new MealPlanInputData(expectedDiet, expectedCalorieLevel, expectedMealsPerDay);
        
        // Then
        assertEquals(expectedDiet, inputData.getDiet());
        assertEquals(expectedCalorieLevel, inputData.getCalorieLevel());
        assertEquals(expectedMealsPerDay, inputData.getMealsPerDay());
    }
    
    @Test
    void constructorHandlesVeganOption() {
        // Given
        String expectedDiet = "Vegan";
        String expectedCalorieLevel = "High";
        int expectedMealsPerDay = 2;
        
        // When
        MealPlanInputData inputData = new MealPlanInputData(expectedDiet, expectedCalorieLevel, expectedMealsPerDay);
        
        // Then
        assertEquals(expectedDiet, inputData.getDiet());
        assertEquals(expectedCalorieLevel, inputData.getCalorieLevel());
        assertEquals(expectedMealsPerDay, inputData.getMealsPerDay());
    }
    
    @Test
    void constructorHandlesNullValues() {
        // When
        MealPlanInputData inputData = new MealPlanInputData(null, null, 0);
        
        // Then
        assertNull(inputData.getDiet());
        assertNull(inputData.getCalorieLevel());
        assertEquals(0, inputData.getMealsPerDay());
    }
}
