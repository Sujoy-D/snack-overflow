package use_case.similar_recipes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Comprehensive test suite for SimilarRecipesInteractor.
 * 
 * This test class verifies the business logic for finding similar recipes,
 * including successful retrieval and error handling.
 * 
 * Test Categories:
 * - Successful similar recipe retrieval
 * - System error handling (data access exceptions)
 * - Edge cases with different recipe ID types
 * 
 * All tests use mock implementations to isolate the unit under test.
 */
class SimilarRecipesInteractorTest {

    @Test
    void executeSuccessfulSimilarRecipesRetrieval() {
        // Given - Valid recipe ID with similar recipes available
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        Integer recipeId = 123;
        List<Integer> similarRecipeIds = Arrays.asList(456, 789, 101);
        
        dataAccess.setSimilarRecipeIds(similarRecipeIds);
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // When
        interactor.execute(inputData);

        // Then - Verify successful retrieval
        assertTrue(presenter.isSuccessCalled(), "Success presenter should be called");
        assertFalse(presenter.isFailureCalled(), "Failure presenter should not be called");
        
        SimilarRecipesOutputData outputData = presenter.getOutputData();
        assertNotNull(outputData, "Output data should not be null");
        assertEquals(similarRecipeIds, outputData.getSimilarRecipes());

        // Verify data access calls
        assertTrue(dataAccess.isGetSimilarRecipeIDCalled());
        assertEquals(recipeId, dataAccess.getRequestedRecipeId());
    }

    @Test
    void executeSuccessfulRetrievalWithNoSimilarRecipes() {
        // Given - Recipe ID with no similar recipes
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        Integer recipeId = 999;
        List<Integer> emptySimilarRecipeIds = Collections.emptyList();
        
        dataAccess.setSimilarRecipeIds(emptySimilarRecipeIds);
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // When
        interactor.execute(inputData);

        // Then - Should succeed with empty list
        assertTrue(presenter.isSuccessCalled());
        assertFalse(presenter.isFailureCalled());
        
        SimilarRecipesOutputData outputData = presenter.getOutputData();
        assertNotNull(outputData);
        assertTrue(outputData.getSimilarRecipes().isEmpty(), 
                  "Should return empty list when no similar recipes found");
    }

    @Test
    void executeSuccessfulRetrievalWithSingleSimilarRecipe() {
        // Given - Recipe ID with one similar recipe
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        Integer recipeId = 555;
        List<Integer> singleSimilarRecipe = Collections.singletonList(666);
        
        dataAccess.setSimilarRecipeIds(singleSimilarRecipe);
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // When
        interactor.execute(inputData);

        // Then - Should succeed with single recipe
        assertTrue(presenter.isSuccessCalled());
        
        SimilarRecipesOutputData outputData = presenter.getOutputData();
        assertEquals(1, outputData.getSimilarRecipes().size());
        assertEquals(666, (int) outputData.getSimilarRecipes().get(0));
    }

    @Test
    void executeSuccessfulRetrievalWithLargeSimilarRecipeList() {
        // Given - Recipe ID with many similar recipes
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        Integer recipeId = 777;
        List<Integer> manySimilarRecipes = new ArrayList<>();
        for (int i = 1000; i < 1020; i++) {
            manySimilarRecipes.add(i);
        }
        
        dataAccess.setSimilarRecipeIds(manySimilarRecipes);
        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // When
        interactor.execute(inputData);

        // Then - Should handle large lists
        assertTrue(presenter.isSuccessCalled());
        
        SimilarRecipesOutputData outputData = presenter.getOutputData();
        assertEquals(20, outputData.getSimilarRecipes().size());
        assertEquals(manySimilarRecipes, outputData.getSimilarRecipes());
    }

