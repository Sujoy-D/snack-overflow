package data_access;

import java.util.ArrayList;

/**
 * DAO interface for the Similar Recipes Use Case.
 */
public interface SimilarRecipeDataAccessInterface {

    /**
     * Returns the list of IDs of similar recipes to the Recipe ID provided.
     * @return the list of IDs of similar recipes to recipe with recipeID
     */
    ArrayList<Integer> getSimilarRecipeID(int recipeId) throws Exception;
}
