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

    /**
     * Constructs a MealPlanController.
     *
     * @param interactor the input boundary that handles the use case logic
     */
    public MealPlanController(MealPlanInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Triggers the meal plan generation use case.
     *
     * @param diet the dietary preference selected by the user
     * @param calorieLevel the calorie level preference selected by the user
     * @param mealsPerDay the number of meals per day selected by the user
     */
    public void execute(String diet, String calorieLevel, int mealsPerDay) {
        MealPlanInputData inputData =
                new MealPlanInputData(diet, calorieLevel, mealsPerDay);

        interactor.execute(inputData);
    }
}
