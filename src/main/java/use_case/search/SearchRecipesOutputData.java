package use_case.search;

import java.util.List;

import entity.Recipe;

/**
 * Output data carrying the searched recipes.
 */
public class SearchRecipesOutputData {
    private final List<Recipe> recipes;
    
    public SearchRecipesOutputData(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    
    public List<Recipe> getRecipes() {
        return recipes;
    }
}
