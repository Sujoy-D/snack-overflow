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

        recipeInfo.put("recipeID", recipe.getRecipeId() != null ? recipe.getRecipeId().toString() : "Unknown");
        recipeInfo.put("title", recipe.getTitle() != null ? recipe.getTitle() : "Unknown Recipe");
        recipeInfo.put("cooking time", recipe.getCookingTime() != null ? recipe.getCookingTime().toString() : "Not specified");
        recipeInfo.put("cuisine", recipe.getCuisine() != null ? recipe.getCuisine() : "Not specified");
        recipeInfo.put("meal type", recipe.getMealType() != null ? recipe.getMealType() : "Not specified");
        recipeInfo.put("instructions", recipe.getInstructions() != null ? recipe.getInstructions() : "No instructions available");
        recipeInfo.put("serving size", recipe.getServingSize() != null ? recipe.getServingSize().toString() : "Not specified");

        return recipeInfo;
    }

    @Override
    public ArrayList<ArrayList<String>> getRecipeIngredients(Recipe recipe) throws Exception {
        final ArrayList<ArrayList<String>> recipeIngredients = new ArrayList<>();

        if (recipe.getIngredients() != null) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ArrayList<String> ingredientData = new ArrayList<>();
                ingredientData.add(ingredient.getName() != null ? ingredient.getName() : "Unknown ingredient");
                ingredientData.add(ingredient.getQuantity() != null ? ingredient.getQuantity() : "?");
                ingredientData.add(ingredient.getUnit() != null ? ingredient.getUnit() : "");

                recipeIngredients.add(ingredientData);
            }
        }
        return recipeIngredients;
    }

    @Override
    public ArrayList<String> getRecipeTags(Recipe recipe) throws Exception {
        final ArrayList<String> recipeTags = new ArrayList<>();

        if (recipe.getTags() != null) {
            for (Tag tag : recipe.getTags()) {
                if (tag != null && tag.getName() != null) {
                    recipeTags.add(tag.getName());
                }
            }
        }

        return recipeTags;
    }
}