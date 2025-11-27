package data_access;

import entity.Recipe;

import java.util.List;

public interface AddRecipeDataAccessInterface {
    void saveRecipe(String username, Recipe recipe);
    List<Recipe> loadRecipes(String username);
}
