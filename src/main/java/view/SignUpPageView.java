package view;

import interface_adapter.signup.SignUpController;
import interface_adapter.signup.SignUpViewModel;
import interface_adapter.signup.SignUpState;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for the Sign Up page following Clean Architecture principles.
 * This view only handles UI concerns and delegates business logic to the controller.
 */
public class SignUpPageView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "signup";
    private final SignUpViewModel signUpViewModel;
    private final SignUpController signUpController;
    private final NavigationController navigationController;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton signUpButton;
    private JLabel loginLabel;
    private JLabel messageLabel;
    
    public SignUpPageView(SignUpViewModel signUpViewModel, SignUpController signUpController,
                         NavigationController navigationController) {
        this.signUpViewModel = signUpViewModel;
        this.signUpController = signUpController;
        this.navigationController = navigationController;
        
        this.signUpViewModel.addPropertyChangeListener(this);
        
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
        
        // Email field
        emailField = new JTextField(15);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(147, 112, 219)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Sign up button
        signUpButton = new JButton(SignUpViewModel.SIGNUP_BUTTON_LABEL);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBackground(new Color(138, 43, 226));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setOpaque(true);
        signUpButton.setPreferredSize(new Dimension(120, 40));
        signUpButton.addActionListener(this);
        
        // Login link
        loginLabel = new JLabel("<html><u>" + SignUpViewModel.LOGIN_LINK_LABEL + "</u></html>");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        loginLabel.setForeground(new Color(147, 112, 219));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Navigate to login page using proper navigation controller
                showLoginPageDialog();
            }
        });
        
        // Message label (for both error and success messages)
        messageLabel = new JLabel(" "); // Invisible by default
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void layoutComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel(SignUpViewModel.TITLE_LABEL);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 30, 20);
        this.add(titleLabel, gbc);
        
        // Username label
        JLabel usernameLabel = new JLabel(SignUpViewModel.USERNAME_LABEL);
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
        JLabel passwordLabel = new JLabel(SignUpViewModel.PASSWORD_LABEL);
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
        
        // Email label
        JLabel emailLabel = new JLabel(SignUpViewModel.EMAIL_LABEL);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(75, 0, 130));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 20, 5, 10);
        this.add(emailLabel, gbc);
        
        // Email field
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 20);
        this.add(emailField, gbc);
        
        // Message label
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 20, 5, 20);
        this.add(messageLabel, gbc);
        
        // Sign up button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 10, 20);
        this.add(signUpButton, gbc);
        
        // Login link
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 20, 20, 20);
        this.add(loginLabel, gbc);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText().trim();
            
            // Update state to show sign up in progress
            SignUpState currentState = signUpViewModel.getState();
            currentState.setUsername(username);
            currentState.setPassword(password);
            currentState.setEmail(email);
            currentState.setSignUpInProgress(true);
            currentState.setErrorMessage(null);
            currentState.setSuccessMessage(null);
            signUpViewModel.setState(currentState);
            signUpViewModel.firePropertyChanged();
            
            // Execute sign up use case
            signUpController.execute(username, password, email);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignUpState state = signUpViewModel.getState();
        
        // Update message display
        if (state.getErrorMessage() != null) {
            messageLabel.setText(state.getErrorMessage());
            messageLabel.setForeground(Color.RED);
            messageLabel.setVisible(true);
        } else if (state.getSuccessMessage() != null) {
            messageLabel.setText(state.getSuccessMessage());
            messageLabel.setForeground(new Color(0, 128, 0)); // Dark green
            messageLabel.setVisible(true);
        } else {
            messageLabel.setText(" ");
            messageLabel.setVisible(false);
        }
        
        // Update form state
        signUpButton.setEnabled(!state.isSignUpInProgress());
        usernameField.setEnabled(!state.isSignUpInProgress());
        passwordField.setEnabled(!state.isSignUpInProgress());
        emailField.setEnabled(!state.isSignUpInProgress());
        
        if (state.isSignUpInProgress()) {
            signUpButton.setText("Creating Account...");
        } else {
            signUpButton.setText(SignUpViewModel.SIGNUP_BUTTON_LABEL);
        }
    }
    
    public String getViewName() {
        return viewName;
    }
    
    // Navigate to login page using proper navigation
    protected void showLoginPageDialog() {
        // Use navigation controller to navigate to login page
        // The ViewManager will handle creating the login page with clean architecture
        navigationController.execute("login", null);
    }
    
    /**
     * Static factory method to create and display the sign up page in a frame.
     * This is for backward compatibility and testing.
     */
    public static JFrame show(SignUpViewModel signUpViewModel, SignUpController signUpController,
                             NavigationController navigationController) {
        JFrame frame = new JFrame("Snack Overflow - Sign Up");
        frame.setMinimumSize(new Dimension(720, 520));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        SignUpPageView signUpPageView = new SignUpPageView(signUpViewModel, signUpController, navigationController);
        frame.add(signUpPageView);
        
        frame.pack();
        frame.setVisible(true);
        
        return frame;
    }
}
