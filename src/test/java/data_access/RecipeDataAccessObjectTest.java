package data_access;

import entity.Recipe;
import entity.Ingredient;
import use_case.user_management.UserDataAccessInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class RecipeDataAccessObjectTest {

    private MockUserDataAccessInterface mockUserDataAccess;
    private RecipeDataAccessObject recipeDAO;

    @BeforeEach
    void setUp() {
        mockUserDataAccess = new MockUserDataAccessInterface();
        recipeDAO = new RecipeDataAccessObject(mockUserDataAccess);
    }

    @Test
    void testSaveRecipe_Success() {
        // Arrange
        String username = "testuser";
        Recipe recipe = createTestRecipe();

        // Act
        recipeDAO.saveRecipe(username, recipe);

        // Assert
        assertTrue(mockUserDataAccess.saveRecipeForUserCalled);
        assertEquals(username, mockUserDataAccess.savedRecipeUsername);
        assertEquals(recipe, mockUserDataAccess.savedRecipe);
    }

    @Test
    void testLoadRecipes_Success() {
        // Arrange
        String username = "testuser";
        List<Recipe> expectedRecipes = List.of(createTestRecipe());
        mockUserDataAccess.setRecipesForUser(expectedRecipes);

        // Act
        List<Recipe> actualRecipes = recipeDAO.loadRecipes(username);

        // Assert
        assertTrue(mockUserDataAccess.getSavedRecipesForUserCalled);
        assertEquals(username, mockUserDataAccess.loadedRecipeUsername);
        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    void testSaveRecipeForUser_Success() {
        // Arrange
        String username = "testuser";
        Recipe recipe = createTestRecipe();
        mockUserDataAccess.setSaveRecipeForUserResult(true);

        // Act
        boolean result = recipeDAO.saveRecipeForUser(username, recipe);

        // Assert
        assertTrue(result);
        assertTrue(mockUserDataAccess.saveRecipeForUserCalled);
        assertEquals(username, mockUserDataAccess.savedRecipeUsername);
        assertEquals(recipe, mockUserDataAccess.savedRecipe);
    }

    @Test
    void testSaveRecipeForUser_Failure() {
        // Arrange
        String username = "testuser";
        Recipe recipe = createTestRecipe();
        mockUserDataAccess.setSaveRecipeForUserResult(false);

        // Act
        boolean result = recipeDAO.saveRecipeForUser(username, recipe);

        // Assert
        assertFalse(result);
        assertTrue(mockUserDataAccess.saveRecipeForUserCalled);
        assertEquals(username, mockUserDataAccess.savedRecipeUsername);
        assertEquals(recipe, mockUserDataAccess.savedRecipe);
    }

    @Test
    void testIsRecipeSaved_True() {
        // Arrange
        String username = "testuser";
        Integer recipeId = 1;
        mockUserDataAccess.setRecipeSavedResult(true);

        // Act
        boolean result = recipeDAO.isRecipeSaved(username, recipeId);

        // Assert
        assertTrue(result);
        assertTrue(mockUserDataAccess.isRecipeSavedCalled);
        assertEquals(username, mockUserDataAccess.checkedRecipeUsername);
        assertEquals(recipeId, mockUserDataAccess.checkedRecipeId);
    }

    @Test
    void testIsRecipeSaved_False() {
        // Arrange
        String username = "testuser";
        Integer recipeId = 1;
        mockUserDataAccess.setRecipeSavedResult(false);

        // Act
        boolean result = recipeDAO.isRecipeSaved(username, recipeId);

        // Assert
        assertFalse(result);
        assertTrue(mockUserDataAccess.isRecipeSavedCalled);
        assertEquals(username, mockUserDataAccess.checkedRecipeUsername);
        assertEquals(recipeId, mockUserDataAccess.checkedRecipeId);
    }

    @Test
    void testDefaultConstructor() {
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> {
            new RecipeDataAccessObject();
        });
    }

    // Helper method to create test recipe
    private Recipe createTestRecipe() {
        List<Ingredient> ingredients = List.of(new Ingredient("Tomato", "1", "pc"));
        return new Recipe(1, ingredients, "Test Recipe", "Test instructions", "Italian", 30, "Dinner", 1, null);
    }

    // Mock class
    static class MockUserDataAccessInterface implements UserDataAccessInterface {
        boolean saveRecipeForUserCalled = false;
        boolean getSavedRecipesForUserCalled = false;
        boolean isRecipeSavedCalled = false;
        String savedRecipeUsername;
        String loadedRecipeUsername;
        String checkedRecipeUsername;
        Recipe savedRecipe;
        Integer checkedRecipeId;
        List<Recipe> recipesForUser = new ArrayList<>();
        boolean saveRecipeForUserResult = true;
        boolean recipeSavedResult = false;

        @Override
        public boolean saveRecipeForUser(String username, Recipe recipe) {
            this.saveRecipeForUserCalled = true;
            this.savedRecipeUsername = username;
            this.savedRecipe = recipe;
            return saveRecipeForUserResult;
        }

        @Override
        public List<Recipe> getSavedRecipesForUser(String username) {
            this.getSavedRecipesForUserCalled = true;
            this.loadedRecipeUsername = username;
            return recipesForUser;
        }

        @Override
        public boolean isRecipeSaved(String username, Integer recipeId) {
            this.isRecipeSavedCalled = true;
            this.checkedRecipeUsername = username;
            this.checkedRecipeId = recipeId;
            return recipeSavedResult;
        }

        // Stub implementations for other interface methods
        @Override
        public entity.User saveUser(String username, String plainPassword, String email) {
            return null;
        }

        @Override
        public entity.User findUserByUsername(String username) {
            return null;
        }

        @Override
        public boolean validateLogin(String username, String plainPassword) {
            return false;
        }

        @Override
        public boolean userExists(String username) {
            return false;
        }

        @Override
        public void updateLastLogin(String username) {
            // stub
        }

        // Setter methods for test setup
        public void setRecipesForUser(List<Recipe> recipes) {
            this.recipesForUser = recipes;
        }

        public void setSaveRecipeForUserResult(boolean result) {
            this.saveRecipeForUserResult = result;
        }

        public void setRecipeSavedResult(boolean result) {
            this.recipeSavedResult = result;
        }
    }
}
