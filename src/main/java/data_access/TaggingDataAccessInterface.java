package data_access;

import entity.Recipe;

public interface TaggingDataAccessInterface {
    Recipe getRecipebyId(int recipeId);
    void saveRecipe(Recipe recipe);
}
