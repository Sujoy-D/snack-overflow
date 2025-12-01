package use_case.checkout_recipe;

/**
 * Output boundary for the Checkout Recipe Use Case.
 * The Presenter will implement this.
 */
public interface CheckoutRecipeOutputBoundary {

    void prepareSuccessView(CheckoutRecipeOutputData outputData);

    void prepareFailView(String errorMessage);
}
