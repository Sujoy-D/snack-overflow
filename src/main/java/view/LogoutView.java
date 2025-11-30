package view;

import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View layer for logout functionality.
 * This view handles the user interface concerns for logout,
 * including confirmation dialogs and presenting logout results to the user.
 */
public class LogoutView implements PropertyChangeListener {
    
    private final LogoutController logoutController;
    private final LogoutViewModel logoutViewModel;
    
    public LogoutView(LogoutController logoutController, LogoutViewModel logoutViewModel) {
        this.logoutController = logoutController;
        this.logoutViewModel = logoutViewModel;
        
        // Listen for changes in the view model
        this.logoutViewModel.addPropertyChangeListener(this);
    }
    
    /**
     * Initiates the logout process.
     * Shows confirmation dialog and executes logout if confirmed.
     * 
     * @param parentComponent The parent component for dialog positioning
     * @param username The username of the user to log out
     */
    public void initiateLogout(java.awt.Component parentComponent, String username) {
        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(
            parentComponent,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            logoutController.execute(username);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("error".equals(evt.getPropertyName())) {
            String errorMessage = (String) evt.getNewValue();
            if (errorMessage != null && !errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "Logout failed: " + errorMessage,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
