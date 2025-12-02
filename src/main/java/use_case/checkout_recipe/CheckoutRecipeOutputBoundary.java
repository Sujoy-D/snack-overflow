package use_case.checkout_recipe;

/**
 * Output boundary for the Checkout Recipe Use Case.
 * The Presenter will implement this.
 */
public interface CheckoutRecipeOutputBoundary {

    /**
     * Prepares the success view of the Presenter - no exceptions were thrown/handled.
     * @param outputData the processed data from the CheckoutRecipe Use Case Interactor.
     */
    void prepareSuccessView(CheckoutRecipeOutputData outputData);

    /**
     * Prepares the fail view of the Presenter - an exception had to be handled.
     * @param errorMessage processed error message of the exception that was handled.
     */
    void prepareFailView(String errorMessage);
}
