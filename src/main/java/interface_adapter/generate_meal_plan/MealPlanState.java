package interface_adapter.generate_meal_plan;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The state of the Meal Plan ViewModel.
 * Holds:
 * - weekly meal plan (7 days, each day a list of recipe titles)
 * - error message (optional)
 */
public class MealPlanState {

    private Map<String, List<String>> mealPlan = new HashMap<>();

    private String errorMessage = null;

    public Map<String, List<String>> getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(Map<String, List<String>> mealPlan) {
        this.mealPlan = mealPlan;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
