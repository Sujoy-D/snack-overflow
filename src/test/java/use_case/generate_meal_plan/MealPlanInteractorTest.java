package use_case.generate_meal_plan;

import org.junit.jupiter.api.Test;
import use_case.generate_meal_plan.*;
import entity.Recipe;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MealPlanInteractorTest {

    @Test
    void executesHappyPathSuccessfully() throws Exception {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        MealPlanInteractor interactor = new MealPlanInteractor(gateway, presenter);

        MealPlanInputData inputData = new MealPlanInputData("Vegan", "Low", 3);

        interactor.execute(inputData);

        // Gateway should receive correct params
        assertEquals("Vegan", gateway.lastDiet);
        assertEquals("Low", gateway.lastCalorieLevel);
        assertEquals(3, gateway.lastMealsPerDay);

        // Presenter should be called success
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failureCalled);

        // Weekly plan should be passed through
        assertNotNull(presenter.lastOutput);
        assertEquals(gateway.fakePlan, presenter.lastOutput.getWeeklyPlan());
    }

    @Test
    void failsWhenApiThrowsException() throws Exception {
        RecordingFailingGateway gateway = new RecordingFailingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        MealPlanInteractor interactor = new MealPlanInteractor(gateway, presenter);

        MealPlanInputData input = new MealPlanInputData("Keto", "High", 2);

        interactor.execute(input);

        // Should call fail view
        assertTrue(presenter.failureCalled);
        assertFalse(presenter.successCalled);

        assertNotNull(presenter.errorMessage);
        assertTrue(presenter.errorMessage.contains("Failed to load meal plan"));
        assertTrue(presenter.errorMessage.contains("boom"));

        // Gateway should have been called
        assertEquals("Keto", gateway.lastDiet);
        assertEquals("High", gateway.lastCalorieLevel);
        assertEquals(2, gateway.lastMealsPerDay);
    }


    // --------------------------
    // Recording Fakes
    // --------------------------

    private static class RecordingGateway implements MealPlanDataAccessInterface {

        String lastDiet;
        String lastCalorieLevel;
        int lastMealsPerDay;

        Map<String, List<Recipe>> fakePlan = Map.of(
                "Monday", List.of(),
                "Tuesday", List.of()
        );

        @Override
        public Map<String, List<Recipe>> generateWeeklyMealPlan(
                String diet, String calorieLevel, int mealsPerDay
        ) {
            this.lastDiet = diet;
            this.lastCalorieLevel = calorieLevel;
            this.lastMealsPerDay = mealsPerDay;
            return fakePlan;
        }

        @Override public void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan) {}
        @Override public Map<String, List<Recipe>> loadMealPlan(String username) { return null; }
    }

    private static class RecordingFailingGateway implements MealPlanDataAccessInterface {

        String lastDiet;
        String lastCalorieLevel;
        int lastMealsPerDay;

        @Override
        public Map<String, List<Recipe>> generateWeeklyMealPlan(
                String diet, String calorieLevel, int mealsPerDay
        ) {
            this.lastDiet = diet;
            this.lastCalorieLevel = calorieLevel;
            this.lastMealsPerDay = mealsPerDay;
            throw new RuntimeException("boom");
        }

        @Override public void saveMealPlan(String username, Map<String, List<Recipe>> mealPlan) {}
        @Override public Map<String, List<Recipe>> loadMealPlan(String username) { return null; }
    }

    private static class RecordingPresenter implements MealPlanOutputBoundary {

        boolean successCalled;
        boolean failureCalled;

        MealPlanOutputData lastOutput;
        String errorMessage;

        @Override
        public void prepareSuccessView(MealPlanOutputData outputData) {
            successCalled = true;
            this.lastOutput = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failureCalled = true;
            this.errorMessage = errorMessage;
        }
    }
}