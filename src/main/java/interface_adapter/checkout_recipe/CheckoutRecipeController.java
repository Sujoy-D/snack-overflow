package interface_adapter.checkout_recipe;

import entity.Recipe;
import use_case.checkout_recipe.CheckoutRecipeInputBoundary;
import use_case.checkout_recipe.CheckoutRecipeInputData;

/**
 * The Controller for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeController {

    private final CheckoutRecipeInputBoundary checkoutRecipeInteractor;

    public CheckoutRecipeController(CheckoutRecipeInputBoundary checkoutRecipeInteractor) {
        this.checkoutRecipeInteractor = checkoutRecipeInteractor;
    }

    /**
     * Executes the Checkout Recipe Use Case, with the current user's username and the recipe to check out.
     * @param username the current user's username.
     * @param recipe the recipe to check out.
     */
    public void execute(String username, Recipe recipe) {
        checkoutRecipeInteractor.execute(new CheckoutRecipeInputData(recipe, username));
    }
}
