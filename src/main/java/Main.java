import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;
import view.ViewManager;

import javax.swing.*;

/**
 * Main entry point for the Snack Overflow application.
 * This class sets up the ViewManager which handles all Clean Architecture dependencies
 * and navigation between different views (login, signup, home, etc.).
 */
public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create navigation system - this handles all Clean Architecture wiring
            NavigationViewModel navigationViewModel = new NavigationViewModel();
            ViewManager viewManager = new ViewManager(navigationViewModel);
            NavigationController navigationController = viewManager.getNavigationController();
            
            // Start the application with login page
            // ViewManager will create the login page with all its Clean Architecture dependencies
            navigationController.execute("login", null);
        });
    }
}
