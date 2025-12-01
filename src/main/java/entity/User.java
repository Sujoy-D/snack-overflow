package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a User.
 */
public class User {

    private final Integer userId;
    private final String username;
    private final String passwordHash;
    private final String email; // optional
    private final List<Recipe> savedRecipes;
    private final List<Tag> customTags;
    private final java.util.Map<String, List<Recipe>> mealPlan;

    /**
     * Creates a new User entity.
     *
     * @param userId unique user ID
     * @param username username
     * @param email user email
     * @param passwordHash hashed password
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(Integer userId, String username, String email, String passwordHash) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.savedRecipes = new ArrayList<>();
        this.customTags = new ArrayList<>();
        this.mealPlan = new java.util.LinkedHashMap<>();
    }

    public Integer getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public List<Recipe> getSavedRecipes() { return savedRecipes; }
    public List<Tag> getCustomTags() { return customTags; }
    public java.util.Map<String, List<Recipe>> getMealPlan() { return mealPlan; }

    public void addRecipe(Recipe recipe) {
        if (!savedRecipes.contains(recipe)) {
            savedRecipes.add(recipe);
        }
    }

    public void addCustomTag(Tag tag) {
        customTags.add(tag);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", savedRecipes=" + savedRecipes.size() +
                ", customTags=" + customTags.size() +
                '}';
    }
}
