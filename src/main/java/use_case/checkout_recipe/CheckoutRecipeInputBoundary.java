package use_case.checkout_recipe;

/**
 * Input boundary for the Checkout Recipe Use Case.
 * The Interactor will implement this.
 */
public interface CheckoutRecipeInputBoundary {
    /**
     * Executes the Checkout Recipe Use Case.
     * @param checkoutRecipeInputData Checkout Recipe Use Case input data (Recipe entity and String username)
     */
    void execute(CheckoutRecipeInputData checkoutRecipeInputData);
}
