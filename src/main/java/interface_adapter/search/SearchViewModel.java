package interface_adapter.search;

import entity.Recipe;
import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel holding search results and status for the Search view
 */
public class SearchViewModel extends ViewModel {
    private List<Recipe> recipes = new ArrayList<>();
    private String errorMessage;
    private boolean searching;
    
    public SearchViewModel() {
        super("search");
    }
    
    public List<Recipe> getRecipes() {
        return recipes;
    }
    
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes != null ? recipes : new ArrayList<>();
        firePropertyChanged();
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        firePropertyChanged();
    }
    
    public boolean isSearching() {
        return searching;
    }
    
    public void setSearching(boolean searching) {
        this.searching = searching;
        firePropertyChanged();
    }
}
