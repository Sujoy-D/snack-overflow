package use_case.checkout_recipe;

import java.util.ArrayList;
import java.util.Map;

import use_case.tagging.AddTagDataAccessInterface;

/**
 * Interactor for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeInteractor implements CheckoutRecipeInputBoundary {

    private final CheckoutRecipeDataAccessInterface checkoutRecipeDataAccessObject;
    private final CheckoutRecipeOutputBoundary checkoutRecipePresenter;
    private final AddTagDataAccessInterface taggingDataAccess;

    public CheckoutRecipeInteractor(CheckoutRecipeDataAccessInterface checkoutRecipeDataAccessObject,
                                    CheckoutRecipeOutputBoundary checkoutRecipePresenter,
                                    AddTagDataAccessInterface taggingDataAccess) {
        this.checkoutRecipeDataAccessObject = checkoutRecipeDataAccessObject;
        this.checkoutRecipePresenter = checkoutRecipePresenter;
        this.taggingDataAccess = taggingDataAccess;
    }

    @Override
    public void execute(CheckoutRecipeInputData checkoutRecipeInputData) {
        try {
            final Map<String, String> recipeInfo =
                    checkoutRecipeDataAccessObject.getRecipeInfo(checkoutRecipeInputData.getRecipe());

            final ArrayList<ArrayList<String>> recipeIngredients =
                    checkoutRecipeDataAccessObject.getRecipeIngredients(checkoutRecipeInputData.getRecipe());

            final Integer recipeId = checkoutRecipeInputData.getRecipeId();

            if (recipeId == null) {
                throw new NullRecipeException();
            }

            final String username = checkoutRecipeInputData.getUsername();
            final ArrayList<String> recipeTags = new ArrayList<>();

            if (username != null) {
                // Recipe already not null from first if statement in try
                recipeTags.addAll(taggingDataAccess.getTagsForRecipe(username, recipeId));
            }

            final CheckoutRecipeOutputData outputData = new CheckoutRecipeOutputData(recipeInfo, recipeIngredients,
                    new ArrayList<>(recipeTags));

            checkoutRecipePresenter.prepareSuccessView(outputData);
        }
        catch (Exception ex) {
            checkoutRecipePresenter.prepareFailView("Sorry, this recipe cannot be viewed: "
                                                            + ex.getMessage() + "\nPlease try another recipe.");
        }
    }
}
