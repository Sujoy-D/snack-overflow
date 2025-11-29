package use_case.checkout_recipe;

import entity.Recipe;

/**
 * Input Data for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeInputData {

    private final Recipe recipe;

    public CheckoutRecipeInputData(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
