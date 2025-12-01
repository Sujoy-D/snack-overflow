package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

import use_case.user_management.UserDataAccessInterface;

/**
 * Data Access Object for User database operations.
 */
public class UserDataAccessObject implements UserDataAccessInterface {
    private final MongoCollection<Document> userCollection;
    private static final String COLLECTION_NAME = "users";
    
    public UserDataAccessObject() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        this.userCollection = dbManager.getDatabase().getCollection(COLLECTION_NAME);
    }
    
    /**
     * Save a new user to the database.
     * @param username the username
     * @param plainPassword the plain text password (will be hashed)
     * @param email the user's email (optional)
     * @return the created User object
     * @throws RuntimeException if username already exists
     */
    public User saveUser(String username, String plainPassword, String email) {
        // Check if username already exists
        if (userExists(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        // Hash the password
        String passwordHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        
        // Generate new userId (simple counter for now)
        Integer userId = getNextUserId();
        
        // Create document
        Document userDoc = new Document("_id", new ObjectId())
                .append("userId", userId)
                .append("username", username)
                .append("email", email)
                .append("passwordHash", passwordHash)
                .append("savedRecipes", new ArrayList<>())
                .append("customTags", new ArrayList<>())
                .append("createdAt", new java.util.Date());
        
        // Insert into database
        userCollection.insertOne(userDoc);
        
        // Return User entity
        return new User(userId, username, email, passwordHash);
    }
    
    /**
     * Find user by username.
     * @param username the username to search for
     * @return User object if found, null otherwise
     */
    public User findUserByUsername(String username) {
        Document userDoc = userCollection.find(Filters.eq("username", username)).first();
        
        if (userDoc == null) {
            return null;
        }
        
        return documentToUser(userDoc);
    }
    
    /**
     * Validate user login credentials.
     * @param username the username
     * @param plainPassword the plain text password
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String plainPassword) {
        User user = findUserByUsername(username);
        
        if (user == null) {
            return false;
        }
        
        return BCrypt.checkpw(plainPassword, user.getPasswordHash());
    }
    
    /**
     * Check if a username already exists.
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    public boolean userExists(String username) {
        Document userDoc = userCollection.find(Filters.eq("username", username)).first();
        return userDoc != null;
    }
    
    /**
     * Update user's last login time.
     * @param username the username
     */
    public void updateLastLogin(String username) {
        userCollection.updateOne(
            Filters.eq("username", username),
            new Document("$set", new Document("lastLogin", new java.util.Date()))
        );
    }
    
    /**
     * Save a recipe to the user's saved recipes list.
     *
     * @param username the username
     * @param recipe the recipe to save
     * @return true if successful, false otherwise
     */
    public boolean saveRecipeForUser(String username, entity.Recipe recipe) {
        try {
            Document recipeDoc = recipeToDocument(recipe);

            userCollection.updateOne(Filters.eq("username", username),
                    new Document("$push", new Document("savedRecipes", recipeDoc)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if a recipe is already saved by the user.
     *
     * @param username the username
     * @param recipeId the recipe ID
     * @return true if already saved, false otherwise
     */
    public boolean isRecipeSaved(String username, Integer recipeId) {
        try {
            Document user = userCollection
                    .find(Filters.and(Filters.eq("username", username), Filters.eq("savedRecipes.recipeId", recipeId)))
                    .first();

            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all saved recipes for a user.
     *
     * @param username the username
     * @return list of saved recipes
     */
    public java.util.List<entity.Recipe> getSavedRecipesForUser(String username) {
        java.util.List<entity.Recipe> recipes = new java.util.ArrayList<>();

        try {
            Document userDoc = userCollection.find(Filters.eq("username", username)).first();

            if (userDoc != null) {
                java.util.List<Document> savedRecipes = userDoc.getList("savedRecipes", Document.class);

                if (savedRecipes != null) {
                    for (Document recipeDoc : savedRecipes) {
                        entity.Recipe recipe = documentToRecipe(recipeDoc);
                        recipes.add(recipe);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }

    /**
     * Convert Recipe entity to MongoDB Document.
     */
    private Document recipeToDocument(entity.Recipe recipe) {
        Document recipeDoc = new Document().append("recipeId", recipe.getRecipeId()).append("title", recipe.getTitle())
                .append("instructions", recipe.getInstructions()).append("cuisine", recipe.getCuisine())
                .append("cookingTime", recipe.getCookingTime()).append("mealType", recipe.getMealType())
                .append("servingSize", recipe.getServingSize()).append("savedAt", new java.util.Date());

        // Convert ingredients
        java.util.List<Document> ingredientDocs = new java.util.ArrayList<>();
        for (entity.Ingredient ingredient : recipe.getIngredients()) {
            ingredientDocs.add(new Document().append("name", ingredient.getName())
                    .append("quantity", ingredient.getQuantity()).append("unit", ingredient.getUnit()));
        }
        recipeDoc.append("ingredients", ingredientDocs);

        // Convert tags
        java.util.List<Document> tagDocs = new java.util.ArrayList<>();
        for (entity.Tag tag : recipe.getTags()) {
            tagDocs.add(new Document().append("tagId", tag.getTagId()).append("name", tag.getName()));
        }
        recipeDoc.append("tags", tagDocs);

        return recipeDoc;
    }

    /**
     * Convert MongoDB Document to Recipe entity.
     */
    private entity.Recipe documentToRecipe(Document recipeDoc) {
        Integer recipeId = recipeDoc.getInteger("recipeId");
        String title = recipeDoc.getString("title");
        String instructions = recipeDoc.getString("instructions");
        String cuisine = recipeDoc.getString("cuisine");
        Integer cookingTime = recipeDoc.getInteger("cookingTime");
        String mealType = recipeDoc.getString("mealType");
        Integer servingSize = recipeDoc.getInteger("servingSize");

        // Convert ingredients
        java.util.List<entity.Ingredient> ingredients = new java.util.ArrayList<>();
        java.util.List<Document> ingredientDocs = recipeDoc.getList("ingredients", Document.class);
        if (ingredientDocs != null) {
            for (Document ingredientDoc : ingredientDocs) {
                entity.Ingredient ingredient = new entity.Ingredient(ingredientDoc.getString("name"),
                        ingredientDoc.getString("quantity"), ingredientDoc.getString("unit"));
                ingredients.add(ingredient);
            }
        }

        // Convert tags
        java.util.List<entity.Tag> tags = new java.util.ArrayList<>();
        java.util.List<Document> tagDocs = recipeDoc.getList("tags", Document.class);
        if (tagDocs != null) {
            for (Document tagDoc : tagDocs) {
                entity.Tag tag = new entity.Tag(tagDoc.getInteger("tagId"), tagDoc.getString("name"));
                tags.add(tag);
            }
        }

        // Use RecipeFactory to create the Recipe entity
        entity.RecipeFactory recipeFactory = new entity.RecipeFactory();
        return recipeFactory.create(recipeId, title, ingredients, instructions, cuisine, cookingTime, mealType,
                servingSize, tags);
    }

    /**
     * Convert MongoDB document to User entity.
     */
    private User documentToUser(Document doc) {
        Integer userId = doc.getInteger("userId");
        String username = doc.getString("username");
        String email = doc.getString("email");
        String passwordHash = doc.getString("passwordHash");
        
        return new User(userId, username, email, passwordHash);
    }
    
    /**
     * Generate next user ID (simple implementation).
     * In production, consider using MongoDB's auto-increment or UUID.
     */
    private Integer getNextUserId() {
        Document lastUser = userCollection.find()
                .sort(new Document("userId", -1))
                .limit(1)
                .first();
        
        if (lastUser == null) {
            return 1;
        }
        
        Integer lastUserId = lastUser.getInteger("userId");
        return (lastUserId != null) ? lastUserId + 1 : 1;
    }
}
