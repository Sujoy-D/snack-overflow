package use_case.similar_recipes;

import java.util.ArrayList;

/**
 * Data access interface for the Similar Recipes use case.
 * Defines the contract for retrieving similar recipes.
 */
public interface SimilarRecipeDataAccessInterface {

    /**
     * Returns the list of IDs of similar recipes to the Recipe ID provided.
     * @param recipeId the recipe ID to find similar recipes for
     * @return the list of IDs of similar recipes to recipe with recipeID
     * @throws Exception if there's an error retrieving similar recipes
     */
    ArrayList<Integer> getSimilarRecipeID(int recipeId) throws Exception;
}
