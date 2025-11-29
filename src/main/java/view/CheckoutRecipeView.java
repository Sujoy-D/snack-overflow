package view;

import entity.Recipe;
import entity.Ingredient;
import interface_adapter.checkout_recipe.CheckoutRecipeController;
import interface_adapter.checkout_recipe.CheckoutRecipeState;
import interface_adapter.checkout_recipe.CheckoutRecipeViewModel;
import interface_adapter.navigation.NavigationController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;

public class CheckoutRecipeView extends JPanel implements PropertyChangeListener {

    private String username;

    private final NavigationController navigationController;
    private final CheckoutRecipeController checkoutRecipeController;
    private final CheckoutRecipeViewModel checkoutRecipeViewModel;

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
                              NavigationController navigationController,
                              CheckoutRecipeController checkoutRecipeController,
                              CheckoutRecipeViewModel checkoutRecipeViewModel,
                              Recipe recipe) {

        this.username = username;
        this.navigationController = navigationController;
        this.checkoutRecipeController = checkoutRecipeController;
        this.checkoutRecipeViewModel = checkoutRecipeViewModel;

        this.checkoutRecipeViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        SidebarView sidebar = new SidebarView(navigationController, username, null);
        add(sidebar, BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 235, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // Right panel (Cuisine + Cooking time + Type etc.)
        JPanel detailsPanel = makeDetailsPanel();
        add(detailsPanel, BorderLayout.EAST);

        // Centre panel (Title + Ingredients + Instructions) as scroll pane TODO: add support for images?
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

    }

    @NotNull
    private JPanel makeDetailsPanel() {
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CheckoutRecipeState state = (CheckoutRecipeState) evt.getNewValue();

        if (state.getErrorMessage() != null) {
            // TODO: add stuff to display when there is an error
            return;
        }

        Map<String, String> recipeInfo = state.getRecipeInfo();

        titleLabel.setText(recipeInfo.get("title").toString());

        instructionsLabel.setText(recipeInfo.get("instructions").toString());
        // TODO: bit of an issue here: calling entity arguments within the view
        //for () {
        //    JLabel ingredientLabel = new JLabel();

        //}
    }
}
