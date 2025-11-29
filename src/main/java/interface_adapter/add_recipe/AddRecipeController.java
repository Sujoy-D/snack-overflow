package interface_adapter.add_recipe;

import entity.Ingredient;
import use_case.add_recipe.AddRecipeInputBoundary;
import use_case.add_recipe.AddRecipeInputData;
import entity.Tag;

import java.util.List;

public class AddRecipeController {

    private final AddRecipeInputBoundary interactor;

    public AddRecipeController(AddRecipeInputBoundary interactor) {
        this.interactor = interactor;
    }

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
        AddRecipeInputData inputData = new AddRecipeInputData(
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
