package data_access;

import java.util.List;
import java.util.Map;

/**
 * Data Access interface for generating weekly meal plans.
 * Implemented by SpoonacularMealPlanAPI.
 */
public interface MealPlanDataAccessInterface {

    /**
     * Generate a weekly meal plan.
     *
     * @return A map: day → list of recipe titles
     */
    Map<String, List<String>> generateWeeklyMealPlan(
            String diet,
            String calorieLevel,
            int mealsPerDay
    ) throws Exception;
}

