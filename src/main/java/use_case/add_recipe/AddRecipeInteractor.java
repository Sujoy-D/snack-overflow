package use_case.add_recipe;

import entity.Recipe;
import use_case.add_recipe.AddRecipeDataAccessInterface;

public class AddRecipeInteractor implements AddRecipeInputBoundary {

    private final AddRecipeDataAccessInterface dataAccess;
    private final AddRecipeOutputBoundary presenter;
    private final String username;  // current user

    public AddRecipeInteractor(AddRecipeDataAccessInterface dataAccess,
                               AddRecipeOutputBoundary presenter,
                               String username) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
        this.username = username;
    }

    @Override
    public void execute(AddRecipeInputData inputData) {
        if (inputData.getTitle() == null || inputData.getTitle().isEmpty()) {
            presenter.present(new AddRecipeOutputData(false, "Recipe title cannot be empty"));
            return;
        }
        if (inputData.getIngredients() == null || inputData.getIngredients().isEmpty()) {
            presenter.present(new AddRecipeOutputData(false, "Ingredients cannot be empty"));
            return;
        }

        Recipe recipe = new Recipe(
                inputData.getRecipeId(),
                inputData.getIngredients(), inputData.getTitle(),
                inputData.getInstructions(),
                inputData.getCuisine(),
                inputData.getCookingTime(),
                inputData.getMealType(),
                inputData.getServingSize(),
                inputData.getTags()
        );

        dataAccess.saveRecipe(username, recipe);
        presenter.present(new AddRecipeOutputData(true, "Recipe added successfully!"));
    }
}
