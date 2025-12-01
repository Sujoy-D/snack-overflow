package interface_adapter.search;

import org.junit.jupiter.api.Test;
import use_case.search.SearchFilters;
import use_case.search.SearchRecipesInputBoundary;
import use_case.search.SearchRecipesInputData;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SearchControllerTest {
    
    @Test
    void searchMarksViewModelSearchingAndDispatchesInput() throws Exception {
        SearchViewModel viewModel = new SearchViewModel();
        CountDownLatch executed = new CountDownLatch(1);
        RecordingInteractor interactor = new RecordingInteractor(executed);
        SearchController controller = new SearchController(interactor, viewModel, 5);
        
        SearchFilters filters = new SearchFilters(null, "vegan", java.util.List.of(), null, null);
        controller.search("apples, flour", filters);
        
        assertTrue(viewModel.isSearching(), "ViewModel should be marked searching immediately");
        assertTrue(executed.await(2, TimeUnit.SECONDS), "Interactor should be executed in a worker thread");
        assertNotNull(interactor.lastInput, "Interactor should receive input");
        assertEquals("apples, flour", interactor.lastInput.getIngredientsCsv());
        assertEquals(5, interactor.lastInput.getNumberOfResults());
        assertEquals("vegan", interactor.lastInput.getFilters().getDiet());
    }
    
    private static class RecordingInteractor implements SearchRecipesInputBoundary {
        private final CountDownLatch latch;
        volatile SearchRecipesInputData lastInput;
        
        RecordingInteractor(CountDownLatch latch) {
            this.latch = latch;
        }
        
        @Override
        public void execute(SearchRecipesInputData inputData) {
            lastInput = inputData;
            latch.countDown();
        }
    }
}
