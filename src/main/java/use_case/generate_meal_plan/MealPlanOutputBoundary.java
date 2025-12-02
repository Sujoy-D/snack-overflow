package use_case.generate_meal_plan;

import use_case.generate_meal_plan.MealPlanOutputData;

/**
 * Output boundary for the Generate Weekly Meal Plan use case.
 * The presenter will implement this interface to prepare the success or failure views.
 */
public interface MealPlanOutputBoundary {

    /**
     * Prepares the success view with the generated meal plan data.
     *
     * @param outputData the output data containing the weekly meal plan
     */
    void prepareSuccessView(MealPlanOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param errorMessage the message describing the error that occurred
     */
    void prepareFailView(String errorMessage);
}

