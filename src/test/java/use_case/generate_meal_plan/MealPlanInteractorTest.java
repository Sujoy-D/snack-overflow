package use_case.generate_meal_plan;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MealPlanInteractor.
 * Tests the business logic for meal plan generation functionality,
 * including success scenarios and failure handling.
 */
class MealPlanInteractorTest {

    /**
     * Mock implementation of MealPlanOutputBoundary for testing.
     * Captures output data and error messages for verification.
     */
    static class MockPresenter implements MealPlanOutputBoundary {
        MealPlanOutputData successData = null;
        String failMessage = null;

        @Override
        public void prepareSuccessView(MealPlanOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failMessage = errorMessage;
        }
    }


    /**
     * Mock implementation of MealPlanDataAccessInterface that simulates successful API responses.
     * Returns predefined meal plan data for testing success scenarios.
     */
    static class MockAPISuccess implements MealPlanDataAccessInterface {
        private final Map<String, List<Recipe>> plan;

        public MockAPISuccess(Map<String, List<Recipe>> plan) {
            this.plan = plan;
        }

        @Override
        public Map<String, List<Recipe>> generateWeeklyMealPlan(
                String diet, String calorieLevel, int mealsPerDay
        ) {
            return plan;
        }

        @Override
        public Map<String, List<Recipe>> loadMealPlan(String id) {
            return plan; // or null; depending on real behavior
        }

        @Override
        public void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan) {
            // Mock implementation - do nothing
        }
    }



    /**
     * Mock implementation of MealPlanDataAccessInterface that simulates API failures.
     * Throws RuntimeException for all methods to test error handling.
     */
    static class MockAPIFailure implements MealPlanDataAccessInterface {

        @Override
        public Map<String, List<Recipe>> generateWeeklyMealPlan(
                String diet, String calorieLevel, int mealsPerDay
        ) {
            throw new RuntimeException("API fail");
        }

        @Override
        public Map<String, List<Recipe>> loadMealPlan(String id) {
            throw new RuntimeException("API fail");
        }

        @Override
        public void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan) {
            throw new RuntimeException("API fail");
        }
    }



    /**
     * Test successful meal plan generation.
     * Verifies that when the API returns valid data, the interactor
     * processes it correctly and calls the presenter with success data.
     */
    @Test
    void testInteractorSuccessFlow() {

        // Valid Ingredient + Tag
        Ingredient ing = new Ingredient("Tomato", "1", "pc");
        Tag tag = new Tag(1, "vegan");

        Recipe r = new Recipe(
                1,
                List.of(ing),
                "Pasta",
                "Boil water",
                "Italian",
                10,
                "Dinner",
                1,
                List.of(tag)
        );

        Map<String, List<Recipe>> mockPlan = new HashMap<>();
        mockPlan.put("monday", List.of(r));

        MockAPISuccess api = new MockAPISuccess(mockPlan);
        MockPresenter presenter = new MockPresenter();

        MealPlanInteractor interactor = new MealPlanInteractor(api, presenter);

        MealPlanInputData input = new MealPlanInputData("None", "Low", 1);

        interactor.execute(input);

        assertNotNull(presenter.successData);
        assertEquals(mockPlan, presenter.successData.getWeeklyPlan());
        assertNull(presenter.failMessage);
    }


    /**
     * Test meal plan generation failure handling.
     * Verifies that when the API throws an exception, the interactor
     * handles it gracefully and calls the presenter with error information.
     */
    @Test
    void testInteractorFailureFlow() {

        MockAPIFailure api = new MockAPIFailure();
        MockPresenter presenter = new MockPresenter();

        MealPlanInteractor interactor = new MealPlanInteractor(api, presenter);

        MealPlanInputData input = new MealPlanInputData("None", "Low", 1);

        interactor.execute(input);

        assertNull(presenter.successData);
        assertNotNull(presenter.failMessage);
        assertTrue(presenter.failMessage.contains("Failed to load meal plan"));
    }
}
