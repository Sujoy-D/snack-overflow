package use_case.add_recipe;

import java.util.List;

import entity.Ingredient;
import entity.Tag;

public class AddRecipeInputData {
    private final Integer recipeId;
    private final String title;
    private final List<Ingredient> ingredients;
    private final String instructions;
    private final String cuisine;
    private final Integer cookingTime;
    private final String mealType;
    private final Integer servingSize;
    private final List<Tag> tags;

    public AddRecipeInputData(Integer recipeId, String title, List<Ingredient> ingredients,
                              String instructions, String cuisine, Integer cookingTime,
                              String mealType, Integer servingSize, List<Tag> tags) {
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cuisine = cuisine;
        this.cookingTime = cookingTime;
        this.mealType = mealType;
        this.servingSize = servingSize;
        this.tags = tags;
    }

    // Getters
    public Integer getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getCuisine() {
        return cuisine;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public String getMealType() {
        return mealType;
    }

    public Integer getServingSize() {
        return servingSize;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
