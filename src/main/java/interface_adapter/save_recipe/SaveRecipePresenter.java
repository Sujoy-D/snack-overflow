package interface_adapter.save_recipe;

import javax.swing.JOptionPane;

import use_case.save_recipe.SaveRecipeOutputBoundary;
import use_case.save_recipe.SaveRecipeOutputData;

/**
 * Presenter for the Save Recipe Use Case.
 */
public class SaveRecipePresenter implements SaveRecipeOutputBoundary {
	private final SaveRecipeViewModel saveRecipeViewModel;

	public SaveRecipePresenter(SaveRecipeViewModel saveRecipeViewModel) {
		this.saveRecipeViewModel = saveRecipeViewModel;
	}

	@Override
	public void prepareSuccessView(SaveRecipeOutputData outputData) {
        saveRecipeViewModel.setState(new SaveRecipeState(true, outputData.getMessage(), outputData.getRecipeName()));
		saveRecipeViewModel.firePropertyChanged();

        // Show success message to user
        JOptionPane.showMessageDialog(null, outputData.getMessage(), "Recipe Saved", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void prepareFailView(String error) {
		saveRecipeViewModel.setState(new SaveRecipeState(false, error, null));
		saveRecipeViewModel.firePropertyChanged();

		// Show error message to user
		JOptionPane.showMessageDialog(null, error, "Save Recipe Error", JOptionPane.ERROR_MESSAGE);
	}
}
