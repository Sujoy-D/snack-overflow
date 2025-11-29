package data_access;

import entity.Recipe;

import java.util.ArrayList;
import java.util.Map;

/**
 * DAO interface for the Checkout Recipe Use Case.
 */
public interface CheckoutRecipeDataAccessInterface {

    /**
     * Returns the information of the recipe provided.
     * @return the information of the recipe provided as a Hashmap of information title to data.
     */
    Map<String, String> getRecipeInfo(Recipe recipe);

    ArrayList<ArrayList<String>> getRecipeIngredients(Recipe recipe);

    ArrayList<String> getRecipeTags(Recipe recipe);
}