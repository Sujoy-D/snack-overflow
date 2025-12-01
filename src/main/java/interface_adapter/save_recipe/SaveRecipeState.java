package interface_adapter.save_recipe;

/**
 * State for the Save Recipe View Model.
 */
public class SaveRecipeState {
	private boolean success;
	private String message;
	private String recipeName;

	public SaveRecipeState(boolean success, String message, String recipeName) {
		this.success = success;
		this.message = message;
		this.recipeName = recipeName;
	}

	public SaveRecipeState() {
		this.success = false;
		this.message = "";
		this.recipeName = null;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
}
