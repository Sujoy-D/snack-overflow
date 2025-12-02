package data_access;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import gateways.JavaHttpGateway;
import use_case.similar_recipes.SimilarRecipesDataAccessInterface;

/**
 * The Data Access Object for the Similar Recipes Use Case.
 */
public class SimilarRecipesDataAccessObject implements SimilarRecipesDataAccessInterface {
    private final JavaHttpGateway httpGateway;

    public SimilarRecipesDataAccessObject(JavaHttpGateway httpGateway) {
        this.httpGateway = httpGateway;
    }

    @Override
    public ArrayList<Integer> getSimilarRecipeID(int recipeID) throws Exception {
        final String baseLink = String.format("https://api.spoonacular.com/recipes/%s/similar?apiKey", recipeID);
        final String response = httpGateway.get(baseLink);
        final JSONArray responseBody = new JSONArray(response);

        ArrayList<Integer> similarRecipeID = null;

        if (!responseBody.isEmpty()) {
            similarRecipeID = new ArrayList<>();
            for (int i = 0; i < responseBody.length(); i++) {
                final JSONObject recipe = responseBody.getJSONObject(i);
                similarRecipeID.add(recipe.getInt("id"));
            }
        }
        return similarRecipeID;
    }
}
