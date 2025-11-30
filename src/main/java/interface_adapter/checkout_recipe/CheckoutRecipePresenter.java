package interface_adapter.checkout_recipe;

import use_case.checkout_recipe.CheckoutRecipeOutputBoundary;
import use_case.checkout_recipe.CheckoutRecipeOutputData;

public class CheckoutRecipePresenter implements CheckoutRecipeOutputBoundary {

    private final CheckoutRecipeViewModel checkoutRecipeViewModel;

    public CheckoutRecipePresenter(CheckoutRecipeViewModel checkoutRecipeViewModel) {
        this.checkoutRecipeViewModel = checkoutRecipeViewModel;
    }

    @Override
    public void prepareSuccessView(CheckoutRecipeOutputData outputData) {
        CheckoutRecipeState newState = new CheckoutRecipeState();
        newState.setRecipeInfo(outputData.getRecipeInfo());
        newState.setRecipeIngredients(outputData.getRecipeIngredients());
        newState.setRecipeTags(outputData.getRecipeTags());

        // Update ViewModel
        checkoutRecipeViewModel.setState(newState);
        checkoutRecipeViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        CheckoutRecipeState newState = new CheckoutRecipeState();
        newState.setErrorMessage(errorMessage);

        // Update ViewModel
        checkoutRecipeViewModel.setState(newState);
        checkoutRecipeViewModel.firePropertyChanged();
    }
}
