package use_case.generate_meal_plan;

/**
 * Input boundary for the Generate Weekly Meal Plan Use Case.
 * The Interactor will implement this.
 */
public interface MealPlanInputBoundary {
    void execute(MealPlanInputData inputData);
}

