package data_access;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutRecipeDataAccessObject implements CheckoutRecipeDataAccessInterface {

    @Override
    public Map<String, String> getRecipeInfo(Recipe recipe) throws Exception {
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
    public ArrayList<ArrayList<String>> getRecipeIngredients(Recipe recipe) throws Exception {
        final ArrayList<ArrayList<String>> recipeIngredients = new ArrayList<>();

        for (Ingredient ingredient : recipe.getIngredients()) {
            ArrayList<String> ingredientData = new ArrayList<>();
            ingredientData.add(ingredient.getName());
            ingredientData.add(ingredient.getQuantity());
            ingredientData.add(ingredient.getUnit());

            recipeIngredients.add(ingredientData);
        }

        return recipeIngredients;
    }

    @Override
    public ArrayList<String> getRecipeTags(Recipe recipe) throws Exception {
        final ArrayList<String> recipeTags = new ArrayList<>();

        for (Tag tag : recipe.getTags()) {
            recipeTags.add(tag.getName());
        }

        return recipeTags;
    }
}