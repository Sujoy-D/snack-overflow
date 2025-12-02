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
     * Returns the recipe ID of the recipe variable, or null is the recipe is null.
     * @return the recipe ID of the recipe variable, or null is the recipe is null.
     */
    public Integer getRecipeId() {
        Integer returnedInt = null;
        if (recipe != null) {
            returnedInt = recipe.getRecipeId();
        }
        return returnedInt;
    }
}
