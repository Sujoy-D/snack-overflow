package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import use_case.tagging.AddTagDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access implementation for tagging operations.
 * Stores user tags within the user documents in the database.
 */
public class AddTagDataAccessObject implements AddTagDataAccessInterface {
    private final MongoCollection<Document> userCollection;
    
    public AddTagDataAccessObject() {
        DatabaseManager dbManager = DatabaseManager.getInstance();
        this.userCollection = dbManager.getDatabase().getCollection("users");
    }
    
    @Override
    public void addTagToRecipe(String username, int recipeId, String tagName) {
        try {
            // First check if the tag already exists for this user/recipe combination
            List<String> existingTags = getTagsForRecipe(username, recipeId);
            if (existingTags.contains(tagName.toLowerCase().trim())) {
                return; // Tag already exists
            }
            
            // Create a tag document
            Document tagDoc = new Document()
                    .append("recipeId", recipeId)
                    .append("tagName", tagName.toLowerCase().trim());
            
            // Add the tag to the user's custom tags array
            userCollection.updateOne(
                    Filters.eq("username", username),
                    Updates.push("customTags", tagDoc)
            );
            
        } catch (Exception e) {
            System.err.println("Error adding tag to recipe: " + e.getMessage());
        }
    }
    
    @Override
    public List<String> getTagsForRecipe(String username, int recipeId) {
        List<String> tags = new ArrayList<>();
        
        try {
            Document userDoc = userCollection.find(Filters.eq("username", username)).first();
            
            if (userDoc != null) {
                List<Document> customTags = userDoc.getList("customTags", Document.class);
                
                if (customTags != null) {
                    for (Document tagDoc : customTags) {
                        Integer tagRecipeId = tagDoc.getInteger("recipeId");
                        String tagName = tagDoc.getString("tagName");
                        
                        if (tagRecipeId != null && tagRecipeId == recipeId && tagName != null) {
                            tags.add(tagName);
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error getting tags for recipe: " + e.getMessage());
        }
        
        return tags;
    }
}
