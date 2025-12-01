package interface_adapter.search;

import entity.Ingredient;
import entity.Recipe;
import entity.RecipeFactory;
import org.junit.jupiter.api.Test;
import use_case.search.SearchRecipesOutputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchPresenterTest {
    
    private final Recipe sampleRecipe = new RecipeFactory().create(
            1,
            "Apple Pie",
            List.of(new Ingredient("apple", "", "")),
            "",
            null,
            30,
            null,
            null
    );
    
    @Test
    void presentSuccessUpdatesViewModel() {
        SearchViewModel viewModel = new SearchViewModel();
        SearchPresenter presenter = new SearchPresenter(viewModel);
        
        presenter.presentSuccess(new SearchRecipesOutputData(List.of(sampleRecipe)));
        
        assertFalse(viewModel.isSearching(), "Searching flag should turn off on success");
        assertNull(viewModel.getErrorMessage(), "No error expected on success");
        assertEquals(1, viewModel.getRecipes().size(), "Recipes should be populated on success");
        assertEquals("Apple Pie", viewModel.getRecipes().get(0).getTitle());
    }
    
    @Test
    void presentFailureClearsRecipesAndSetsError() {
        SearchViewModel viewModel = new SearchViewModel();
        viewModel.setRecipes(List.of(sampleRecipe));
        SearchPresenter presenter = new SearchPresenter(viewModel);
        
        presenter.presentFailure("Boom");
        
        assertFalse(viewModel.isSearching(), "Searching flag should turn off on failure");
        assertEquals("Boom", viewModel.getErrorMessage());
        assertTrue(viewModel.getRecipes().isEmpty(), "Recipes should be cleared on failure");
    }
}
