package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Singleton class to manage MongoDB database connections.
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private MongoClient mongoClient;
    private MongoDatabase database;
    
    private static final String DATABASE_NAME = "snack_overflow";
    
    private DatabaseManager() {
        initializeConnection();
    }
    
    /**
     * Get the singleton instance of DatabaseManager.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * Initialize MongoDB connection using connection string.
     */
    private void initializeConnection() {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            String connectionString = dotenv.get("MONGODB_URI");
            
            if (connectionString == null || connectionString.isEmpty()) {
                throw new RuntimeException("MONGODB_URI not found in environment variables");
            }
            
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase(DATABASE_NAME);
            
            // Test connection
            database.runCommand(new org.bson.Document("ping", 1));
            System.out.println("Successfully connected to MongoDB!");
            
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }
    
    /**
     * Get the MongoDB database instance.
     */
    public MongoDatabase getDatabase() {
        return database;
    }
    
    /**
     * Close the database connection.
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    
    /**
     * Check if the database connection is healthy.
     */
    public boolean isConnected() {
        try {
            database.runCommand(new org.bson.Document("ping", 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
