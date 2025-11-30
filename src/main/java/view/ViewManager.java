package view;

import gateways.SpoonacularMealPlanAPI;
import gateways.JavaHttpGateway;
import interface_adapter.generate_meal_plan.MealPlanController;
import interface_adapter.generate_meal_plan.MealPlanPresenter;
import interface_adapter.generate_meal_plan.MealPlanViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import use_case.generate_meal_plan.MealPlanInteractor;
import use_case.generate_meal_plan.MealPlanDataAccessInterface;
import javax.swing.JFrame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private NavigationViewModel navigationViewModel;
    private NavigationController navigationController;
    private JFrame currentFrame;

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
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                    currentFrame = HomePageView.show(username, navigationController);
                    break;
                case "search":
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                    currentFrame = SearchPageView.show(username, navigationController);
                    break;
                case "saved":
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                    currentFrame = SavedPageView.show(username, navigationController);
                    break;
                case "create":
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                    currentFrame = CreatePageView.show(username, navigationController);
                    break;
                case "mealPlanning":
                    MealPlanViewModel mealPlanViewModel = new MealPlanViewModel();
                    MealPlanPresenter presenter = new MealPlanPresenter(mealPlanViewModel);
                    MealPlanDataAccessInterface api = new SpoonacularMealPlanAPI(new JavaHttpGateway());
                    MealPlanInteractor interactor = new MealPlanInteractor(api, presenter);
                    MealPlanController controller = new MealPlanController(interactor);
                    currentFrame = MealPlanningPageView.show(
                            username,
                            navigationController,
                            controller,
                            mealPlanViewModel
                    );
                    break;
                case "checkoutRecipe":
                    // Get the selected recipe from navigation data
                    entity.Recipe selectedRecipe = navigationViewModel.getSelectedRecipe();

                    if (selectedRecipe != null) {
                        CheckoutRecipeView.show(username, navigationController, selectedRecipe);
                    }

                    navigationViewModel.setSelectedRecipe(null);
                    break;
            }
        }
    }
}
