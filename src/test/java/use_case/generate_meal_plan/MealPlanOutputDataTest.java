package use_case.generate_meal_plan;

import org.junit.jupiter.api.Test;
import use_case.generate_meal_plan.MealPlanOutputData;
import entity.Recipe;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MealPlanOutputDataTest {

    @Test
    void testSuccessOutput() {
        Map<String, List<Recipe>> plan = Map.of("Monday", List.of());
        MealPlanOutputData output = new MealPlanOutputData(plan, null);

        assertEquals(plan, output.getWeeklyPlan());
        assertNull(output.getErrorMessage());
    }

    @Test
    void testFailureOutput() {
        MealPlanOutputData output = new MealPlanOutputData(null, "error!");

        assertNull(output.getWeeklyPlan());
        assertEquals("error!", output.getErrorMessage());
    }
}

