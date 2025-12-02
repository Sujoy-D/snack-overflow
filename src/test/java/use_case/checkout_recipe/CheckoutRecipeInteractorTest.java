package use_case.checkout_recipe;

import entity.Recipe;
import entity.Ingredient;
import org.junit.jupiter.api.Test;
import use_case.tagging.AddTagDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Comprehensive test suite for CheckoutRecipeInteractor.
 * 
 * This test class verifies the business logic for checking out/viewing recipes,
 * including successful recipe retrieval, tag handling, and error scenarios.
 * 
 * Test Categories:
 * - Successful recipe checkout with various configurations
 * - Recipe retrieval with and without tags
 * - System error handling (data access exceptions)
 * - Edge cases with null/invalid data
 * 
 * All tests use mock implementations to isolate the unit under test.
 */
class CheckoutRecipeInteractorTest {

    @Test
    void executeSuccessfulRecipeCheckout() {
        // Given - Valid recipe checkout data
        MockCheckoutRecipeDataAccess dataAccess = new MockCheckoutRecipeDataAccess();
        MockCheckoutRecipePresenter presenter = new MockCheckoutRecipePresenter();
        MockTaggingDataAccess taggingDataAccess = new MockTaggingDataAccess();
        CheckoutRecipeInteractor interactor = new CheckoutRecipeInteractor(
            dataAccess, presenter, taggingDataAccess);

        // Setup test data
        Recipe recipe = createTestRecipe();
        Map<String, String> recipeInfo = new HashMap<>();
        recipeInfo.put("title", "Test Recipe");
        recipeInfo.put("cookingTime", "30");
        
        ArrayList<ArrayList<String>> ingredients = new ArrayList<>();
        ArrayList<String> ingredient1 = new ArrayList<>(Arrays.asList("Flour", "2", "cups"));
        ingredients.add(ingredient1);

        dataAccess.setRecipeInfo(recipeInfo);
        dataAccess.setRecipeIngredients(ingredients);
        taggingDataAccess.setTags(Arrays.asList("easy", "dessert"));

        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(
            recipe, "testuser");

        // When
        interactor.execute(inputData);

        // Then - Verify successful checkout
        assertTrue(presenter.isSuccessCalled(), "Success presenter should be called");
        assertFalse(presenter.isFailureCalled(), "Failure presenter should not be called");
        
        CheckoutRecipeOutputData outputData = presenter.getOutputData();
        assertNotNull(outputData, "Output data should not be null");
        assertEquals(recipeInfo, outputData.getRecipeInfo());
        assertEquals(ingredients, outputData.getRecipeIngredients());
        assertEquals(2, outputData.getRecipeTags().size());
        assertTrue(outputData.getRecipeTags().contains("easy"));
        assertTrue(outputData.getRecipeTags().contains("dessert"));

        // Verify data access calls
        assertTrue(dataAccess.isGetRecipeInfoCalled());
        assertTrue(dataAccess.isGetRecipeIngredientsCalled());
        assertTrue(taggingDataAccess.isGetTagsForRecipeCalled());
    }

    @Test
    void executeSuccessfulCheckoutWithoutUserTags() {
        // Given - Recipe checkout without username/recipeId (no tags)
        MockCheckoutRecipeDataAccess dataAccess = new MockCheckoutRecipeDataAccess();
        MockCheckoutRecipePresenter presenter = new MockCheckoutRecipePresenter();
        MockTaggingDataAccess taggingDataAccess = new MockTaggingDataAccess();
        CheckoutRecipeInteractor interactor = new CheckoutRecipeInteractor(
            dataAccess, presenter, taggingDataAccess);

        Recipe recipe = createTestRecipe();
        Map<String, String> recipeInfo = new HashMap<>();
        recipeInfo.put("title", "Test Recipe");
        
        ArrayList<ArrayList<String>> ingredients = new ArrayList<>();
        dataAccess.setRecipeInfo(recipeInfo);
        dataAccess.setRecipeIngredients(ingredients);

        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(
            recipe, null);

        // When
        interactor.execute(inputData);

        // Then - Should succeed without tags
        assertTrue(presenter.isSuccessCalled());
        CheckoutRecipeOutputData outputData = presenter.getOutputData();
        assertEquals(0, outputData.getRecipeTags().size(), "Should have no tags");
        assertFalse(taggingDataAccess.isGetTagsForRecipeCalled(), 
                   "Should not call tagging service without user/recipe ID");
    }

