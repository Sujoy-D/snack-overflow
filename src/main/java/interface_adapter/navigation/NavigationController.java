package interface_adapter.navigation;

import entity.Recipe;

public class NavigationController {
    private NavigationViewModel navigationViewModel;

    public NavigationController(NavigationViewModel navigationViewModel) {
        this.navigationViewModel = navigationViewModel;
    }

    public void execute(String targetPage, String username) {
        navigationViewModel.setCurrentPage(targetPage);
        navigationViewModel.setUsername(username);
        navigationViewModel.firePropertyChanged();
    }

    public void executeWithRecipe(String targetPage, String username, Recipe recipe) {
        navigationViewModel.setCurrentPage(targetPage);
        navigationViewModel.setUsername(username);
        navigationViewModel.setSelectedRecipe(recipe);

        navigationViewModel.firePropertyChanged();
    }
}