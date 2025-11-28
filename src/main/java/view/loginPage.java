package view;

import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import data_access.UserRepository;


public class loginPage {
    private static final UserRepository userRepository = new UserRepository();
    private static ViewManager viewManager;
    
    // Show themed message dialog
    private static void showThemedMessage(Component parent, String message, String title, int messageType) {
        UIManager.put("OptionPane.background", new Color(240, 235, 255));
        UIManager.put("Panel.background", new Color(240, 235, 255));
        UIManager.put("OptionPane.messageForeground", new Color(75, 0, 130));
        UIManager.put("Button.background", new Color(138, 43, 226));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new Color(147, 112, 219));
        
        JOptionPane.showMessageDialog(parent, message, title, messageType);
        
        // Reset UI defaults
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.focus", null);
    }
    
    // Navigate to home page
    private static void goToHomePage(String username, JFrame loginFrame) {
        loginFrame.dispose();
        
        // initialize ViewManager if not already initialized
        if (viewManager == null) {
            NavigationViewModel navigationViewModel = new NavigationViewModel();
            viewManager = new ViewManager(navigationViewModel);
        }
        
        NavigationController navigationController = viewManager.getNavigationController();
        navigationController.execute("home", username);
    }
    
    // Save user credentials
    private static void saveUser(String username, String password, String email) {
        try {
            userRepository.saveUser(username, password, email);
        } catch (RuntimeException e) {
            showThemedMessage(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Validate login credentials
    private static boolean validateLogin(String username, String password) {
        try {
            boolean isValid = userRepository.validateLogin(username, password);
            if (isValid) {
                userRepository.updateLastLogin(username);
            }
            return isValid;
        } catch (Exception e) {
            showThemedMessage(null, "Database error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Show sign up page
    private static void showSignUpPage() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snack Overflow - Sign Up");
            frame.setMinimumSize(new java.awt.Dimension(720, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            
            // Create main panel
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBackground(new Color(240, 235, 255));
            GridBagConstraints gbc = new GridBagConstraints();
            
            // Title
            JLabel titleLabel = new JLabel("Create Your Account");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(new Color(75, 0, 130));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(20, 20, 30, 20);
            mainPanel.add(titleLabel, gbc);
            
            // Username label
            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            usernameLabel.setForeground(new Color(75, 0, 130));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 20, 5, 10);
            mainPanel.add(usernameLabel, gbc);
            
            // Username field
            JTextField usernameField = new JTextField(15);
            usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
            usernameField.setBackground(Color.WHITE);
            usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 10, 5, 20);
            mainPanel.add(usernameField, gbc);
            
            // Password label
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            passwordLabel.setForeground(new Color(75, 0, 130));
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 20, 5, 10);
            mainPanel.add(passwordLabel, gbc);
            
            // Password field
            JPasswordField passwordField = new JPasswordField(15);
            passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
            passwordField.setBackground(Color.WHITE);
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 10, 5, 20);
            mainPanel.add(passwordField, gbc);
            
            // Sign up button
            JButton signUpButton = new JButton("Sign Up");
            signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
            signUpButton.setBackground(new Color(138, 43, 226));
            signUpButton.setForeground(Color.WHITE);
            signUpButton.setFocusPainted(false);
            signUpButton.setBorderPainted(false);
            signUpButton.setOpaque(true);
            signUpButton.setPreferredSize(new Dimension(120, 40));
            
            // Add sign up functionality
            signUpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText().trim();
                    String password = new String(passwordField.getPassword());
                    
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter both username and password");
                        return;
                    }
                    
                    saveUser(username, password, ""); // Empty email for now
                    showThemedMessage(frame, "Account created successfully!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    main(new String[]{}); // Return to login page
                }
            });
            
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 20, 10, 20);
            mainPanel.add(signUpButton, gbc);
            
            // Back to login link
            JLabel loginLabel = new JLabel("<html><u>Already have an account? Login</u></html>");
            loginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            loginLabel.setForeground(new Color(147, 112, 219));
            loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    frame.dispose();
                    main(new String[]{});
                }
            });
            
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 20, 20, 20);
            mainPanel.add(loginLabel, gbc);
            
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snack Overflow - Login");
            frame.setMinimumSize(new java.awt.Dimension(720, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Center the window
            
            // Create main panel
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBackground(new Color(240, 235, 255)); // Light purple background
            GridBagConstraints gbc = new GridBagConstraints();
            
            // Title
            JLabel titleLabel = new JLabel("Welcome to Snack Overflow");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(new Color(75, 0, 130)); // Indigo purple
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(20, 20, 30, 20);
            mainPanel.add(titleLabel, gbc);
            
            // Username label
            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            usernameLabel.setForeground(new Color(75, 0, 130)); // Purple text
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 20, 5, 10);
            mainPanel.add(usernameLabel, gbc);
            
            // Username field
            JTextField usernameField = new JTextField(15);
            usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
            usernameField.setBackground(Color.WHITE);
            usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)), // Medium purple border
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 10, 5, 20);
            mainPanel.add(usernameField, gbc);
            
            // Password label
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            passwordLabel.setForeground(new Color(75, 0, 130)); // Purple text
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 20, 5, 10);
            mainPanel.add(passwordLabel, gbc);
            
            // Password field
            JPasswordField passwordField = new JPasswordField(15);
            passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
            passwordField.setBackground(Color.WHITE);
            passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)), // Medium purple border
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 10, 5, 20);
            mainPanel.add(passwordField, gbc);
            
            // Login button
            JButton loginButton = new JButton("Login");
            loginButton.setFont(new Font("Arial", Font.BOLD, 14));
            loginButton.setBackground(new Color(138, 43, 226)); // Blue violet purple
            loginButton.setForeground(Color.WHITE);
            loginButton.setFocusPainted(false);
            loginButton.setBorderPainted(false);
            loginButton.setOpaque(true); // Ensure background color shows
            loginButton.setPreferredSize(new Dimension(120, 40));
            
            // Add login functionality
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText().trim();
                    String password = new String(passwordField.getPassword());
                    
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter both username and password");
                        return;
                    }
                    
                    if (validateLogin(username, password)) {
                        goToHomePage(username, frame);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials");
                    }
                }
            });
            
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 20, 10, 20);
            mainPanel.add(loginButton, gbc);
            
            // Sign up link
            JLabel signupLabel = new JLabel("<html><u>Don't have an account? Sign up</u></html>");
            signupLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            signupLabel.setForeground(new Color(147, 112, 219)); // Medium purple
            signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Add click functionality to sign up
            signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    frame.dispose();
                    showSignUpPage();
                }
            });
            
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 20, 20, 20);
            mainPanel.add(signupLabel, gbc);
            
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