    @Test
    void executeSuccessfulCheckoutWithNullUsername() {
        // Given - Recipe checkout with null username
        MockCheckoutRecipeDataAccess dataAccess = new MockCheckoutRecipeDataAccess();
        MockCheckoutRecipePresenter presenter = new MockCheckoutRecipePresenter();
        MockTaggingDataAccess taggingDataAccess = new MockTaggingDataAccess();
        CheckoutRecipeInteractor interactor = new CheckoutRecipeInteractor(
            dataAccess, presenter, taggingDataAccess);

        Recipe recipe = createTestRecipe();
        dataAccess.setRecipeInfo(new HashMap<>());
        dataAccess.setRecipeIngredients(new ArrayList<>());

        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(
            recipe, null);

        // When
        interactor.execute(inputData);

        // Then - Should succeed without calling tagging service
        assertTrue(presenter.isSuccessCalled());
        assertFalse(taggingDataAccess.isGetTagsForRecipeCalled());
    }

    @Test
    void executeHandlesDataAccessException() {
        // Given - Data access that throws exception
        MockCheckoutRecipeDataAccess dataAccess = new MockCheckoutRecipeDataAccess();
        MockCheckoutRecipePresenter presenter = new MockCheckoutRecipePresenter();
        MockTaggingDataAccess taggingDataAccess = new MockTaggingDataAccess();
        CheckoutRecipeInteractor interactor = new CheckoutRecipeInteractor(
            dataAccess, presenter, taggingDataAccess);

        dataAccess.setThrowException(true);
        Recipe recipe = createTestRecipe();

        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(
            recipe, "testuser");

        // When
        interactor.execute(inputData);

        // Then - Should handle exception and call failure presenter
        assertTrue(presenter.isFailureCalled(), "Failure presenter should be called");
        assertFalse(presenter.isSuccessCalled(), "Success presenter should not be called");
        
        String errorMessage = presenter.getErrorMessage();
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Sorry, this recipe cannot be viewed"));
        assertTrue(errorMessage.contains("Please try another recipe"));
    }

    @Test
    void executeHandlesTaggingServiceException() {
        // Given - Tagging service throws exception
        MockCheckoutRecipeDataAccess dataAccess = new MockCheckoutRecipeDataAccess();
        MockCheckoutRecipePresenter presenter = new MockCheckoutRecipePresenter();
        MockTaggingDataAccess taggingDataAccess = new MockTaggingDataAccess();
        CheckoutRecipeInteractor interactor = new CheckoutRecipeInteractor(
            dataAccess, presenter, taggingDataAccess);

        // Setup successful recipe data but failing tagging service
        dataAccess.setRecipeInfo(new HashMap<>());
        dataAccess.setRecipeIngredients(new ArrayList<>());
        taggingDataAccess.setThrowException(true);

        Recipe recipe = createTestRecipe();
        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(
            recipe, "testuser");

        // When
        interactor.execute(inputData);

        // Then - Should handle tagging exception
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
    }

