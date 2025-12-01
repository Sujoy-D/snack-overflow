package use_case.checkout_recipe;

import use_case.checkout_recipe.CheckoutRecipeDataAccessInterface;
import use_case.tagging.AddTagDataAccessInterface;
import entity.Recipe;

import java.util.ArrayList;
import java.util.List;
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
            Recipe recipe = checkoutRecipeInputData.getRecipe();

            Map<String, String> recipeInfo =
                    checkoutRecipeDAO.getRecipeInfo(checkoutRecipeInputData.getRecipe());

            ArrayList<ArrayList<String>> recipeIngredients =
                    checkoutRecipeDAO.getRecipeIngredients(checkoutRecipeInputData.getRecipe());

            Integer recipeId = checkoutRecipeInputData.getRecipeId();
            String username = checkoutRecipeInputData.getUsername();
            List<String> recipeTags = new ArrayList<>();

            if (username != null && recipeId != null && recipeId > 0) {
                List<String> storedTags = taggingDataAccess.getTagsForRecipe(username, recipeId);
                if (storedTags != null) {
                    recipeTags.addAll(storedTags);
                }
            }
            CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(recipeInfo, recipeIngredients, new ArrayList<>(recipeTags));

            checkoutRecipePresenter.prepareSuccessView(outputData);
        }
        catch (Exception e) {
            checkoutRecipePresenter.prepareFailView("Sorry, this recipe cannot be viewed: "
                                                            + e.getMessage() + "\nPlease try another recipe.");
        }


    }
}
