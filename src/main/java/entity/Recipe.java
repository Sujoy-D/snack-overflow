package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Recipe.
 */
public class Recipe {

    private final Integer recipeId;
    private final String title;
    private final List<String> ingredients;
    private final String instructions;
    private final String cuisine;
    private final Integer cookingTime;
    private final String mealType;
    private final Integer servingSize;
    private final List<Tag> tags;

    /**
     * Creates a Recipe entity with the given parameters.
     * @param recipeId recipe ID
     * @param title name of the recipe
     * @param ingredients list of ingredients
     * @param instructions cooking instructions
     * @param cuisine type of cuisine
     * @param cookingTime cooking duration in minutes
     * @param mealType meal category
     * @param servingSize number of servings
     * @param tags number of tags
     *  @throws IllegalArgumentException if the password or name are empty
     */
    public Recipe(Integer recipeId,
                  String title,
                  List<String> ingredients,
                  String instructions,
                  String cuisine,
                  Integer cookingTime,
                  String mealType,
                  Integer servingSize,
                  List<Tag> tags) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Recipe title cannot be empty");
        }
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("Ingredients list cannot be empty");
        }
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.cuisine = cuisine;
        this.cookingTime = cookingTime;
        this.mealType = mealType;
        this.servingSize = servingSize;
        this.tags = tags != null ? tags : List.of();
    }

    public Integer getRecipeId() { return recipeId; }
    public String getTitle() { return title; }
    public List<String> getIngredients() { return ingredients; }
    public String getInstructions() { return instructions; }
    public String getCuisine() { return cuisine; }
    public Integer getCookingTime() { return cookingTime; }
    public String getMealType() { return mealType; }
    public Integer getServingSize() { return servingSize; }
    public List<Tag> getTags() { return tags; }

    @Override
    public String toString() {
        return title + " (" + cookingTime + " min, " + cuisine + ")";
    }
}
