package interface_adapter.generate_meal_plan;

import use_case.generate_meal_plan.MealPlanInputBoundary;
import use_case.generate_meal_plan.MealPlanInputData;

/**
 * Controller for the Generate Weekly Meal Plan Use Case.
 *
 * Receives raw input from the UI and sends it to the Interactor.
 */
public class MealPlanController {

    private final MealPlanInputBoundary interactor;

    public MealPlanController(MealPlanInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Trigger the meal plan generation use case.
     */
    public void execute(String diet, String calorieLevel, int mealsPerDay) {
        MealPlanInputData inputData =
                new MealPlanInputData(diet, calorieLevel, mealsPerDay);

        interactor.execute(inputData);
    }
}

