package interface_adapter.add_recipe;

import use_case.add_recipe.AddRecipeOutputBoundary;
import use_case.add_recipe.AddRecipeOutputData;

public class AddRecipePresenter implements AddRecipeOutputBoundary {

    private final AddRecipeViewModel viewModel;

    public AddRecipePresenter(AddRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void prepareSuccessView(AddRecipeOutputData data) {
        viewModel.setMessage("Successfully added recipe!");
        viewModel.firePropertyChanged();
    }

    public void prepareFailView(String error) {
        viewModel.setMessage("Failed to add recipe: " + error);
        viewModel.firePropertyChanged();
    }

    @Override
    public void present(AddRecipeOutputData outputData) {

    }
}
