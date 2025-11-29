package use_case.checkout_recipe;

import data_access.CheckoutRecipeDataAccessInterface;

import java.util.Map;

public class CheckoutRecipeInteractor implements CheckoutRecipeInputBoundary {

    private final CheckoutRecipeDataAccessInterface checkoutRecipeDAO;
    private final CheckoutRecipeOutputBoundary checkoutRecipePresenter;

    public CheckoutRecipeInteractor(CheckoutRecipeDataAccessInterface checkoutRecipeDAO,
                                    CheckoutRecipeOutputBoundary checkoutRecipePresenter) {
        this.checkoutRecipeDAO = checkoutRecipeDAO;
        this.checkoutRecipePresenter = checkoutRecipePresenter;
    }

    @Override
    public void execute(CheckoutRecipeInputData checkoutRecipeInputData) {
        Map<String, Object> recipeInfo = checkoutRecipeDAO.getRecipeInfo(checkoutRecipeInputData.getRecipe());

        CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(recipeInfo);

        checkoutRecipePresenter.prepareSuccessView(outputData);
    }
}
