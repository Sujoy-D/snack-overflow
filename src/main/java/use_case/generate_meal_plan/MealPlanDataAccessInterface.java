package use_case.generate_meal_plan;

import java.util.List;
import java.util.Map;

/**
 * Gateway interface for generating meal plans.
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
