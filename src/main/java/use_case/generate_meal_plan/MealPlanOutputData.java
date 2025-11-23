package use_case.generate_meal_plan;

import java.util.List;
import java.util.Map;

/**
 * Output Data for the Generate Weekly Meal Plan Use Case.
 *
 * Contains:
 *  - weekly meal plan (7 days → list of recipe titles)
 *  - optional error message
 */
public class MealPlanOutputData {

    private final Map<String, List<String>> weeklyPlan;
    private final String errorMessage;

    public MealPlanOutputData(Map<String, List<String>> weeklyPlan,
                              String errorMessage) {
        this.weeklyPlan = weeklyPlan;
        this.errorMessage = errorMessage;
    }

    public Map<String, List<String>> getWeeklyPlan() {
        return weeklyPlan;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
