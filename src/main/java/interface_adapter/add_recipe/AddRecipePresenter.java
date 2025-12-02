package interface_adapter.add_recipe;

import use_case.add_recipe.AddRecipeOutputBoundary;
import use_case.add_recipe.AddRecipeOutputData;

/**
 * Presenter class responsible for preparing the view model for recipe addition results.
 *
 * <p>
 * This class implements {@link AddRecipeOutputBoundary} and acts as the interface adapter
 * between the use case interactor and the user interface. It converts the output data
 * from the interactor into a format suitable for the view layer.
 */
public class AddRecipePresenter implements AddRecipeOutputBoundary {

    private final AddRecipeViewModel viewModel;

    /**
     * Constructs an AddRecipePresenter with the given view model.
     *
     * @param viewModel the view model that will be updated with success or failure messages
     */
    public AddRecipePresenter(AddRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the view model for a successful recipe addition.
     *
     * <p>
     * Updates the view model message and notifies observers of the change.
     *
     * @param data the output data from the use case interactor
     */
    public void prepareSuccessView(AddRecipeOutputData data) {
        viewModel.setMessage("Successfully added recipe!");
        viewModel.firePropertyChanged();
    }

    /**
     * Prepares the view model for a failed recipe addition.
     *
     *  <p>
     * Updates the view model message with the provided error and notifies observers of the change.
     *
     * @param error the error message describing why the addition failed
     */
    public void prepareFailView(String error) {
        viewModel.setMessage("Failed to add recipe: " + error);
        viewModel.firePropertyChanged();
    }

    /**
     * Handles the output data from the interactor and prepares the appropriate view.
     *
     * <p>
     * This method should determine whether the addition was successful or failed
     * and call either {@link #prepareSuccessView(AddRecipeOutputData)} or
     * {@link #prepareFailView(String)}.
     *
     * @param outputData the output data from the use case interactor
     */
    @Override
    public void present(AddRecipeOutputData outputData) {
    }
}
