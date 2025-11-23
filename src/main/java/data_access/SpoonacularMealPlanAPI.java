package data_access;

import gateways.JavaHttpGateway;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Real implementation of MealPlanDataAccessInterface
 * Calls the Spoonacular meal planner API.
 */
public class SpoonacularMealPlanAPI implements MealPlanDataAccessInterface {

    private final JavaHttpGateway httpGateway;

    public SpoonacularMealPlanAPI(JavaHttpGateway httpGateway) {
        this.httpGateway = httpGateway;
    }

    @Override
    public Map<String, List<String>> generateWeeklyMealPlan(
            String diet,
            String calorieLevel,
            int mealsPerDay
    ) throws Exception {

        String baseUrl = "https://api.spoonacular.com/mealplanner/generate?timeFrame=week";

        if (diet != null && !diet.equals("None")) {
            baseUrl += "&diet=" + diet.toLowerCase();
        }

        int targetCalories = switch (calorieLevel) {
            case "Low" -> 1600;
            case "High" -> 2400;
            default -> 2000;
        };

        baseUrl += "&targetCalories=" + targetCalories;

        String response = httpGateway.get(baseUrl);

        JSONObject json = new JSONObject(response);
        JSONObject weekObj = json.getJSONObject("week");

        Map<String, List<String>> result = new LinkedHashMap<>();

        List<String> days = Arrays.asList(
                "monday", "tuesday", "wednesday", "thursday",
                "friday", "saturday", "sunday"
        );

        for (String day : days) {
            JSONObject dayObj = weekObj.getJSONObject(day);
            JSONArray mealsArr = dayObj.getJSONArray("meals");

            List<String> titles = new ArrayList<>();

            for (int i = 0; i < Math.min(mealsPerDay, mealsArr.length()); i++) {
                JSONObject mealObj = mealsArr.getJSONObject(i);
                titles.add(mealObj.getString("title"));
            }

            result.put(day, titles);
        }

        return result;
    }
}
