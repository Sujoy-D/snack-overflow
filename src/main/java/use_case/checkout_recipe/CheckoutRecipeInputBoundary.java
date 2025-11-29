package use_case.checkout_recipe;

/**
 * Input boundary for the Checkout Recipe Use Case.
 * The Interactor will implement this.
 */
public interface CheckoutRecipeInputBoundary {
    void execute(CheckoutRecipeInputData checkoutRecipeInputData);
}
