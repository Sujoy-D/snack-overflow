package use_case.checkout_recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Output Data for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeOutputData {

    private final Map<String, String> recipeInfo;
    private final List<ArrayList<String>> recipeIngredients;
    private final List<String> recipeTags;

    public CheckoutRecipeOutputData(Map<String, String> recipeInfo,
                                    List<ArrayList<String>> recipeIngredients,
                                    List<String> recipeTage) {
        this.recipeInfo = recipeInfo;
        this.recipeIngredients = recipeIngredients;
        this.recipeTags = recipeTage;
    }

    public Map<String, String> getRecipeInfo() {
        return recipeInfo;
    }

    public List<ArrayList<String>> getRecipeIngredients() {
        return recipeIngredients;
    }

    public List<String> getRecipeTags() {
        return recipeTags;
    }

}
