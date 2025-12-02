package use_case.generate_meal_plan;

import use_case.generate_meal_plan.MealPlanInputData;

/**
 * Input boundary for the Generate Weekly Meal Plan Use Case.
 * The Interactor will implement this.
 */
public interface MealPlanInputBoundary {

    /**
     * Execute the use case with the given input data.
     *
     * @param inputData the input data for the meal plan generation use case
     */
    void execute(MealPlanInputData inputData);
}

