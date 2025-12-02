package interface_adapter.save_recipe;

import use_case.save_recipe.SaveRecipeOutputBoundary;
import use_case.save_recipe.SaveRecipeOutputData;
import javax.swing.JOptionPane;

/**
 * Interface for handling UI notifications in the Save Recipe use case.
 */
interface SaveRecipeUINotifier {
	void showSuccessMessage(String message);
	void showErrorMessage(String message);
}

/**
 * Default implementation that shows JOptionPane dialogs.
 */
class DefaultSaveRecipeUINotifier implements SaveRecipeUINotifier {
	@Override
	public void showSuccessMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Recipe Saved", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Save Recipe Error", JOptionPane.ERROR_MESSAGE);
	}
}

/**
 * Presenter for the Save Recipe Use Case.
 */
public class SaveRecipePresenter implements SaveRecipeOutputBoundary {
	private SaveRecipeViewModel saveRecipeViewModel;
	private SaveRecipeUINotifier uiNotifier;

	public SaveRecipePresenter(SaveRecipeViewModel saveRecipeViewModel) {
		this.saveRecipeViewModel = saveRecipeViewModel;
		this.uiNotifier = new DefaultSaveRecipeUINotifier();
	}

	// Constructor for testing with custom UI notifier
	public SaveRecipePresenter(SaveRecipeViewModel saveRecipeViewModel, SaveRecipeUINotifier uiNotifier) {
		this.saveRecipeViewModel = saveRecipeViewModel;
		this.uiNotifier = uiNotifier;
	}

	@Override
	public void prepareSuccessView(SaveRecipeOutputData outputData) {
		saveRecipeViewModel.setState(new SaveRecipeState(true, outputData.getMessage(), outputData.getRecipeName()));
		saveRecipeViewModel.firePropertyChanged();

		// Show success message to user
		uiNotifier.showSuccessMessage(outputData.getMessage());
	}

	@Override
	public void prepareFailView(String error) {
		saveRecipeViewModel.setState(new SaveRecipeState(false, error, null));
		saveRecipeViewModel.firePropertyChanged();

		// Show error message to user
		uiNotifier.showErrorMessage(error);
	}
}
