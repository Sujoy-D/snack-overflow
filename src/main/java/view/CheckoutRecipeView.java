package view;

import data_access.CheckoutRecipeDataAccessInterface;
import data_access.CheckoutRecipeDataAccessObject;
import interface_adapter.checkout_recipe.CheckoutRecipeController;
import interface_adapter.checkout_recipe.CheckoutRecipePresenter;
import interface_adapter.checkout_recipe.CheckoutRecipeState;
import interface_adapter.checkout_recipe.CheckoutRecipeViewModel;
import interface_adapter.navigation.NavigationController;
import org.jetbrains.annotations.NotNull;
import use_case.checkout_recipe.CheckoutRecipeInputBoundary;
import use_case.checkout_recipe.CheckoutRecipeInteractor;
import use_case.checkout_recipe.CheckoutRecipeOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;

public class CheckoutRecipeView implements PropertyChangeListener {

    private String username;

    private final NavigationController navigationController;
    private final CheckoutRecipeController checkoutRecipeController;
    private final CheckoutRecipeViewModel checkoutRecipeViewModel;

    private JFrame frame;

    // Centre panel
    private JLabel titleLabel;
    private JPanel ingredientsPanel;
    private JLabel instructionsLabel;

    // East panel
    private JLabel cuisineLabel;
    private JLabel cookingTimeLabel;
    private JLabel mealTypeLabel;
    private JLabel servingSizeLabel;
    private JLabel tagsLabel;


