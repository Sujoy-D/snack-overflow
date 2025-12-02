package use_case.generate_meal_plan;

import entity.Recipe;
import java.util.List;
import java.util.Map;

/**
 * Output data for the Generate Weekly Meal Plan use case.
 *
 * <p>Contains:
 * <ul>
 *     <li>the generated weekly meal plan (7 days -> list of recipes)</li>
 *     <li>an optional error message</li>
 * </ul>
 */
public class MealPlanOutputData {

    private final Map<String, List<Recipe>> weeklyPlan;
    private final String errorMessage;

    /**
     * Constructs the output data for the weekly meal plan.
     *
     * @param weeklyPlan the generated weekly meal plan (day -> list of recipes)
     * @param errorMessage the error message, or {@code null} if the generation succeeded
     */
    public MealPlanOutputData(Map<String, List<Recipe>> weeklyPlan,
                              String errorMessage) {
        this.weeklyPlan = weeklyPlan;
        this.errorMessage = errorMessage;
    }

    /**
     * Returns the generated weekly meal plan.
     *
     * @return the weekly meal plan (day -> list of recipes)
     */
    public Map<String, List<Recipe>> getWeeklyPlan() {
        return weeklyPlan;
    }

    /**
     * Returns the error message if an error occurred.
     *
     * @return the error message, or {@code null} if none
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
