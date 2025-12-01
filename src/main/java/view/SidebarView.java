package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.logout.LogoutPresenter;
import use_case.logout.LogoutInteractor;
import data_access.LogoutDataAccessObject;
import data_access.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidebarView extends JPanel {
    private NavigationController navigationController;
    private String username;
    private JFrame parentFrame;
    private LogoutView logoutView;
    
    public SidebarView(NavigationController navigationController, String username, JFrame parentFrame) {
        this.navigationController = navigationController;
        this.username = username;
        this.parentFrame = parentFrame;
        
        initializeLogoutComponents();
        initializeSidebar();
    }
    
    private void initializeLogoutComponents() {
        // Create logout dependencies using clean architecture
        UserRepository userRepository = new UserRepository();
        LogoutDataAccessObject logoutDataAccess = new LogoutDataAccessObject(userRepository);
        LogoutViewModel logoutViewModel = new LogoutViewModel();
        LogoutPresenter logoutPresenter = new LogoutPresenter(logoutViewModel, navigationController);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutDataAccess, logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        
        this.logoutView = new LogoutView(logoutController, logoutViewModel);
    }
    
    private void closeParent() {
        if (parentFrame != null) {
            parentFrame.dispose();
        }
    }
    
    private void initializeSidebar() {
        setBackground(new Color(138, 43, 226));
        setPreferredSize(new Dimension(200, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // profile button (leads to HomePage)
        JButton profileButton = createSidebarButton("Profile");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeParent();
                navigationController.execute("home", username);
            }
        });
        add(profileButton);
        add(Box.createVerticalStrut(10));
        
        // search button leads to SearchPage
        JButton searchButton = createSidebarButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeParent();
                navigationController.execute("search", username);
            }
        });
        add(searchButton);
        add(Box.createVerticalStrut(10));
        
        // saved button leads to SavedPage
        JButton savedButton = createSidebarButton("Saved");
        savedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeParent();
                navigationController.execute("saved", username);
            }
        });
        add(savedButton);
        add(Box.createVerticalStrut(10));
        
        // create new button leads to CreateNewPage
        JButton createNewButton = createSidebarButton("Create New");
        createNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeParent();
                navigationController.execute("create", username);
            }
        });
        add(createNewButton);
        add(Box.createVerticalStrut(10));
        
        // meal planning button leads to MealPlanningPage
        JButton mealPlanningButton = createSidebarButton("Meal Planning");
        mealPlanningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeParent();
                navigationController.execute("mealPlanning", username);
            }
        });
        add(mealPlanningButton);
        
        add(Box.createVerticalGlue());
        
        // Logout button at the bottom using clean architecture
        JButton logoutButton = createSidebarButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69)); // Red color for logout
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutView.initiateLogout(SidebarView.this, username);
                closeParent();
            }
        });
        add(logoutButton);
    }
    
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(147, 112, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setPreferredSize(new Dimension(180, 40));
        
        return button;
    }
    
}
