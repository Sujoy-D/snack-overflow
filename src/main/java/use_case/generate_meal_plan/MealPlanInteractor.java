package use_case.generate_meal_plan;

import data_access.MealPlanDataAccessInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interactor for generating a weekly meal plan.
 */
public class MealPlanInteractor implements MealPlanInputBoundary {

    private final MealPlanDataAccessInterface mealPlanAPI;
    private final MealPlanOutputBoundary presenter;

    public MealPlanInteractor(MealPlanDataAccessInterface mealPlanAPI,
                              MealPlanOutputBoundary presenter) {
        this.mealPlanAPI = mealPlanAPI;
        this.presenter = presenter;
    }

    @Override
    public void execute(MealPlanInputData inputData) {
        try {
            Map<String, List<String>> weeklyPlan =
                    mealPlanAPI.generateWeeklyMealPlan(
                            inputData.getDiet(),
                            inputData.getCalorieLevel(),
                            inputData.getMealsPerDay()
                    );

            MealPlanOutputData outputData =
                    new MealPlanOutputData(weeklyPlan, null);

            presenter.prepareSuccessView(outputData);

        } catch (Exception e) {

            presenter.prepareFailView("Failed to load meal plan: " + e.getMessage());
        }
    }
}

