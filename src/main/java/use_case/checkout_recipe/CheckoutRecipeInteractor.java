package use_case.checkout_recipe;

import use_case.tagging.AddTagDataAccessInterface;

import java.util.ArrayList;
import java.util.Map;

public class CheckoutRecipeInteractor implements CheckoutRecipeInputBoundary {

    private final CheckoutRecipeDataAccessInterface checkoutRecipeDAO;
    private final CheckoutRecipeOutputBoundary checkoutRecipePresenter;
    private final AddTagDataAccessInterface taggingDataAccess;

    public CheckoutRecipeInteractor(CheckoutRecipeDataAccessInterface checkoutRecipeDAO,
                                    CheckoutRecipeOutputBoundary checkoutRecipePresenter,
                                    AddTagDataAccessInterface taggingDataAccess) {
        this.checkoutRecipeDAO = checkoutRecipeDAO;
        this.checkoutRecipePresenter = checkoutRecipePresenter;
        this.taggingDataAccess = taggingDataAccess;
    }

    @Override
    public void execute(CheckoutRecipeInputData checkoutRecipeInputData) {
        try {
            if (checkoutRecipeInputData.getRecipe() == null) {
                throw new NullRecipeException(); // Recipe is null
            }
            Map<String, String> recipeInfo =
                    checkoutRecipeDAO.getRecipeInfo(checkoutRecipeInputData.getRecipe());

            ArrayList<ArrayList<String>> recipeIngredients =
                    checkoutRecipeDAO.getRecipeIngredients(checkoutRecipeInputData.getRecipe());

            Integer recipeId = checkoutRecipeInputData.getRecipeId();
            String username = checkoutRecipeInputData.getUsername();
            ArrayList<String> recipeTags = new ArrayList<>();

            if (username != null) {
                // Recipe already not null from first if statement in try
                recipeTags.addAll(taggingDataAccess.getTagsForRecipe(username, recipeId));
            }

            CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(recipeInfo, recipeIngredients,
                    new ArrayList<>(recipeTags));

            checkoutRecipePresenter.prepareSuccessView(outputData);
        }
        catch (Exception e) {
            checkoutRecipePresenter.prepareFailView("Sorry, this recipe cannot be viewed: "
                                                            + e.getMessage() + "\nPlease try another recipe.");
        }
    }
}
