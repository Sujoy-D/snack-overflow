package data_access;

import entity.Recipe;
import entity.Ingredient;
import entity.Tag;

import java.io.*;
import java.util.*;

public class MealPlanStorage {

    private static String filename(String username) {
        return "mealplan_" + username + ".txt";
    }

    public static void saveMealPlan(String username,
                                    Map<String, List<Recipe>> mealPlan) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename(username)))) {

            for (String day : mealPlan.keySet()) {
                writer.println("DAY:" + day);

                for (Recipe recipe : mealPlan.get(day)) {
                    writer.println("RECIPE_ID:" + recipe.getRecipeId());
                    writer.println("TITLE:" + recipe.getTitle());
                }

                writer.println("ENDDAY");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Map<String, List<Recipe>> loadMealPlan(String username) {

        File file = new File(filename(username));
        if (!file.exists()) {
            return null;
        }

        Map<String, List<Recipe>> mealPlan = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            String currentDay = null;

            List<Recipe> currentRecipes = null;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("DAY:")) {
                    currentDay = line.substring(4);
                    currentRecipes = new ArrayList<>();
                    mealPlan.put(currentDay, currentRecipes);
                }
                else if (line.startsWith("RECIPE_ID:")) {
                    int id = Integer.parseInt(line.substring(10));

                    String titleLine = reader.readLine();
                    String title = titleLine.substring(6);

                    Recipe recipe = new Recipe(
                            id,
                            List.of(new Ingredient("placeholder","1","unit")),
                            title,
                            "",
                            "",
                            0,
                            "",
                            1,
                            List.of()
                    );

                    currentRecipes.add(recipe);
                }
                else if (line.equals("ENDDAY")) {
                    currentDay = null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return mealPlan;
    }
}