    @Test
    void executeHandlesDataAccessException() {
        // Given - Data access that throws exception
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        dataAccess.setThrowException(true);
        Integer recipeId = 123;

        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // When
        interactor.execute(inputData);

        // Then - Should handle exception gracefully
        assertTrue(presenter.isFailureCalled(), "Failure presenter should be called");
        assertFalse(presenter.isSuccessCalled(), "Success presenter should not be called");
        
        String errorMessage = presenter.getErrorMessage();
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Failed to get similar recipes:"));
        assertTrue(errorMessage.contains("Database connection error"));
    }

    @Test
    void executeHandlesNullPointerException() {
        // Given - Data access that throws NullPointerException
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        dataAccess.setThrowNullPointerException(true);
        Integer recipeId = 123;

        SimilarRecipesInputData inputData = new SimilarRecipesInputData(recipeId);

        // When
        interactor.execute(inputData);

        // Then - Should handle null pointer exception
        assertTrue(presenter.isFailureCalled());
        assertFalse(presenter.isSuccessCalled());
        
        String errorMessage = presenter.getErrorMessage();
        assertTrue(errorMessage.contains("Failed to get similar recipes:"));
        assertTrue(errorMessage.contains("Null value encountered"));
    }

    @Test
    void executeWithEdgeCaseRecipeIds() {
        // Given - Edge case recipe IDs
        MockSimilarRecipeDataAccess dataAccess = new MockSimilarRecipeDataAccess();
        MockSimilarRecipesPresenter presenter = new MockSimilarRecipesPresenter();
        SimilarRecipesInteractor interactor = new SimilarRecipesInteractor(dataAccess, presenter);

        // Test with very large recipe ID
        Integer largeRecipeId = Integer.MAX_VALUE;
        List<Integer> similarIds = Arrays.asList(1, 2, 3);
        dataAccess.setSimilarRecipeIds(similarIds);

        SimilarRecipesInputData inputData = new SimilarRecipesInputData(largeRecipeId);

        // When
        interactor.execute(inputData);

        // Then - Should handle edge case IDs
        assertTrue(presenter.isSuccessCalled());
        assertEquals(largeRecipeId, dataAccess.getRequestedRecipeId());
        assertEquals(similarIds, presenter.getOutputData().getSimilarRecipes());
    }

    /**
     * Mock implementation of SimilarRecipeDataAccessInterface for testing.
     */
    private static class MockSimilarRecipeDataAccess implements SimilarRecipeDataAccessInterface {
        private boolean getSimilarRecipeIDCalled = false;
        private boolean throwException = false;
        private boolean throwNullPointerException = false;
        private List<Integer> similarRecipeIds = new ArrayList<>();
        private Integer requestedRecipeId;

        public void setSimilarRecipeIds(List<Integer> similarRecipeIds) {
            this.similarRecipeIds = similarRecipeIds;
        }

        public void setThrowException(boolean throwException) {
            this.throwException = throwException;
        }

        public void setThrowNullPointerException(boolean throwNullPointerException) {
            this.throwNullPointerException = throwNullPointerException;
        }

        @Override
        public ArrayList<Integer> getSimilarRecipeID(int recipeId) throws Exception {
            getSimilarRecipeIDCalled = true;
            requestedRecipeId = recipeId;
            
            if (throwException) {
                throw new Exception("Database connection error");
            }
            if (throwNullPointerException) {
                throw new NullPointerException("Null value encountered");
            }
            
            return new ArrayList<>(similarRecipeIds);
        }

        // Getters for verification
        public boolean isGetSimilarRecipeIDCalled() { return getSimilarRecipeIDCalled; }
        public Integer getRequestedRecipeId() { return requestedRecipeId; }
    }

    /**
     * Mock implementation of SimilarRecipesOutputBoundary for testing.
     */
    private static class MockSimilarRecipesPresenter implements SimilarRecipesOutputBoundary {
        private boolean successCalled = false;
        private boolean failureCalled = false;
        private SimilarRecipesOutputData outputData;
        private String errorMessage;

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
