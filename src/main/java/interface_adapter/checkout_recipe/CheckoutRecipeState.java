package interface_adapter.checkout_recipe;

import java.util.HashMap;
import java.util.Map;

public class CheckoutRecipeState {

    private Map<String, Object> recipeInfo = new HashMap<>();
    private String errorMessage = null;

    public Map<String, Object> getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipeInfo(Map<String, Object> recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
