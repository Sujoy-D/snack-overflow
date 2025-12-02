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

    public String getUsername() {
        return username;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Returns the recipe ID of the recipe variable. This method is always used from a non-null recipe context.
     * @return the recipe ID of the recipe variable.
     */
    public Integer getRecipeId() {
        return recipe.getRecipeId();
    }
}
