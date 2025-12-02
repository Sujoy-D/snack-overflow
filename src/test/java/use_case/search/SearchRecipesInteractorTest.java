package use_case.search;

import entity.Ingredient;
import entity.Recipe;
import entity.RecipeFactory;
import org.junit.jupiter.api.Test;
import use_case.search.SearchRecipesOutputBoundary;
import use_case.search.SearchRecipesOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchRecipesInteractorTest {
    
    @Test
    void executesHappyPathWithNormalizedIngredients() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);
        
        SearchFilters filters = new SearchFilters(30, null, List.of(), null, null);
        interactor.execute(new SearchRecipesInputData(" Apples , FLOUR ", 3, filters));
        
        assertEquals("apples,flour", gateway.lastIngredientsCsv);
        assertEquals(3, gateway.lastNumber);
        assertEquals(filters, gateway.lastFilters);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failureCalled);
    }
    
    @Test
    void failsWhenNoIngredientsAndNoFilters() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);
        
        interactor.execute(new SearchRecipesInputData("   ", 2, new SearchFilters(null, null, List.of(), null, null)));
        
        assertTrue(presenter.failureCalled);
        assertEquals("Add ingredients or filters to search.", presenter.errorMessage);
        assertFalse(presenter.successCalled);
        assertNull(gateway.lastIngredientsCsv, "Gateway should not be hit on validation failure");
    }
    
    @Test
    void failsOnConflictingIngredientsAndAllergens() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);
        
        SearchFilters filters = new SearchFilters(null, null, List.of("apple"), null, null);
        interactor.execute(new SearchRecipesInputData("apple, flour", 3, filters));
        
        assertTrue(presenter.failureCalled);
        assertEquals("Conflicting filters: ingredients to include overlap with exclusions.", presenter.errorMessage);
        assertNull(gateway.lastIngredientsCsv, "Gateway should not be hit on validation failure");
    }
    
    @Test
    void failsOnNonPositiveMaxCookingTime() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);
        
        SearchFilters filters = new SearchFilters(0, null, List.of(), null, null);
        interactor.execute(new SearchRecipesInputData("apple", 3, filters));
        
        assertTrue(presenter.failureCalled);
        assertEquals("Maximum cooking time must be a positive number.", presenter.errorMessage);
        assertNull(gateway.lastIngredientsCsv);
    }

    @Test
    void failsWhenIngredientsAndFiltersAreNull() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);

        interactor.execute(new SearchRecipesInputData(null, 1, null));

        assertTrue(presenter.failureCalled);
        assertEquals("Add ingredients or filters to search.", presenter.errorMessage);
        assertFalse(presenter.successCalled);
        assertNull(gateway.lastIngredientsCsv);
    }

    @Test
    void succeedsWithNullFilters() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);

        interactor.execute(new SearchRecipesInputData("Tomato", 2, null));

        assertEquals("tomato", gateway.lastIngredientsCsv);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failureCalled);
    }

    @Test
    void skipsEmptyIngredientSegments() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);

        SearchFilters filters = new SearchFilters(null, null, List.of("peanut"), null, null);
        interactor.execute(new SearchRecipesInputData("Apple,, FLOUR, , ", 4, filters));

        assertEquals("apple,flour", gateway.lastIngredientsCsv);
        assertTrue(presenter.successCalled);
    }

    @Test
    void usesFiltersWhenIngredientsAreBlank() {
        RecordingGateway gateway = new RecordingGateway();
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);

        SearchFilters filters = new SearchFilters(null, null, List.of("peanut"), null, null);
        interactor.execute(new SearchRecipesInputData("   ,  ", 1, filters));

        assertEquals("", gateway.lastIngredientsCsv);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failureCalled);
    }

    @Test
    void returnsEmptyListWhenGatewayReturnsNull() {
        RecordingPresenter presenter = new RecordingPresenter();
        NullReturningGateway gateway = new NullReturningGateway();
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);

        interactor.execute(new SearchRecipesInputData("apple", 2, null));

        assertTrue(gateway.called);
        assertTrue(presenter.successCalled);
        assertNotNull(presenter.lastOutputData);
        assertTrue(presenter.lastOutputData.getRecipes().isEmpty());
    }

    @Test
    void reportsGatewayExceptionsAsFailures() {
        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipesGateway gateway = (ingredientsCsv, filters, numberOfResults) -> {
            throw new RuntimeException("boom");
        };
        SearchRecipesInteractor interactor = new SearchRecipesInteractor(gateway, presenter);

        interactor.execute(new SearchRecipesInputData(null, 2, new SearchFilters(null, null, List.of("nut"), null, null)));

        assertTrue(presenter.failureCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Unable to fetch recipes: boom", presenter.errorMessage);
    }
    
    private static class RecordingGateway implements SearchRecipesGateway {
        String lastIngredientsCsv;
        SearchFilters lastFilters;
        int lastNumber;
        
        @Override
        public List<Recipe> searchRecipes(String ingredientsCsv, SearchFilters filters, int numberOfResults) {
            this.lastIngredientsCsv = ingredientsCsv;
            this.lastFilters = filters;
            this.lastNumber = numberOfResults;
            Recipe recipe = new RecipeFactory().create(1,
                    "Sample",
                    List.of(new Ingredient("apple", "", "")),
                    "",
                    null,
                    10,
                    null,
                    null);
            return new ArrayList<>(List.of(recipe));
        }
    }

    private static class NullReturningGateway implements SearchRecipesGateway {
        boolean called;

        @Override
        public List<Recipe> searchRecipes(String ingredientsCsv, SearchFilters filters, int numberOfResults) {
            called = true;
            return null;
        }
    }
    
    private static class RecordingPresenter implements SearchRecipesOutputBoundary {
        boolean successCalled;
        boolean failureCalled;
        String errorMessage;
        SearchRecipesOutputData lastOutputData;
        
        @Override
        public void presentSuccess(SearchRecipesOutputData outputData) {
            successCalled = true;
            this.lastOutputData = outputData;
        }
        
        @Override
        public void presentFailure(String errorMessage) {
            failureCalled = true;
            this.errorMessage = errorMessage;
        }
    }
}
