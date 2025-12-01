package use_case.checkout_recipe;

import entity.Recipe;

/**
 * Input Data for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeInputData {

    private final Recipe recipe;
    private final String username;

    public CheckoutRecipeInputData(Recipe recipe, String username) {
        this.recipe = recipe;
        this.username = username;
    }

    public String getUsername() { return username; }

    public Recipe getRecipe() {
        return recipe;
    }

    public Integer getRecipeId() {
        if (recipe != null) {
            return recipe.getRecipeId();
        } else {
            return null;
        }
    }
}
