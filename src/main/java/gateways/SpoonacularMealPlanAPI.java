package gateways;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.generate_meal_plan.MealPlanDataAccessInterface;

import java.util.*;

public class SpoonacularMealPlanAPI implements MealPlanDataAccessInterface {

    private final JavaHttpGateway httpGateway;

    public SpoonacularMealPlanAPI(JavaHttpGateway httpGateway) {
        this.httpGateway = httpGateway;
    }

    @Override
    public Map<String, List<Recipe>> generateWeeklyMealPlan(
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

        Map<String, List<Recipe>> result = new LinkedHashMap<>();

        List<String> days = Arrays.asList(
                "monday", "tuesday", "wednesday", "thursday",
                "friday", "saturday", "sunday"
        );

        for (String day : days) {

            JSONObject dayObj = weekObj.getJSONObject(day);
            JSONArray mealsArr = dayObj.getJSONArray("meals");

            List<Recipe> recipesForDay = new ArrayList<>();

            int count = Math.min(mealsPerDay, mealsArr.length());

            for (int i = 0; i < count; i++) {
                JSONObject mealObj = mealsArr.getJSONObject(i);
                int recipeId = mealObj.getInt("id");

                Recipe recipe = fetchFullRecipe(recipeId);
                if (recipe != null) {
                    recipesForDay.add(recipe);
                }
            }

            result.put(day, recipesForDay);
        }

        return result;
    }

    private Recipe fetchFullRecipe(int id) {
        try {
            String url = "https://api.spoonacular.com/recipes/" + id + "/information";
            String response = httpGateway.get(url);

            JSONObject json = new JSONObject(response);

            String title = json.getString("title");
            String instructions = json.optString("instructions", "No instructions available");

            String cuisine = "Unknown";
            if (json.has("cuisines") && json.getJSONArray("cuisines").length() > 0) {
                cuisine = json.getJSONArray("cuisines").getString(0);
            }

            int cookingTime = json.optInt("readyInMinutes", 0);

            String mealType = "Unknown";
            if (json.has("dishTypes") && json.getJSONArray("dishTypes").length() > 0) {
                mealType = json.getJSONArray("dishTypes").getString(0);
            }

            int servings = json.optInt("servings", 1);

            List<Ingredient> ingredients = new ArrayList<>();

            if (json.has("extendedIngredients")) {
                JSONArray ingArr = json.getJSONArray("extendedIngredients");
                for (int i = 0; i < ingArr.length(); i++) {
                    JSONObject ingObj = ingArr.getJSONObject(i);
                    String name = ingObj.getString("name");

                    String quantity = String.valueOf(ingObj.getDouble("amount"));
                    String unit = ingObj.optString("unit", "");

                    ingredients.add(new Ingredient(name, quantity, unit));
                }
            }

            List<Tag> tags = new ArrayList<>();

            int tagCounter = 1;

            if (json.has("dishTypes")) {
                JSONArray typeArr = json.getJSONArray("dishTypes");
                for (int i = 0; i < typeArr.length(); i++) {
                    tags.add(new Tag(tagCounter++, typeArr.getString(i)));
                }
            }

            if (json.has("cuisines")) {
                JSONArray cuisineArr = json.getJSONArray("cuisines");
                for (int i = 0; i < cuisineArr.length(); i++) {
                    tags.add(new Tag(tagCounter++, cuisineArr.getString(i)));
                }
            }

            return new Recipe(
                    id,
                    ingredients,
                    title,
                    instructions,
                    cuisine,
                    cookingTime,
                    mealType,
                    servings,
                    tags
            );

        } catch (Exception e) {
            System.out.println("Error loading detailed recipe " + id + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * This implementation only handles API operations for generating meal plans.
     * Use MealPlanDataAccessObject for storing meal plans to persistent storage.
     *
     * @throws UnsupportedOperationException always, as this implementation doesn't support storage operations
     */
    @Override
    public void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan) {
        throw new UnsupportedOperationException("SpoonacularMealPlanAPI only handles API operations. Use MealPlanDataAccessObject for storing meal plans to persistent storage.");
    }

    /**
     * This implementation only handles API operations for generating meal plans.
     * Use MealPlanDataAccessObject for loading meal plans from persistent storage.
     *
     * @throws UnsupportedOperationException always, as this implementation doesn't support storage operations
     */
    @Override
    public Map<String, List<Recipe>> loadMealPlan(String username) {
        throw new UnsupportedOperationException("SpoonacularMealPlanAPI only handles API operations. Use MealPlanDataAccessObject for loading meal plans from persistent storage.");
    }
}