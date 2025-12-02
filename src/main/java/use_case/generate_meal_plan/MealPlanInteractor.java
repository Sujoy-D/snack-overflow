package use_case.generate_meal_plan;

import entity.Recipe;
import java.util.List;
import java.util.Map;

/**
 * Interactor that implements the business logic for generating a weekly meal plan.
 */
public class MealPlanInteractor implements MealPlanInputBoundary {

    private final MealPlanDataAccessInterface mealPlanAPI;
    private final MealPlanOutputBoundary presenter;

    /**
     * Constructs a MealPlanInteractor.
     *
     * @param mealPlanAPI the data access interface used to generate the weekly meal plan
     * @param presenter the presenter responsible for preparing the output view
     */
    public MealPlanInteractor(MealPlanDataAccessInterface mealPlanAPI,
                              MealPlanOutputBoundary presenter) {
        this.mealPlanAPI = mealPlanAPI;
        this.presenter = presenter;
    }

    /**
     * Executes the generate-weekly-meal-plan use case.
     *
     * @param inputData the input data containing diet, calorie level, and meals per day
     */
    @Override
    public void execute(MealPlanInputData inputData) {
        try {
            Map<String, List<Recipe>> weeklyPlan =
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
