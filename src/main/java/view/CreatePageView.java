package view;

import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;

    public class CreatePageView {
        public static JFrame show(String username, NavigationController navigationController) {
            JFrame frame = new JFrame("Snack Overflow - Create New");
            frame.setMinimumSize(new Dimension(720, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // Sidebar (same as HomePageView)
            SidebarView sidebar = new SidebarView(navigationController, username, frame);
            frame.add(sidebar, BorderLayout.WEST);

            // Main content panel
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(new Color(240, 235, 255));
            mainPanel.setLayout(new BorderLayout());

            // Embed AddRecipeForm panel here
            AddRecipeForm addRecipeForm = new AddRecipeForm();
            JPanel formPanel = addRecipeForm.getRecipeForm(username, navigationController);

            // Wrap in a scroll pane so it fits nicely if window is small
            JScrollPane scrollPane = new JScrollPane(formPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // optional: remove default border
            scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scrolling

            mainPanel.add(scrollPane, BorderLayout.CENTER);

            frame.add(mainPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setVisible(true);
            return frame;
        }
    }