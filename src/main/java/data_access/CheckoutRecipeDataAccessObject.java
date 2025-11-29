package data_access;

import entity.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutRecipeDataAccessObject implements CheckoutRecipeDataAccessInterface {

    @Override
    public Map<String, String> getRecipeInfo(Recipe recipe) {
        final Map<String, String> recipeInfo = new HashMap<>();

        recipeInfo.put("recipeID", recipe.getRecipeId().toString());
        recipeInfo.put("name", recipe.getTitle());
        recipeInfo.put("cooking time", recipe.getCookingTime().toString());
        recipeInfo.put("cuisine", recipe.getCuisine());
        recipeInfo.put("meal type", recipe.getMealType());
        recipeInfo.put("instructions", recipe.getInstructions());
        recipeInfo.put("serving size", recipe.getServingSize().toString());

        return recipeInfo;
    }

    @Override
    public ArrayList<ArrayList<String>> getRecipeIngredients(Recipe recipe) {
        final ArrayList<ArrayList<String>> recipeIngredients = new ArrayList<>();

        for (Ingredient ingredient : recipe.getIngredients()) {
            recipeIngredients.add(new ArrayList<ArrayList<String>>(ingredient.name, ingredient.quantity, ingredient.unit));
        }

        return recipeIngredients;
    }
}