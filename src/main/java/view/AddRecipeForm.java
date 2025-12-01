package view;

import data_access.RecipeDataAccessObject;
import entity.Ingredient;
import entity.Tag;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import interface_adapter.add_recipe.AddRecipeController;
import interface_adapter.add_recipe.AddRecipePresenter;
import interface_adapter.add_recipe.AddRecipeViewModel;
import interface_adapter.navigation.NavigationController;
import use_case.add_recipe.AddRecipeInteractor;

public class AddRecipeForm {

    private int recipeID; // auto-generated if needed

    public JPanel getRecipeForm(String username, NavigationController navigationController) {

        AddRecipeForm form = new AddRecipeForm();

        JFrame frame = new JFrame("Add New Recipe");
        frame.setSize(480, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= MAIN PANEL =================
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 235, 255));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Color labelColor = new Color(75, 0, 130);

        // ================= RECIPE INFO PANEL =================
        JPanel recipeInfoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        recipeInfoPanel.setBackground(new Color(240, 235, 255));

        JLabel recipeNameLabel = new JLabel("Recipe Name:");
        recipeNameLabel.setFont(labelFont);
        recipeNameLabel.setForeground(labelColor);
        JTextField recipeNameField = form.styledTextField();
        recipeInfoPanel.add(recipeNameLabel);
        recipeInfoPanel.add(recipeNameField);

        JLabel servingSizeLabel = new JLabel("Serving Size:");
        servingSizeLabel.setFont(labelFont);
        servingSizeLabel.setForeground(labelColor);
        JTextField servingSizeField = form.styledTextField();
        recipeInfoPanel.add(servingSizeLabel);
        recipeInfoPanel.add(servingSizeField);

        JLabel cuisineLabel = new JLabel("Cuisine:");
        cuisineLabel.setFont(labelFont);
        cuisineLabel.setForeground(labelColor);
        JTextField cuisineField = form.styledTextField();
        recipeInfoPanel.add(cuisineLabel);
        recipeInfoPanel.add(cuisineField);

        JLabel cookingTimeLabel = new JLabel("Cooking Time (in minutes):");
        cookingTimeLabel.setFont(labelFont);
        cookingTimeLabel.setForeground(labelColor);
        JTextField cookingTimeField = form.styledTextField();
        recipeInfoPanel.add(cookingTimeLabel);
        recipeInfoPanel.add(cookingTimeField);

        JLabel mealTypeLabel = new JLabel("Meal Type:");
        mealTypeLabel.setFont(labelFont);
        mealTypeLabel.setForeground(labelColor);
        JTextField mealTypeField = form.styledTextField();
        recipeInfoPanel.add(mealTypeLabel);
        recipeInfoPanel.add(mealTypeField);

        JLabel tagsLabel = new JLabel("Tags:");
        tagsLabel.setFont(labelFont);
        tagsLabel.setForeground(labelColor);
        JTextField tagsField = form.styledTextField();
        recipeInfoPanel.add(tagsLabel);
        recipeInfoPanel.add(tagsField);

        // ================= INGREDIENTS PANEL =================
        JPanel ingredientsPanel = new JPanel(new BorderLayout(10, 10));
        ingredientsPanel.setBorder(BorderFactory.createTitledBorder("Ingredients"));
        ingredientsPanel.setBackground(new Color(240, 235, 255));

        String[] columnNames = {"Ingredient", "Quantity", "Unit"};
        DefaultTableModel ingredientsModel = new DefaultTableModel(columnNames, 1);
        JTable ingredientsTable = new JTable(ingredientsModel);

        JScrollPane ingScroll = new JScrollPane(ingredientsTable);
        ingredientsPanel.add(ingScroll, BorderLayout.CENTER);

        JPanel addIngPanel = new JPanel();
        addIngPanel.setBackground(new Color(240, 235, 255));
        JButton addIngredientButton = form.styledButton("Add Ingredient");
        addIngredientButton.addActionListener(e -> ingredientsModel.addRow(new Object[]{"", "", ""}));
        addIngPanel.add(addIngredientButton);

        // Add ingredient section
        mainPanel.add(recipeInfoPanel);
        mainPanel.add(ingredientsPanel);
        mainPanel.add(addIngPanel);

        // ================= INSTRUCTIONS PANEL =================
        JPanel instructionsPanel = new JPanel(new BorderLayout(10, 10));
        instructionsPanel.setBackground(new Color(240, 235, 255));
        instructionsPanel.setBorder(BorderFactory.createTitledBorder("Instructions"));

        JTextArea instructionsTextArea = new JTextArea(6, 20);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);

        JScrollPane instructionsScroll = new JScrollPane(instructionsTextArea);
        instructionsPanel.add(instructionsScroll, BorderLayout.CENTER);

        mainPanel.add(instructionsPanel);

        // ================= BUTTON PANEL =================
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomButtonPanel.setBackground(new Color(240, 235, 255));

        JButton addButton = form.styledButton("Add Recipe");
        JButton cancelButton = form.styledButton("Cancel");

        bottomButtonPanel.add(addButton);
        bottomButtonPanel.add(cancelButton);

        cancelButton.addActionListener(e -> {
            // Close the current frame
            frame.dispose();

        });


        // ========== ADD BUTTON ACTION =============
        addButton.addActionListener(e -> {
            try {
                String title = recipeNameField.getText();
                int servingSize = Integer.parseInt(servingSizeField.getText().trim());
                String cuisine = cuisineField.getText();
                int cookingTime = Integer.parseInt(cookingTimeField.getText().trim());
                String mealType = mealTypeField.getText();
                String tagText = tagsField.getText();
                String instructions = instructionsTextArea.getText();

                // Collect ingredients
                List<Ingredient> ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsModel.getRowCount(); i++) {
                    String name = (String) ingredientsModel.getValueAt(i, 0);
                    String qty = (String) ingredientsModel.getValueAt(i, 1);
                    String unit = (String) ingredientsModel.getValueAt(i, 2);

                    if (name != null && !name.isEmpty()) {
                        ingredients.add(new Ingredient(name, qty, unit));
                    }
                }

                // Collect tags
                List<Tag> tagList = new ArrayList<>();
                if (tagText != null && !tagText.isEmpty()) {
                    int id = 1;
                    for (String t : tagText.split(",")) {
                        String trimmed = t.trim();
                        if (!trimmed.isEmpty()) {
                            tagList.add(new Tag(id++, trimmed));
                        }
                    }
                }

                // INTERACTOR SETUP
                AddRecipeViewModel vm = new AddRecipeViewModel();
                AddRecipePresenter presenter = new AddRecipePresenter(vm);
                RecipeDataAccessObject data = new RecipeDataAccessObject();
                AddRecipeInteractor interactor = new AddRecipeInteractor(data, presenter, username);

                AddRecipeController controller = new AddRecipeController(interactor);

                controller.addRecipe(
                        form.recipeID,
                        title,
                        ingredients,
                        instructions,
                        cuisine,
                        cookingTime,
                        mealType,
                        servingSize,
                        tagList
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input: " + ex.getMessage());
            }
        });

        mainPanel.add(bottomButtonPanel);

        return mainPanel;
    }


    // ========== Utility Styling Methods ==========
    private JTextField styledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return tf;
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(138, 43, 226));
        btn.setForeground(new Color(255, 255, 255));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        return btn;
    }
}
