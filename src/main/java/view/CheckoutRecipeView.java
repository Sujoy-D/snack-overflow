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
import java.util.ArrayList;
import java.util.Map;

public class CheckoutRecipeView {

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

        frame = new JFrame("Snack Overflow - Recipe");
        frame.setMinimumSize(new Dimension(640, 480));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.setLayout(new BorderLayout());

        SidebarView sidebar = new SidebarView(navigationController, username, null);
        frame.add(sidebar, BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 235, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        // Right panel (Cuisine + Cooking time + Type etc.)
        JPanel detailsPanel = buildDetailsPanel();
        frame.add(detailsPanel, BorderLayout.EAST);

        // Centre panel (Title + Ingredients + Instructions)
        JScrollPane mainScrollPane = new JScrollPane();
        mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(mainScrollPane);

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainScrollPane.add(titleLabel);

        ingredientsPanel = new JPanel();
        ingredientsPanel.setBackground(new Color(240, 235, 255));
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        mainScrollPane.add(ingredientsPanel);

        instructionsLabel = new JLabel();
        mainScrollPane.add(instructionsLabel);

        // Adding text to labels
        CheckoutRecipeState state = checkoutRecipeViewModel.getState();

        if (state.getErrorMessage() != null) {
            titleLabel.setText(state.getErrorMessage());
            return;
        }

        Map<String, String> recipeInfo = state.getRecipeInfo();

        titleLabel.setText(recipeInfo.get("title"));

        instructionsLabel.setText(recipeInfo.get("instructions"));

        cuisineLabel.setText(recipeInfo.get("cuisine"));

        cookingTimeLabel.setText(recipeInfo.get("cooking time"));

        mealTypeLabel.setText(recipeInfo.get("meal type"));

        servingSizeLabel.setText(recipeInfo.get("serving size"));

        updateIngredientsPanel(state);
        updateTagsLabel(state);

    }

    // TODO: implement the show method
    public static JFrame show(String username, NavigationController navigationController) {
        CheckoutRecipeView view = new CheckoutRecipeView(username, navigationController);
        return view.frame;
    }

    @NotNull
    private JPanel buildDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(new Color(240, 235, 255));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        cuisineLabel = new JLabel();
        detailsPanel.add(cuisineLabel);

        cookingTimeLabel = new JLabel();
        detailsPanel.add(cookingTimeLabel);

        mealTypeLabel = new JLabel();
        detailsPanel.add(mealTypeLabel);

        servingSizeLabel = new JLabel();
        detailsPanel.add(servingSizeLabel);

        tagsLabel = new JLabel();
        detailsPanel.add(tagsLabel);
        return detailsPanel;
    }

    private void updateTagsLabel(CheckoutRecipeState state) {
        ArrayList<String> recipeTags = (ArrayList<String>) state.getRecipeTags();
        StringBuilder tagString = new StringBuilder("Tags: ");

        for (String tag : recipeTags) {
            tagString.append(tag).append(", ");
        }
        tagString.delete(tagString.length() - 2, tagString.length()); // Remove the last ", " appended in the loop

        tagsLabel.setText(tagString.toString());
    }

    private void updateIngredientsPanel(CheckoutRecipeState state) {
        for (ArrayList<String> ingredientData : state.getRecipeIngredients()) {
            JLabel ingredientLabel = new JLabel();

            // Indices: 0 = name, 1 = quantity, 2 = unit
            ingredientLabel.setText(
                    ingredientData.get(0) + " : "
                    + ingredientData.get(1) + " "
                    + ingredientData.get(2)
            );

            ingredientsPanel.add(ingredientLabel);
        }
    }
}
