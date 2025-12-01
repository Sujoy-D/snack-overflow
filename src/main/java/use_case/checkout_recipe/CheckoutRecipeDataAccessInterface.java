package use_case.checkout_recipe;

import entity.Recipe;
import java.util.ArrayList;
import java.util.Map;

/**
 * Data access interface for the checkout recipe use case.
 * Defines the contract for retrieving detailed recipe information.
 */
public interface CheckoutRecipeDataAccessInterface {
    
    /**
     * Returns the information of the recipe provided.
     * @param recipe the recipe to get information for
     * @return the information of the recipe provided as a HashMap of information title to data
     * @throws Exception if there's an error retrieving recipe information
     */
    Map<String, String> getRecipeInfo(Recipe recipe) throws Exception;

    /**
     * Returns the ingredients of the recipe provided.
     * @param recipe the recipe to get ingredients for
     * @return the ingredients of the recipe
     * @throws Exception if there's an error retrieving recipe ingredients
     */
    ArrayList<ArrayList<String>> getRecipeIngredients(Recipe recipe) throws Exception;

    /**
     * Returns the tags of the recipe provided.
     * @param recipe the recipe to get tags for
     * @return the tags of the recipe
     * @throws Exception if there's an error retrieving recipe tags
     */
    ArrayList<String> getRecipeTags(Recipe recipe) throws Exception;
}
