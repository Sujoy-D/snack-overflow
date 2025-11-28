package data_access;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

/**
 * Repository class for User database operations.
 */
public class UserRepository {
    private final MongoCollection<Document> userCollection;
    private static final String COLLECTION_NAME = "users";
    
    public UserRepository() {
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
