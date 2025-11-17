package view;

import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;

public class MealPlanningPageView {
    public static void show(String username, NavigationController navigationController) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snack Overflow - Meal Planning");
            frame.setMinimumSize(new Dimension(720, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            
            frame.setLayout(new BorderLayout());
            
            // Create sidebar using shared component
            SidebarView sidebar = new SidebarView(navigationController, username, frame);
            frame.add(sidebar, BorderLayout.WEST);
            
            // main content area
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(new Color(240, 235, 255));
            mainPanel.setLayout(new BorderLayout());
            
            frame.add(mainPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

