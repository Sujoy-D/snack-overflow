package interface_adapter.generate_meal_plan;

import org.junit.jupiter.api.Test;
import use_case.generate_meal_plan.MealPlanInputBoundary;
import use_case.generate_meal_plan.MealPlanInputData;

import static org.junit.jupiter.api.Assertions.*;

class MealPlanControllerTest {

    /**
     * Fake controller
     */
    static class MockInteractor implements MealPlanInputBoundary {

        MealPlanInputData received = null;

        @Override
        public void execute(MealPlanInputData data) {
            this.received = data;
        }
    }

    @Test
    void testControllerPassesCorrectInputData() {

        MockInteractor mock = new MockInteractor();
        MealPlanController controller = new MealPlanController(mock);

        controller.execute("Vegan", "Low", 2);

        assertNotNull(mock.received);
        assertEquals("Vegan", mock.received.getDiet());
        assertEquals("Low", mock.received.getCalorieLevel());
        assertEquals(2, mock.received.getMealsPerDay());
    }
}
