package gateways;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;
import use_case.generate_meal_plan.MealPlanDataAccessInterface;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SpoonacularMealPlanAPITest {

    /**
     * Fake HTTP Gateway to simulate JSON responses
     */
    static class FakeHttpGateway extends JavaHttpGateway {
        private final Map<String, String> responses = new HashMap<>();

        public void addResponse(String urlContains, String jsonResponse) {
            responses.put(urlContains, jsonResponse);
        }

        @Override
        public String get(String url) {
            for (String key : responses.keySet()) {
                if (url.contains(key)) {
                    return responses.get(key);
                }
            }
            throw new RuntimeException("No fake response for URL: " + url);
        }
    }


    @Test
    void testGenerateWeeklyMealPlan_success() throws Exception {
        FakeHttpGateway http = new FakeHttpGateway();


        String weeklyJson =
                "{\n" +
                        "  \"week\": {\n" +
                        "    \"monday\": {\"meals\": [{\"id\": 1}]},\n" +
                        "    \"tuesday\": {\"meals\": [{\"id\": 2}]},\n" +
                        "    \"wednesday\": {\"meals\": [{\"id\": 3}]},\n" +
                        "    \"thursday\": {\"meals\": [{\"id\": 4}]},\n" +
                        "    \"friday\": {\"meals\": [{\"id\": 5}]},\n" +
                        "    \"saturday\": {\"meals\": [{\"id\": 6}]},\n" +
                        "    \"sunday\": {\"meals\": [{\"id\": 7}]}\n" +
                        "  }\n" +
                        "}";

        http.addResponse("mealplanner/generate", weeklyJson);


        for (int i = 1; i <= 7; i++) {
            String recipeJson =
                    "{\n" +
                            "  \"title\": \"Recipe " + i + "\",\n" +
                            "  \"instructions\": \"Cook it\",\n" +
                            "  \"cuisines\": [\"Asian\"],\n" +
                            "  \"readyInMinutes\": 10,\n" +
                            "  \"dishTypes\": [\"Lunch\"],\n" +
                            "  \"servings\": 2,\n" +
                            "  \"extendedIngredients\": [\n" +
                            "    {\"name\": \"Egg\", \"amount\": 1, \"unit\": \"pcs\"}\n" +
                            "  ]\n" +
                            "}";

            http.addResponse("/recipes/" + i + "/information", recipeJson);
        }

        SpoonacularMealPlanAPI api = new SpoonacularMealPlanAPI(http);

        Map<String, List<Recipe>> result =
                api.generateWeeklyMealPlan("None", "Medium", 1);

        assertEquals(7, result.size());
        assertEquals("Recipe 1", result.get("monday").get(0).getTitle());
        assertEquals("Lunch", result.get("monday").get(0).getMealType());
        assertEquals("Asian", result.get("monday").get(0).getCuisine());
        assertEquals(1, result.get("monday").get(0).getIngredients().size());
    }


    @Test
    void testGenerateWeeklyMealPlan_handlesInvalidRecipeGracefully() throws Exception {
        FakeHttpGateway http = new FakeHttpGateway();


        http.addResponse("mealplanner/generate",
                "{ \"week\": { \"monday\": {\"meals\": [{\"id\": 99}]}, " +
                        "\"tuesday\": {\"meals\": []}, " +
                        "\"wednesday\": {\"meals\": []}, " +
                        "\"thursday\": {\"meals\": []}, " +
                        "\"friday\": {\"meals\": []}, " +
                        "\"saturday\": {\"meals\": []}, " +
                        "\"sunday\": {\"meals\": []} } }"
        );


        http.addResponse("/recipes/99/information", "{ INVALID JSON ");

        SpoonacularMealPlanAPI api = new SpoonacularMealPlanAPI(http);

        Map<String, List<Recipe>> result =
                api.generateWeeklyMealPlan("None", "Low", 1);

        assertEquals(7, result.size());
        assertEquals(0, result.get("monday").size(), "recipe should be skipped on error");
    }
}
