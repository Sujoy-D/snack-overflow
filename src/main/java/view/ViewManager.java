package view;

import gateways.SpoonacularMealPlanAPI;
import gateways.JavaHttpGateway;
import interface_adapter.generate_meal_plan.MealPlanController;
import interface_adapter.generate_meal_plan.MealPlanPresenter;
import interface_adapter.generate_meal_plan.MealPlanViewModel;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignUpController;
import interface_adapter.signup.SignUpPresenter;
import interface_adapter.signup.SignUpViewModel;
import use_case.generate_meal_plan.MealPlanInteractor;
import use_case.generate_meal_plan.MealPlanDataAccessInterface;
import use_case.login.LoginInteractor;
import use_case.signup.SignUpInteractor;
import data_access.LoginDataAccessObject;
import data_access.SignUpDataAccessObject;
import data_access.UserRepository;
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
        
        if (currentPage != null) {
            if (currentFrame != null) {
                currentFrame.dispose();
                currentFrame = null;
            }
            switch (currentPage) {
                case "login":
                    // Create login dependencies and show login page
                    UserRepository loginUserRepo = new UserRepository();
                    LoginDataAccessObject loginDataAccess = new LoginDataAccessObject(loginUserRepo);
                    LoginViewModel loginViewModel = new LoginViewModel();
                    LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, navigationController);
                    LoginInteractor loginInteractor = new LoginInteractor(loginDataAccess, loginPresenter);
                    LoginController loginController = new LoginController(loginInteractor);
                    currentFrame = LoginPageView.show(loginViewModel, loginController, navigationController);
                    break;
                case "signup":
                    // Create signup dependencies and show signup page
                    UserRepository signupUserRepo = new UserRepository();
                    SignUpDataAccessObject signUpDataAccess = new SignUpDataAccessObject(signupUserRepo);
                    SignUpViewModel signUpViewModel = new SignUpViewModel();
                    SignUpPresenter signUpPresenter = new SignUpPresenter(signUpViewModel, navigationController);
                    SignUpInteractor signUpInteractor = new SignUpInteractor(signUpDataAccess, signUpPresenter);
                    SignUpController signUpController = new SignUpController(signUpInteractor);
                    currentFrame = SignUpPageView.show(signUpViewModel, signUpController, navigationController);
                    break;
            }
            
            // Pages that require a username
            if (username != null) {
                switch (currentPage) {
                case "home":
                    currentFrame = HomePageView.show(username, navigationController);
                    break;
                case "search":
                    currentFrame = SearchPageView.show(username, navigationController);
                    break;
                case "saved":
                    currentFrame = SavedPageView.show(username, navigationController);
                    break;
                case "create":
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
                }
            }
        }
    }
    

}
