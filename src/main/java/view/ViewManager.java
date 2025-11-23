package view;

import data_access.MealPlanDataAccessInterface;
import data_access.SpoonacularMealPlanAPI;
import gateways.JavaHttpGateway;
import interface_adapter.generate_meal_plan.MealPlanController;
import interface_adapter.generate_meal_plan.MealPlanPresenter;
import interface_adapter.generate_meal_plan.MealPlanViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import use_case.generate_meal_plan.MealPlanInteractor;

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
                    MealPlanViewModel mealPlanViewModel = new MealPlanViewModel();
                    MealPlanPresenter presenter = new MealPlanPresenter(mealPlanViewModel);
                    MealPlanDataAccessInterface api = new SpoonacularMealPlanAPI(new JavaHttpGateway());
                    MealPlanInteractor interactor = new MealPlanInteractor(api, presenter);
                    MealPlanController controller = new MealPlanController(interactor);
                    MealPlanningPageView.show(
                            username,
                            navigationController,
                            controller,
                            mealPlanViewModel
                    );
                    break;
            }
        }
    }
}

