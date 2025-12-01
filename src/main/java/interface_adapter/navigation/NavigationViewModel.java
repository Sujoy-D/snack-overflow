package interface_adapter.navigation;

import entity.Recipe;
import interface_adapter.ViewModel;

public class NavigationViewModel extends ViewModel {
    private String currentPage;
    private String username;
    private Recipe selectedRecipe; // For passing recipe data between views

    public NavigationViewModel() {
        super("navigation");
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
        // Don't fire property change here automatically - let the controller decide when
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        firePropertyChanged();
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    public void setSelectedRecipe(Recipe selectedRecipe) {

        this.selectedRecipe = selectedRecipe;
        // Don't fire property change when just setting recipe data
    }
}