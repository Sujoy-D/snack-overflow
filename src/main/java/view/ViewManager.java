package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private NavigationViewModel navigationViewModel;
    private NavigationController navigationController;
    
    public ViewManager(NavigationViewModel navigationViewModel) {
        this.navigationViewModel = navigationViewModel;
        this.navigationViewModel.addPropertyChangeListener(this);
        this.navigationController = new NavigationController(navigationViewModel);
    }
    
    public NavigationController getNavigationController() {
        return navigationController;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String currentPage = navigationViewModel.getCurrentPage();
        String username = navigationViewModel.getUsername();
        
        if (currentPage != null && username != null) {
            switch (currentPage) {
                case "home":
                    HomePageView.show(username, navigationController);
                    break;
                case "search":
                    SearchPageView.show(username, navigationController);
                    break;
                case "saved":
                    SavedPageView.show(username, navigationController);
                    break;
                case "create":
                    CreatePageView.show(username, navigationController);
                    break;
                case "mealPlanning":
                    MealPlanningPageView.show(username, navigationController);
                    break;
            }
        }
    }
}

