package use_case.checkout_recipe;

import data_access.CheckoutRecipeDataAccessInterface;

import java.util.ArrayList;
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
        try {
            Map<String, String> recipeInfo =
                    checkoutRecipeDAO.getRecipeInfo(checkoutRecipeInputData.getRecipe());

            ArrayList<ArrayList<String>> recipeIngredients =
                    checkoutRecipeDAO.getRecipeIngredients(checkoutRecipeInputData.getRecipe());

            ArrayList<String> recipeTags =
                    checkoutRecipeDAO.getRecipeTags(checkoutRecipeInputData.getRecipe());

            CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(recipeInfo, recipeIngredients, recipeTags);

            checkoutRecipePresenter.prepareSuccessView(outputData);
        }
        catch (Exception e) {
            checkoutRecipePresenter.prepareFailView("Sorry, this recipe cannot be viewed: "
                                                            + e.getMessage() + "\nPlease try another recipe.");
        }


    }
}
