package data_access;

import gateways.JavaHttpGateway;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.similar_recipes.SimilarRecipeDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;

public class DBSimilarRecipesDataAccessObject implements SimilarRecipeDataAccessInterface {
    private final JavaHttpGateway httpGateway;

    public DBSimilarRecipesDataAccessObject(JavaHttpGateway httpGateway) {
        this.httpGateway = httpGateway;
    }


    @Override
    public ArrayList<Integer> getSimilarRecipeID(int recipeID) throws Exception {
        String baseURL = String.format("https://api.spoonacular.com/recipes/%s/similar?apiKey", recipeID);
        String response = httpGateway.get(baseURL);
        final JSONArray responseBody = new JSONArray(response);

        final ArrayList<Integer> similarRecipeID = new ArrayList<>();

        if (!responseBody.isEmpty()) {

            for (int i = 0; i < responseBody.length(); i++) {
                JSONObject recipe = responseBody.getJSONObject(i);
                similarRecipeID.add(recipe.getInt("id"));
            }
        }
        return similarRecipeID;
    }
}
