package interface_adapter.generate_meal_plan;

import use_case.generate_meal_plan.MealPlanOutputBoundary;
import use_case.generate_meal_plan.MealPlanOutputData;

/**
 * Presenter for the Meal Plan use case.
 * Converts MealPlanOutputData into MealPlanState for the ViewModel.
 */
public class MealPlanPresenter implements MealPlanOutputBoundary {

    private final MealPlanViewModel viewModel;

    /**
     * Constructs a MealPlanPresenter.
     *
     * @param viewModel the ViewModel used to store and propagate state changes
     */
    public MealPlanPresenter(MealPlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the success view by updating the ViewModel
     * with the generated weekly meal plan.
     *
     * @param outputData the output data containing the weekly meal plan
     */
    @Override
    public void prepareSuccessView(MealPlanOutputData outputData) {

        MealPlanState newState = new MealPlanState();
        newState.setErrorMessage(null);
        newState.setMealPlan(outputData.getWeeklyPlan());

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view by updating the ViewModel
     * with the provided error message.
     *
     * @param errorMessage the message describing the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {

        MealPlanState newState = new MealPlanState();
        newState.setErrorMessage(errorMessage);
        newState.setMealPlan(null);

        viewModel.setState(newState);
        viewModel.firePropertyChanged();
    }
}

