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
    
    private static class RecordingPresenter implements SearchRecipesOutputBoundary {
        boolean successCalled;
        boolean failureCalled;
        String errorMessage;
        
        @Override
        public void presentSuccess(SearchRecipesOutputData outputData) {
            successCalled = true;
        }
        
        @Override
        public void presentFailure(String errorMessage) {
            failureCalled = true;
            this.errorMessage = errorMessage;
        }
    }
}
