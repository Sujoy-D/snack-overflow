package use_case.generate_meal_plan;

/**
 * Output boundary for the Generate Weekly Meal Plan Use Case.
 * The Presenter will implement this.
 */
public interface MealPlanOutputBoundary {

    void prepareSuccessView(MealPlanOutputData outputData);

    void prepareFailView(String errorMessage);
}

