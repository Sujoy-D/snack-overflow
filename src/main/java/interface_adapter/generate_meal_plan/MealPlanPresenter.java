package interface_adapter.generate_meal_plan;

import use_case.generate_meal_plan.MealPlanOutputBoundary;
import use_case.generate_meal_plan.MealPlanOutputData;

/**
 * Presenter for the Meal Plan use case.
 * Converts MealPlanOutputData into MealPlanState for the ViewModel.
 */
public class MealPlanPresenter implements MealPlanOutputBoundary {

    private final MealPlanViewModel viewModel;

    public MealPlanPresenter(MealPlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(MealPlanOutputData outputData) {

        MealPlanState newState = new MealPlanState();
        newState.setErrorMessage(null);
        newState.setMealPlan(outputData.getWeeklyPlan());

        // Update ViewModel
        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

        MealPlanState newState = new MealPlanState();
        newState.setErrorMessage(errorMessage);

        // Update ViewModel
        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }
}

