package use_case.similar_recipes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the Similar Recipes Use Case Interactor.
 */
class SimilarRecipesInteractorTest {

    @Test
    void executeSuccessfulSimilarRecipes() {
        MockSimilarRecipesDataAccessObject dataAccessObject =
                new MockSimilarRecipesDataAccessObject(new MockJavaHttpGateway());
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccessObject, presenter);

        // Valid recipe ID from Spoonacular API
        int recipeId = 0;
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // Execute the interactor
        interactor.execute(inputData);

        assertTrue(presenter.isSuccessCalled(), "Success should be called with a valid recipe ID.");
        assertFalse(presenter.isFailureCalled(), "Failure should not be called with a valid recipe ID.");

        String errorMessage = presenter.getErrorMessage();

        // Error message should not be changed as no error occurred
        assertNull(errorMessage);

        List<Integer> similarRecipes = presenter.getOutputData().getSimilarRecipes();

        List<Integer> expectedSimilarRecipes =
                List.of(1);

        assertTrue(similarRecipes.containsAll(expectedSimilarRecipes));
    }

    @Test
    void executeHandlesInvalidRecipeId() {
        MockSimilarRecipesDataAccessObject dataAccessObject =
                new MockSimilarRecipesDataAccessObject(new MockJavaHttpGateway());
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccessObject, presenter);

        // Invalid recipe ID
        int recipeId = -1;
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // Execute the interactor
        interactor.execute(inputData);

        assertTrue(presenter.isFailureCalled(), "Failure should be called with an invalid recipe ID.");
        assertFalse(presenter.isSuccessCalled(), "Failure should not be called with an invalid recipe ID.");

        String errorMessage = presenter.getErrorMessage();

        // Error message should not be changed as no error occurred
        assertTrue(errorMessage.contains("Failed to get similar recipes: "));
    }

    /**
     * Mock implementation of SimilarRecipesDataAccessInterface for testing.
     */
    private static class MockSimilarRecipesDataAccessObject implements SimilarRecipesDataAccessInterface {
        private final MockJavaHttpGateway httpGateway;

        public MockSimilarRecipesDataAccessObject(MockJavaHttpGateway httpGateway) {
            this.httpGateway = httpGateway;
        }

        public ArrayList<Integer> getSimilarRecipeID(int recipeId) {
            String response = httpGateway.getSimilarRecipesJSON(recipeId);
            final JSONArray responseBody = new JSONArray(response);

            ArrayList<Integer> similarRecipeID = null;

            if (!responseBody.isEmpty()) {
                similarRecipeID = new ArrayList<>();
                for (int i = 0; i < responseBody.length(); i++) {
                    JSONObject recipe = responseBody.getJSONObject(i);
                    similarRecipeID.add(recipe.getInt("id"));
                }
            }
            return similarRecipeID;
        }
    }

    /**
     * Mock implementation of JavaHttpGateway for faster, non-API-dependent testing.
     */
    private static class MockJavaHttpGateway {
        public String getSimilarRecipesJSON(int recipeId) {

            StringBuilder similarRecipes = new StringBuilder("[");

            // General logic: a recipe can have any non-negative ID.
            if (recipeId >= 0) {
                similarRecipes.append("{\"id\":1,\"image\":recipe.jpg,\"imageType\":\"jpg\","
                        + "\"title\":\"Sliced Fruit\",\"readyInMinutes\":5,\"servings\":1}");
            }
            similarRecipes.append("]");
            return similarRecipes.toString();
        }
    }

    /**
     * Mock implementation of SimilarRecipesOutputBoundary for testing.
     */
    private static class MockSimilarRecipesPresenter implements SimilarRecipesOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private SimilarRecipesOutputData outputData;
        private String errorMessage = null;

        @Override
        public void prepareSuccessView(SimilarRecipesOutputData outputData) {
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
        public SimilarRecipesOutputData getOutputData() { return outputData; }
        public String getErrorMessage() { return errorMessage; }
    }
}
