package use_case.checkout_recipe;

import java.util.Map;

/**
 * Output Data for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeOutputData {

    private final Map<String, Object> recipeInfo;

    public CheckoutRecipeOutputData(Map<String, Object> recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public Map<String, Object> getRecipeInfo() {
        return recipeInfo;
    }
}
