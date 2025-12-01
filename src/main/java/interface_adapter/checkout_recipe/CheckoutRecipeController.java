package interface_adapter.checkout_recipe;

import entity.Recipe;
import use_case.checkout_recipe.CheckoutRecipeInputBoundary;
import use_case.checkout_recipe.CheckoutRecipeInputData;

public class CheckoutRecipeController {

    private final CheckoutRecipeInputBoundary checkoutRecipeInteractor;

    public CheckoutRecipeController(CheckoutRecipeInputBoundary checkoutRecipeInteractor) {
        this.checkoutRecipeInteractor = checkoutRecipeInteractor;
    }

    public void execute(String username, Recipe recipe) {
        checkoutRecipeInteractor.execute(new CheckoutRecipeInputData(recipe, username));
    }
}