    public CheckoutRecipeView(String username,
                              NavigationController navigationController) {

        this.username = username;
        this.navigationController = navigationController;

        checkoutRecipeViewModel = new CheckoutRecipeViewModel();

        CheckoutRecipeDataAccessInterface checkoutRecipeDAO = new CheckoutRecipeDataAccessObject();
        CheckoutRecipeOutputBoundary checkoutRecipePresenter = new CheckoutRecipePresenter(checkoutRecipeViewModel);
        CheckoutRecipeInputBoundary checkoutRecipeInteractor = new CheckoutRecipeInteractor(checkoutRecipeDAO, checkoutRecipePresenter);
        this.checkoutRecipeController = new CheckoutRecipeController(checkoutRecipeInteractor);

        // Listen for changes to the view model
        checkoutRecipeViewModel.addPropertyChangeListener(this);

        // Create separate JFrame window (not modal)
        frame = new JFrame("Snack Overflow - Recipe Details");
        frame.setSize(new Dimension(1000, 800));
        frame.setMinimumSize(new Dimension(900, 700));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 235, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Right panel (Cuisine + Cooking time + Type etc.)
        JPanel detailsPanel = buildDetailsPanel();
        frame.add(detailsPanel, BorderLayout.WEST);

        // Centre panel (Title + Ingredients + Instructions)
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(55, 0, 120));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel);

        // Ingredients section
        JLabel ingredientsHeaderLabel = new JLabel("Ingredients:");
        ingredientsHeaderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ingredientsHeaderLabel.setForeground(new Color(70, 50, 120));
        mainPanel.add(ingredientsHeaderLabel);

        ingredientsPanel = new JPanel();
        ingredientsPanel.setBackground(Color.WHITE);
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        mainPanel.add(ingredientsPanel);

        // Instructions section
        JLabel instructionsHeaderLabel = new JLabel("Instructions:");
        instructionsHeaderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionsHeaderLabel.setForeground(new Color(70, 50, 120));
        mainPanel.add(instructionsHeaderLabel);

        instructionsLabel = new JLabel();
        instructionsLabel.setBackground(Color.WHITE);
        mainPanel.add(instructionsLabel);

        // Button for saving recipe
        JButton saveButton = new JButton("Save Recipe");
        saveButton.setBackground(new Color(65, 0, 140));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(45, 0, 100), 2),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        saveButton.setFont(new Font("Arial", Font.BOLD, 15));
        saveButton.addActionListener(e -> frame.dispose());
        detailsPanel.add(saveButton);

    }

    public static void show(String username, NavigationController navigationController, entity.Recipe recipe) {
        CheckoutRecipeView view = new CheckoutRecipeView(username, navigationController);

        // Load the recipe data into the view using the controller
        if (recipe != null) {
            view.loadRecipe(recipe);
        }

        view.frame.pack();
        view.frame.setVisible(true);
    }

    /**
     * Load recipe data into the view following Clean Architecture.
     * This method triggers the use case to display the recipe data.
     */
    private void loadRecipe(entity.Recipe recipe) {
        checkoutRecipeController.execute(recipe);
    }

    @NotNull
    private JPanel buildDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setPreferredSize(new Dimension(200, 0));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 235)),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        JLabel detailsHeaderLabel = new JLabel("Recipe Details");
        detailsHeaderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        detailsHeaderLabel.setForeground(new Color(55, 0, 120));
        detailsHeaderLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        detailsPanel.add(detailsHeaderLabel);

        cuisineLabel = new JLabel();
        cuisineLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        cuisineLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        detailsPanel.add(cuisineLabel);

        cookingTimeLabel = new JLabel();
        cookingTimeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        cookingTimeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        detailsPanel.add(cookingTimeLabel);

        mealTypeLabel = new JLabel();
        mealTypeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        mealTypeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        detailsPanel.add(mealTypeLabel);

        servingSizeLabel = new JLabel();
        servingSizeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        servingSizeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        detailsPanel.add(servingSizeLabel);

        tagsLabel = new JLabel();
        tagsLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        tagsLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        detailsPanel.add(tagsLabel);

        return detailsPanel;
    }

    private void updateTagsLabel(CheckoutRecipeState state) {
        ArrayList<String> recipeTags = (ArrayList<String>) state.getRecipeTags();
        if (recipeTags != null && !recipeTags.isEmpty()) {
            StringBuilder tagString = new StringBuilder("<html><b>Tags:</b><br>");

            for (String tag : recipeTags) {
                tagString.append("• ").append(tag).append("<br>");
            }
            tagString.append("</html>");

            tagsLabel.setText(tagString.toString());
        } else {
            tagsLabel.setText("");
        }
    }

    private void updateIngredientsPanel(CheckoutRecipeState state) {
        for (ArrayList<String> ingredientData : state.getRecipeIngredients()) {
            JPanel ingredientRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
            ingredientRow.setBackground(Color.WHITE);

            JLabel bulletLabel = new JLabel("• ");
            bulletLabel.setFont(new Font("Arial", Font.BOLD, 14));
            bulletLabel.setForeground(new Color(65, 0, 140));

            JLabel ingredientLabel = new JLabel();
            ingredientLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            ingredientLabel.setForeground(new Color(60, 60, 60));

            // Indices: 0 = name, 1 = quantity, 2 = unit
            String ingredientText = ingredientData.get(1) + " " + ingredientData.get(2) + " " + ingredientData.get(0);
            ingredientLabel.setText(ingredientText);

            ingredientRow.add(bulletLabel);
            ingredientRow.add(ingredientLabel);
            ingredientsPanel.add(ingredientRow);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Update the view when the view model changes
        SwingUtilities.invokeLater(this::updateView);
    }

    /**
     * Update all UI components based on the current view model state.
     */
    private void updateView() {
        CheckoutRecipeState state = checkoutRecipeViewModel.getState();

        if (state.getErrorMessage() != null) {
            System.out.println("DEBUG: Error message: " + state.getErrorMessage());
            titleLabel.setText("Error: " + state.getErrorMessage());
            return;
        }

        Map<String, String> recipeInfo = state.getRecipeInfo();

        if (recipeInfo != null) {
            String title = recipeInfo.get("title");
            titleLabel.setText(title != null ? title : "Recipe Details");

            String instructions = recipeInfo.get("instructions");
            if (instructions != null && !instructions.trim().isEmpty()) {
                instructionsLabel.setText("<html><body style='width: 400px; font-family: Arial; font-size: 13px; line-height: 1.4;'>" +
                        instructions + "</body></html>");
            } else {
                instructionsLabel.setText("<html><body style='width: 400px; font-family: Arial; font-size: 13px; color: #888;'>" +
                        "No instructions available</body></html>");
            }

            // Update recipe details with better formatting
            String cuisine = recipeInfo.get("cuisine");
            cuisineLabel.setText("<html><b>Cuisine:</b> " + (cuisine != null ? cuisine : "Not specified") + "</html>");

            String cookingTime = recipeInfo.get("cooking time");
            cookingTimeLabel.setText("<html><b>Cooking Time:</b> " + (cookingTime != null ? cookingTime + " min" : "Not specified") + "</html>");

            String mealType = recipeInfo.get("meal type");
            mealTypeLabel.setText("<html><b>Meal Type:</b> " + (mealType != null ? mealType : "Not specified") + "</html>");

            String servingSize = recipeInfo.get("serving size");
            servingSizeLabel.setText("<html><b>Serves:</b> " + (servingSize != null ? servingSize : "Not specified") + "</html>");

            // Clear and update ingredients
            ingredientsPanel.removeAll();
            updateIngredientsPanel(state);

            updateTagsLabel(state);

            // Refresh the display
            frame.revalidate();
            frame.repaint();
        }
    }
}
