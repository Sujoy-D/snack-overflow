package use_case.generate_meal_plan;

import entity.Recipe;
import java.util.List;
import java.util.Map;

/**
 * Data access interface for meal plan operations.
 * Defines the contract for both generating and storing meal plans.
 */
public interface MealPlanDataAccessInterface {

    /**
     * Generate a weekly meal plan from an external API.
     *
     * @param diet the dietary preference
     * @param calorieLevel the calorie level preference
     * @param mealsPerDay number of meals per day
     * @return A map: day → list of recipes
     * @throws Exception if there's an error generating the meal plan
     */
    Map<String, List<Recipe>> generateWeeklyMealPlan(
            String diet,
            String calorieLevel,
            int mealsPerDay
    ) throws Exception;
    
    /**
     * Save a meal plan for a user to persistent storage.
     *
     * @param username the username
     * @param mealPlan the meal plan to save (day -> list of recipes)
     */
    void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan);
    
    /**
     * Load a meal plan for a user from persistent storage.
     *
     * @param username the username
     * @return the meal plan, or null if not found
     */
    Map<String, List<Recipe>> loadMealPlan(String username);
}
