package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddRecipeForm {
    private JLabel instructionsLabel;

    private int recipeID; // automatically generated


    public void AddRecipePopUp() {
        JFrame frame = new JFrame("Add New Recipe");
        frame.setSize(480, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //-------------- main panel ----------------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 235, 255));

        //--------------- top panel ----------------

        JPanel recipeInfoPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        recipeInfoPanel.setBackground(new Color(240, 235, 255));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Color labelColor = new Color(75, 0, 130);

        JLabel recipeNameLabel = new JLabel("Recipe Name:");
        recipeNameLabel.setFont(labelFont);
        recipeNameLabel.setForeground(labelColor);
        JTextField recipeNameField = styledTextField();

        recipeInfoPanel.add(recipeNameLabel);
        recipeInfoPanel.add(recipeNameField);

        JLabel servingSizeLabel = new JLabel("Serving Size:");
        servingSizeLabel.setFont(labelFont);
        servingSizeLabel.setForeground(labelColor);
        JTextField servingSizeField = styledTextField();

        recipeInfoPanel.add(servingSizeLabel);
        recipeInfoPanel.add(servingSizeField);

        JLabel cuisineLabel = new JLabel("Cuisine:");
        cuisineLabel.setFont(labelFont);
        cuisineLabel.setForeground(labelColor);
        JTextField cuisineField = styledTextField();

        recipeInfoPanel.add(cuisineLabel);
        recipeInfoPanel.add(cuisineField);

        JLabel cookingTimeLabel = new JLabel("Cooking Time:");
        cookingTimeLabel.setFont(labelFont);
        cookingTimeLabel.setForeground(labelColor);
        JTextField cookingTimeField = styledTextField();

        recipeInfoPanel.add(cookingTimeLabel);
        recipeInfoPanel.add(cookingTimeField);

        JLabel mealTypeLabel = new JLabel("Meal Type:");
        mealTypeLabel.setFont(labelFont);
        mealTypeLabel.setForeground(labelColor);
        JTextField mealTypeField = styledTextField();

        recipeInfoPanel.add(mealTypeLabel);
        recipeInfoPanel.add(mealTypeField);

        JLabel tagsLabel = new JLabel("Tags:");
        tagsLabel.setFont(labelFont);
        tagsLabel.setForeground(labelColor);
        // tooltip not working :(
        tagsLabel.setToolTipText("Enter multiple tags separated by commas.");
        JTextField tagsField = styledTextField();

        recipeInfoPanel.add(tagsLabel);
        recipeInfoPanel.add(tagsField);

        servingSizeLabel = new JLabel("Serving Size:");
        servingSizeLabel.setFont(labelFont);
        servingSizeLabel.setForeground(labelColor);
        servingSizeField = styledTextField();

        recipeInfoPanel.add(servingSizeLabel);
        recipeInfoPanel.add(servingSizeField);

        //--------------ingredients panel----------------
        JPanel ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BorderLayout(10, 10));
        ingredientsPanel.setBorder(BorderFactory.createTitledBorder("Ingredients"));

        ingredientsPanel.setBackground(new Color(240, 235, 255));

        String[] columnNames = {"Ingredient", "Quantity", "Unit"};
        DefaultTableModel ingredientsModel = new DefaultTableModel(columnNames, 1);
        JTable ingredientsTable = new JTable(ingredientsModel);

        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsTable);
        ingredientsPanel.add(ingredientsScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 235, 255));
        JButton addIngredientButton = styledButton("Add Ingredient");

        buttonPanel.add(addIngredientButton);

        mainPanel.add(recipeInfoPanel);
        mainPanel.add(ingredientsPanel);
        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);

        // -------------- instructions panel ----------------
        JPanel instructionsPanel = new JPanel(new BorderLayout(10, 10));
        instructionsPanel.setBackground(new Color(240, 235, 255));
        instructionsPanel.setBorder(BorderFactory.createTitledBorder("Instructions"));

        JTextArea instructionsTextArea = new JTextArea(6, 20);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);

        JScrollPane instructionsScrollPane = new JScrollPane(instructionsTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        instructionsPanel.add(instructionsScrollPane, BorderLayout.CENTER);

        mainPanel.add(instructionsPanel);


        // -------------- submit button panel ----------------
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomButtonPanel.setBackground(new Color(240, 235, 255));
        JButton addButton = styledButton("Add Recipe");
        JButton cancelButton = styledButton("Cancel");

        bottomButtonPanel.add(addButton);
        bottomButtonPanel.add(cancelButton);

        mainPanel.add(bottomButtonPanel);
    }

    private JTextField styledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JButton styledButton(String text) {
        Color labelColor = new Color(75, 0, 130); // purple

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(138, 43, 226));
        button.setForeground(labelColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddRecipeForm form = new AddRecipeForm();
            form.AddRecipePopUp();  // show the window
        });
    }

}