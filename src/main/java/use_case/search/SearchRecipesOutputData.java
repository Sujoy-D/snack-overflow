package use_case.search;

import entity.Recipe;

import java.util.List;

/**
 * Output data carrying the searched recipes
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
