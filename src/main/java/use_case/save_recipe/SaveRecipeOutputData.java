package use_case.save_recipe;

/**
 * Output Data for the Save Recipe Use Case.
 */
public class SaveRecipeOutputData {
	private final boolean success;
	private final String message;
	private final String recipeName;

	public SaveRecipeOutputData(boolean success, String message, String recipeName) {
		this.success = success;
		this.message = message;
		this.recipeName = recipeName;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getRecipeName() {
		return recipeName;
	}
}
