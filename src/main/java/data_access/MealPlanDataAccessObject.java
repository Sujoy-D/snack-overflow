package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import entity.Recipe;
import org.bson.Document;
import java.util.*;

import use_case.generate_meal_plan.MealPlanDataAccessInterface;

/**
 * Data access class for meal plan operations.
 * Stores meal plans within user documents in the database.
 */
public class MealPlanDataAccessObject implements MealPlanDataAccessInterface {
    private final MongoCollection<Document> userCollection;
    
    public MealPlanDataAccessObject() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        this.userCollection = dbManager.getDatabase().getCollection("users");
    }
    
    /**
     * Save a meal plan for a user.
     *
     * @param username the username
     * @param mealPlan the meal plan to save (day -> list of recipes)
     */
    public void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan) {
        try {
            Document mealPlanDoc = new Document();
            
            for (Map.Entry<String, List<Recipe>> entry : mealPlan.entrySet()) {
                String day = entry.getKey();
                List<Recipe> recipes = entry.getValue();
                
                List<Document> recipeDocs = new ArrayList<>();
                for (Recipe recipe : recipes) {
                    Document recipeDoc = new Document()
                            .append("recipeId", recipe.getRecipeId())
                            .append("title", recipe.getTitle())
                            .append("instructions", recipe.getInstructions())
                            .append("cuisine", recipe.getCuisine())
                            .append("cookingTime", recipe.getCookingTime())
                            .append("mealType", recipe.getMealType())
                            .append("servingSize", recipe.getServingSize());
                    
                    // Add ingredients
                    List<Document> ingredientDocs = new ArrayList<>();
                    for (entity.Ingredient ingredient : recipe.getIngredients()) {
                        ingredientDocs.add(new Document()
                                .append("name", ingredient.getName())
                                .append("quantity", ingredient.getQuantity())
                                .append("unit", ingredient.getUnit()));
                    }
                    recipeDoc.append("ingredients", ingredientDocs);
                    
                    // Add tags
                    List<Document> tagDocs = new ArrayList<>();
                    for (entity.Tag tag : recipe.getTags()) {
                        tagDocs.add(new Document()
                                .append("tagId", tag.getTagId())
                                .append("name", tag.getName()));
                    }
                    recipeDoc.append("tags", tagDocs);
                    
                    recipeDocs.add(recipeDoc);
                }
                
                mealPlanDoc.append(day, recipeDocs);
            }
            
            userCollection.updateOne(
                    Filters.eq("username", username),
                    Updates.set("mealPlan", mealPlanDoc)
            );
            
        } catch (Exception e) {
            System.err.println("Error saving meal plan: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Load a meal plan for a user.
     *
     * @param username the username
     * @return the meal plan, or null if not found
     */
    public Map<String, List<Recipe>> loadMealPlan(String username) {
        try {
            Document userDoc = userCollection.find(Filters.eq("username", username)).first();
            
            if (userDoc == null) {
                return null;
            }
            
            Document mealPlanDoc = userDoc.get("mealPlan", Document.class);
            if (mealPlanDoc == null) {
                return null;
            }
            
            Map<String, List<Recipe>> mealPlan = new LinkedHashMap<>();
            
            for (String day : mealPlanDoc.keySet()) {
                List<Document> recipeDocs = mealPlanDoc.getList(day, Document.class);
                List<Recipe> recipes = new ArrayList<>();
                
                if (recipeDocs != null) {
                    for (Document recipeDoc : recipeDocs) {
                        Recipe recipe = documentToRecipe(recipeDoc);
                        recipes.add(recipe);
                    }
                }
                
                mealPlan.put(day, recipes);
            }
            
            return mealPlan;
            
        } catch (Exception e) {
            System.err.println("Error loading meal plan: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Convert MongoDB Document to Recipe entity.
     */
    private Recipe documentToRecipe(Document recipeDoc) {
        Integer recipeId = recipeDoc.getInteger("recipeId");
        String title = recipeDoc.getString("title");
        String instructions = recipeDoc.getString("instructions");
        String cuisine = recipeDoc.getString("cuisine");
        Integer cookingTime = recipeDoc.getInteger("cookingTime");
        String mealType = recipeDoc.getString("mealType");
        Integer servingSize = recipeDoc.getInteger("servingSize");

        // Convert ingredients
        List<entity.Ingredient> ingredients = new ArrayList<>();
        List<Document> ingredientDocs = recipeDoc.getList("ingredients", Document.class);
        if (ingredientDocs != null) {
            for (Document ingredientDoc : ingredientDocs) {
                entity.Ingredient ingredient = new entity.Ingredient(
                        ingredientDoc.getString("name"),
                        ingredientDoc.getString("quantity"),
                        ingredientDoc.getString("unit")
                );
                ingredients.add(ingredient);
            }
        }

        // Convert tags
        List<entity.Tag> tags = new ArrayList<>();
        List<Document> tagDocs = recipeDoc.getList("tags", Document.class);
        if (tagDocs != null) {
            for (Document tagDoc : tagDocs) {
                entity.Tag tag = new entity.Tag(
                        tagDoc.getInteger("tagId"),
                        tagDoc.getString("name")
                );
                tags.add(tag);
            }
        }

        // Use RecipeFactory to create the Recipe entity
        entity.RecipeFactory recipeFactory = new entity.RecipeFactory();
        return recipeFactory.create(recipeId, title, ingredients, instructions, cuisine, 
                                  cookingTime, mealType, servingSize, tags);
    }

    /**
     * This implementation only handles storage operations.
     * Use SpoonacularMealPlanAPI for generating meal plans from the API.
     * 
     * @throws UnsupportedOperationException always, as this implementation doesn't support API operations
     */
    @Override
    public Map<String, List<Recipe>> generateWeeklyMealPlan(String diet, String calorieLevel, int mealsPerDay) throws Exception {
        throw new UnsupportedOperationException("MealPlanDataAccessObject only handles storage operations. Use SpoonacularMealPlanAPI for generating meal plans from the API.");
    }
}
