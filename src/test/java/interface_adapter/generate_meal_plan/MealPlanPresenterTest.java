package interface_adapter.generate_meal_plan;

import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import org.junit.jupiter.api.Test;
import use_case.generate_meal_plan.MealPlanOutputData;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MealPlanPresenterTest {

    /**
     * Mock ViewModel to check state updates
     * */
    static class MockMealPlanViewModel extends MealPlanViewModel {
        MealPlanState receivedState = null;
        boolean fired = false;

        @Override
        public void setState(MealPlanState state) {
            this.receivedState = state;
        }

        @Override
        public void firePropertyChanged() {
            fired = true;
        }
    }

    @Test
    void testPrepareSuccessView() {
        MockMealPlanViewModel viewModel = new MockMealPlanViewModel();
        MealPlanPresenter presenter = new MealPlanPresenter(viewModel);

        Ingredient ing = new Ingredient("Tomato", "1", "pc");
        Tag tag = new Tag(1, "vegan");

        Recipe r = new Recipe(
                1, List.of(ing), "Pasta",
                "Boil water", "Italian", 10,
                "Dinner", 1,
                List.of(tag)
        );
        Map<String, List<Recipe>> plan = Map.of("monday", List.of(r));

        MealPlanOutputData output = new MealPlanOutputData(plan, null);

        presenter.prepareSuccessView(output);

        assertNotNull(viewModel.receivedState);
        assertEquals(plan, viewModel.receivedState.getMealPlan());
        assertNull(viewModel.receivedState.getErrorMessage());
        assertTrue(viewModel.fired);
    }


    @Test
    void testPrepareFailView() {
        MockMealPlanViewModel viewModel = new MockMealPlanViewModel();
        MealPlanPresenter presenter = new MealPlanPresenter(viewModel);

        presenter.prepareFailView("Something went wrong");

        assertNotNull(viewModel.receivedState);
        assertEquals("Something went wrong", viewModel.receivedState.getErrorMessage());
        assertNull(viewModel.receivedState.getMealPlan());
        assertTrue(viewModel.fired);
    }
}
