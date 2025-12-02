package use_case.user_management;

import java.util.List;

import entity.Recipe;
import entity.User;

/**
 * Data access interface for user management operations.
 * Defines the contract for user-related data operations.
 */
public interface UserDataAccessInterface {
    
    /**
     * Save a new user to the database.
     * @param username the username
     * @param plainPassword the plain text password (will be hashed)
     * @param email the user's email (optional)
     * @return the created User object
     * @throws RuntimeException if username already exists
     */
    User saveUser(String username, String plainPassword, String email);
    
    /**
     * Find a user by username.
     * @param username the username to search for
     * @return User object if found, null otherwise
     */
    User findUserByUsername(String username);
    
    /**
     * Validate user login credentials.
     * @param username the username
     * @param plainPassword the plain text password
     * @return true if credentials are valid, false otherwise
     */
    boolean validateLogin(String username, String plainPassword);
    
    /**
     * Check if a user exists in the database.
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean userExists(String username);
    
    /**
     * Update the last login timestamp for a user.
     * @param username the username
     */
    void updateLastLogin(String username);
    
    /**
     * Save a recipe for a user.
     * @param username the username
     * @param recipe the recipe to save
     * @return true if successful, false otherwise
     */
    boolean saveRecipeForUser(String username, Recipe recipe);
    
    /**
     * Check if a recipe is already saved by a user.
     * @param username the username
     * @param recipeId the recipe ID
     * @return true if recipe is saved, false otherwise
     */
    boolean isRecipeSaved(String username, Integer recipeId);
    
    /**
     * Get all saved recipes for a user.
     * @param username the username
     * @return list of saved recipes
     */
    List<Recipe> getSavedRecipesForUser(String username);
}
