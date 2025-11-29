package data_access;

import entity.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutRecipeDataAccessObject implements CheckoutRecipeDataAccessInterface {

    @Override
    public Map<String, Object> getRecipeInfo(Recipe recipe) {
        final Map<String, Object> recipeInfo = new HashMap<>();

        // Getting recipe info - strings
        recipeInfo.put("recipeID", recipe.getRecipeId().toString());
        recipeInfo.put("name", recipe.getTitle());
        recipeInfo.put("cooking time", recipe.getCookingTime());
        recipeInfo.put("cuisine", recipe.getCuisine());
        recipeInfo.put("meal type", recipe.getMealType());
        recipeInfo.put("instructions", recipe.getInstructions());
        recipeInfo.put("serving size", recipe.getServingSize());

        // Getting recipe info - Lists
        recipeInfo.put("ingredients", recipe.getIngredients());
        recipeInfo.put("tags", recipe.getTags());

        return recipeInfo;
    }
}