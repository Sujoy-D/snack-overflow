package interface_adapter.save_recipe;

import interface_adapter.ViewModel;

/**
 * View Model for the Save Recipe Use Case.
 */
public class SaveRecipeViewModel extends ViewModel {
    private SaveRecipeState state = new SaveRecipeState();

    public SaveRecipeViewModel() {
		super("saveRecipe");
	}

    public SaveRecipeState getState() {
		return state;
	}

    public void setState(SaveRecipeState state) {
		this.state = state;
	}

    /**
     * Notify observers that the state has changed.
     */
    public void firePropertyChanged() {
        super.firePropertyChanged("state");
    }
}

