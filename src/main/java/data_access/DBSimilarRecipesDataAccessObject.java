package data_access;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.similar_recipes.SimilarRecipeDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;

public class DBSimilarRecipesDataAccessObject implements SimilarRecipeDataAccessInterface {
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public ArrayList<Integer> getSimilarRecipeID(int recipeID) {

        // Loading dotenv for API key.
        Dotenv dotenv = Dotenv.load();

        // API call to get similar recipes to recipeID.
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("https://api.spoonacular.com/recipes/%s/similar?apiKey=%s", recipeID, dotenv.get("API_KEY")))
                .method("GET", null)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();

                final JSONArray responseBody = new JSONArray(response.body().string());

                if (!responseBody.isEmpty()) {
                    final ArrayList<Integer> similarRecipeID = new ArrayList<>();

                    for (int i = 0; i < responseBody.length(); i++) {
                        JSONObject recipe = responseBody.getJSONObject(i);
                        similarRecipeID.add(recipe.getInt("id"));
                        }

                    return similarRecipeID;
                }
                else {
                    // invalid API call - TODO: make a dedicated exception.
                    throw new RuntimeException();
                }
            }
        catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
