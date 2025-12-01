package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for the Login page following Clean Architecture principles.
 * This view only handles UI concerns and delegates business logic to the controller.
 */
public class LoginPageView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "login";
    private final LoginViewModel loginViewModel;
    private final LoginController loginController;
    private final interface_adapter.navigation.NavigationController navigationController;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel signupLabel;
    private JLabel errorLabel;
    
    public LoginPageView(LoginViewModel loginViewModel, LoginController loginController, 
                        interface_adapter.navigation.NavigationController navigationController) {
        this.loginViewModel = loginViewModel;
        this.loginController = loginController;
        this.navigationController = navigationController;
        
        this.loginViewModel.addPropertyChangeListener(this);
        
        this.setBackground(new Color(240, 235, 255));
        
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeComponents() {
        // Username field
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(147, 112, 219)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Password field
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(147, 112, 219)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Login button
        loginButton = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(138, 43, 226));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.addActionListener(this);
        
        // Sign up link
        signupLabel = new JLabel("<html><u>" + LoginViewModel.SIGNUP_LINK_LABEL + "</u></html>");
        signupLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        signupLabel.setForeground(new Color(147, 112, 219));
        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Navigate to signup page using proper navigation controller
                showSignUpPageDialog();
            }
        });
        
        // Error label
        errorLabel = new JLabel(" "); // Invisible by default
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        errorLabel.setForeground(Color.RED);
    }
    
    private void layoutComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel(LoginViewModel.TITLE_LABEL);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 30, 20);
        this.add(titleLabel, gbc);
        
        // Username label
        JLabel usernameLabel = new JLabel(LoginViewModel.USERNAME_LABEL);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 20, 5, 10);
        this.add(usernameLabel, gbc);
        
        // Username field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 20);
        this.add(usernameField, gbc);
        
        // Password label
        JLabel passwordLabel = new JLabel(LoginViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 20, 5, 10);
        this.add(passwordLabel, gbc);
        
        // Password field
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 20);
        this.add(passwordField, gbc);
        
        // Error label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 20, 5, 20);
        this.add(errorLabel, gbc);
        
        // Login button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 10, 20);
        this.add(loginButton, gbc);
        
        // Sign up link
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 20, 20, 20);
        this.add(signupLabel, gbc);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            // Update state to show login in progress
            LoginState currentState = loginViewModel.getState();
            currentState.setUsername(username);
            currentState.setPassword(password);
            currentState.setLoginInProgress(true);
            currentState.setErrorMessage(null);
            loginViewModel.setState(currentState);
            loginViewModel.firePropertyChanged();
            
            // Execute login use case
            loginController.execute(username, password);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = loginViewModel.getState();
        
        // Update error message display
        if (state.getErrorMessage() != null) {
            errorLabel.setText(state.getErrorMessage());
            errorLabel.setVisible(true);
        } else {
            errorLabel.setText(" ");
            errorLabel.setVisible(false);
        }
        
        // Update form state
        loginButton.setEnabled(!state.isLoginInProgress());
        
        if (state.isLoginInProgress()) {
            loginButton.setText("Logging in...");
        } else {
            loginButton.setText(LoginViewModel.LOGIN_BUTTON_LABEL);
        }
    }
    
    public String getViewName() {
        return viewName;
    }
    
    // Navigate to signup page using proper navigation
    protected void showSignUpPageDialog() {
        // Use navigation controller to navigate to signup page
        // The ViewManager will handle creating the signup page with clean architecture
        navigationController.execute("signup", null);
    }
    
    /**
     * Static factory method to create and display the login page in a frame.
     * This is for backward compatibility and testing.
     */
    public static JFrame show(LoginViewModel loginViewModel, LoginController loginController, 
                             NavigationController navigationController) {
        JFrame frame = new JFrame("Snack Overflow - Login");
        frame.setMinimumSize(new Dimension(720, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        LoginPageView loginPageView = new LoginPageView(loginViewModel, loginController, navigationController);
        frame.add(loginPageView);
        
        frame.pack();
        frame.setVisible(true);
        
        return frame;
    }
}