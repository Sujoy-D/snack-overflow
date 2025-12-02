package interface_adapter.generate_meal_plan;


import entity.Recipe;

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

    /** The weekly meal plan (day -> list of recipes). */
    private Map<String, List<Recipe>> mealPlan = new HashMap<>();

    /** An optional error message describing a failure, or null. */
    private String errorMessage = null;

    /**
     * Returns the weekly meal plan.
     *
     * @return the weekly meal plan map
     */
    public Map<String, List<Recipe>> getMealPlan() {
        return mealPlan;
    }

    /**
     * Sets the weekly meal plan.
     *
     * @param mealPlan the map containing the weekly meal plan
     */
    public void setMealPlan(Map<String, List<Recipe>> mealPlan) {
        this.mealPlan = mealPlan;
    }

    /**
     * Returns the error message if one exists.
     *
     * @return the error message, or null if none
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage the message describing the error
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
