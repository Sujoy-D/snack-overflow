package use_case.checkout_recipe;

/**
 * Output boundary for the Checkout Recipe Use Case.
 * The Presenter will implement this.
 */
public interface CheckoutRecipeOutputBoundary {

    void prepareSuccessView(CheckoutRecipeOutputData outputData);

    // TODO: does this require a fail view? there should not be an error when the user decides to see a specific recipe
}
