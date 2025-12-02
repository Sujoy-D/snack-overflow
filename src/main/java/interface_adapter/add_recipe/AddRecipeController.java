package interface_adapter.add_recipe;

import java.util.List;

import entity.Ingredient;
import entity.Tag;
import use_case.add_recipe.AddRecipeInputBoundary;
import use_case.add_recipe.AddRecipeInputData;

public class AddRecipeController {

    private final AddRecipeInputBoundary interactor;

    public AddRecipeController(AddRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }
    /**
     * Adds a new recipe with the provided details.
     *
     * <p>
     * This method packages all input data into an {@link AddRecipeInputData} object
     * and passes it to the interactor to handle the business logic,
     * including validation and persistence.
     *
     * @param recipeID      the unique ID of the recipe
     * @param title         the title of the recipe; must not be null or empty
     * @param ingredients   the list of ingredients; must not be null or empty
     * @param instructions  the cooking instructions for the recipe
     * @param cuisine       the cuisine type of the recipe (e.g., "Italian")
     * @param cookingTime   the cooking time in minutes
     * @param mealType      the meal type (e.g., "Breakfast", "Dinner")
     * @param servingSize   the number of servings
     * @param tags          a list of tags associated with the recipe
     */

    public void addRecipe(
            int recipeID,
            String title,
            List<Ingredient> ingredients,
            String instructions,
            String cuisine,
            int cookingTime,
            String mealType,
            int servingSize,
            List<Tag> tags
    ) {
        final AddRecipeInputData inputData = new AddRecipeInputData(
                recipeID,
                title,
                ingredients,
                instructions,
                cuisine,
                cookingTime,
                mealType,
                servingSize,
                tags
        );
        interactor.execute(inputData);
    }
}
