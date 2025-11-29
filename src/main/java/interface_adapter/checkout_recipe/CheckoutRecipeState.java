package interface_adapter.checkout_recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutRecipeState {

    private Map<String, String> recipeInfo = new HashMap<>();
    private List<ArrayList<String>> recipeIngredients = new ArrayList<>();
    private String errorMessage = null;

    public Map<String, String> getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipeInfo(Map<String, String> recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public List<ArrayList<String>> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<ArrayList<String>> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