    @Test
    void executeHandlesNullRecipeException() {
        // Given - Tagging service throws exception
        MockCheckoutRecipeDataAccess dataAccess = new MockCheckoutRecipeDataAccess();
        MockCheckoutRecipePresenter presenter = new MockCheckoutRecipePresenter();
        MockTaggingDataAccess taggingDataAccess = new MockTaggingDataAccess();
        CheckoutRecipeInteractor interactor = new CheckoutRecipeInteractor(
                dataAccess, presenter, taggingDataAccess);

        dataAccess.setRecipeInfo(new HashMap<>());
        dataAccess.setRecipeIngredients(new ArrayList<>());
        taggingDataAccess.setThrowException(false);


        // Create null recipe in InputData - causes NullRecipeException
        CheckoutRecipeInputData inputData = new CheckoutRecipeInputData(
                null, "test-user");

        // When
        interactor.execute(inputData);

        // Then - Should handle NullRecipeException
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
    }

    private Recipe createTestRecipe() {
        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("Flour", "2", "cups"),
            new Ingredient("Sugar", "1", "cup")
        );
        return new Recipe(123, ingredients, "Test Recipe", "Mix and bake", 
                         "American", 30, "Dessert", 8, Collections.emptyList());
    }

    /**
     * Mock implementation of CheckoutRecipeDataAccessInterface for testing.
     */
    private static class MockCheckoutRecipeDataAccess implements CheckoutRecipeDataAccessInterface {
        private boolean getRecipeInfoCalled = false;
        private boolean getRecipeIngredientsCalled = false;
        private boolean throwException = false;
        private Map<String, String> recipeInfo;
        private ArrayList<ArrayList<String>> recipeIngredients;

        public void setRecipeInfo(Map<String, String> recipeInfo) {
            this.recipeInfo = recipeInfo;
        }

        public void setRecipeIngredients(ArrayList<ArrayList<String>> ingredients) {
            this.recipeIngredients = ingredients;
        }

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        @Override
        public Map<String, String> getRecipeInfo(Recipe recipe) {
            getRecipeInfoCalled = true;
            if (throwException) {
                throw new RuntimeException("Database connection error");
            }
            return recipeInfo;
        }

        @Override
        public ArrayList<ArrayList<String>> getRecipeIngredients(Recipe recipe) {
            getRecipeIngredientsCalled = true;
            if (throwException) {
                throw new RuntimeException("Database connection error");
            }
            return recipeIngredients;
        }

        @Override
        public ArrayList<String> getRecipeTags(Recipe recipe) throws Exception {
            // Not used in these tests - tags come from tagging service
            return new ArrayList<>();
        }

        // Getters for verification
        public boolean isGetRecipeInfoCalled() { return getRecipeInfoCalled; }
        public boolean isGetRecipeIngredientsCalled() { return getRecipeIngredientsCalled; }
    }

    /**
     * Mock implementation of AddTagDataAccessInterface for testing.
     */
    private static class MockTaggingDataAccess implements AddTagDataAccessInterface {
        private boolean getTagsForRecipeCalled = false;
        private boolean throwException = false;
        private List<String> tags = new ArrayList<>();

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        @Override
        public void addTagToRecipe(String username, int recipeId, String tagName) {
            // Not used in checkout tests
        }

        @Override
        public List<String> getTagsForRecipe(String username, int recipeId) {
            getTagsForRecipeCalled = true;
            if (throwException) {
                throw new RuntimeException("Tagging service error");
            }
            return new ArrayList<>(tags);
        }

        // Getters for verification
        public boolean isGetTagsForRecipeCalled() { return getTagsForRecipeCalled; }
    }

    /**
     * Mock implementation of CheckoutRecipeOutputBoundary for testing.
     */
    private static class MockCheckoutRecipePresenter implements CheckoutRecipeOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private CheckoutRecipeOutputData outputData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(CheckoutRecipeOutputData outputData) {
            successCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failureCalled = true;
            this.errorMessage = errorMessage;
        }

        // Getters for verification
        public boolean isSuccessCalled() { return successCalled; }
        public boolean isFailureCalled() { return failureCalled; }
        public CheckoutRecipeOutputData getOutputData() { return outputData; }
        public String getErrorMessage() { return errorMessage; }
    }
}
