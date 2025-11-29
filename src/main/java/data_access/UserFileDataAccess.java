package data_access;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;

import java.io.*;
import java.util.*;

public class UserFileDataAccess implements AddRecipeDataAccessInterface {

    private static final String FILE_PATH = "src/main/java/data_access/temp_users.json";

    @Override
    public void saveRecipe(String username, Recipe recipe) {
        Map<String, List<Recipe>> allUsers = loadAllUsers();
        List<Recipe> recipes = allUsers.getOrDefault(username, new ArrayList<>());
        recipes.add(recipe);
        allUsers.put(username, recipes);
        saveAllUsers(allUsers);
    }

    @Override
    public List<Recipe> loadRecipes(String username) {
        Map<String, List<Recipe>> allUsers = loadAllUsers();
        return allUsers.getOrDefault(username, new ArrayList<>());
    }

    private Map<String, List<Recipe>> loadAllUsers() {
        Map<String, List<Recipe>> map = new HashMap<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return map;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder json = new StringBuilder();
            while ((line = br.readLine()) != null) {
                json.append(line);
            }

            // Simple parsing, assuming the format is predictable
            String content = json.toString().trim();
            if (!content.startsWith("{") || content.length() < 2) return map;

            // Remove { } and split users
            content = content.substring(1, content.length() - 1); // remove { }
            String[] users = content.split("],");

            for (String user : users) {
                String[] parts = user.split(":", 2);
                String usernameKey = parts[0].trim().replace("\"", "");
                String recipesArray = parts[1].trim();
                if (!recipesArray.endsWith("]")) recipesArray += "]";
                List<Recipe> recipeList = parseRecipes(recipesArray);
                map.put(usernameKey, recipeList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    private List<Recipe> parseRecipes(String jsonArray) {
        // VERY SIMPLE manual parsing
        // For now, just return empty list; you can expand to parse fields later
        return new ArrayList<>();
    }

    private void saveAllUsers(Map<String, List<Recipe>> allUsers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("{\n");
            int userCount = 0;
            for (Map.Entry<String, List<Recipe>> entry : allUsers.entrySet()) {
                bw.write("  \"" + entry.getKey() + "\": [\n");
                List<Recipe> recipes = entry.getValue();
                for (int i = 0; i < recipes.size(); i++) {
                    Recipe r = recipes.get(i);
                    bw.write("    {\n");
                    bw.write("      \"recipeId\": " + r.getRecipeId() + ",\n");
                    bw.write("      \"title\": \"" + r.getTitle() + "\",\n");
                    bw.write("      \"ingredients\": " + ingredientsToJson(r.getIngredients()) + ",\n");
                    bw.write("      \"instructions\": \"" + r.getInstructions() + "\",\n");
                    bw.write("      \"cuisine\": \"" + r.getCuisine() + "\",\n");
                    bw.write("      \"cookingTime\": " + r.getCookingTime() + ",\n");
                    bw.write("      \"mealType\": \"" + r.getMealType() + "\",\n");
                    bw.write("      \"servingSize\": " + r.getServingSize() + ",\n");
                    bw.write("      \"tags\": " + tagsToJson(r.getTags()) + "\n");
                    bw.write("    }" + (i < recipes.size() - 1 ? "," : "") + "\n");
                }
                bw.write("  ]" + (++userCount < allUsers.size() ? "," : "") + "\n");
            }
            bw.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add these private helper methods below saveAllUsers
    private String ingredientsToJson(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ing = ingredients.get(i);
            sb.append("{");
            sb.append("\"name\":\"").append(ing.getName()).append("\",");
            sb.append("\"quantity\":\"").append(ing.getQuantity()).append("\",");
            sb.append("\"unit\":\"").append(ing.getUnit()).append("\"");
            sb.append("}");
            if (i < ingredients.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String tagsToJson(List<Tag> tags) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            sb.append("\"").append(tag.getName()).append("\"");
            if (i < tags.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}