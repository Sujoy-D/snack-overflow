package view;

import entity.Ingredient;
import entity.Recipe;
import entity.RecipeFactory;
import gateways.JavaHttpGateway;
import gateways.SpoonacularSearchGateway;
import interface_adapter.navigation.NavigationController;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import use_case.search.SearchRecipesInputBoundary;
import use_case.search.SearchRecipesInteractor;
import use_case.search.SearchRecipesOutputBoundary;
import use_case.search.SearchFilters;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPageView implements PropertyChangeListener {
    private final String username;
    private final NavigationController navigationController;
    private final SearchViewModel viewModel;
    private final SearchController searchController;
    
    private JFrame frame;
    private JTextField ingredientsField;
    private JTextField cookingTimeField;
    private JComboBox<String> dietCombo;
    private JTextField allergensField;
    private JTextField cuisineField;
    private JComboBox<String> mealTypeCombo;
    private JLabel statusLabel;
    private JPanel resultsPanel;
    
    private SearchPageView(String username, NavigationController navigationController) {
        this.username = username;
        this.navigationController = navigationController;
        
        this.viewModel = new SearchViewModel();
        RecipeFactory recipeFactory = new RecipeFactory();
        SpoonacularSearchGateway gateway = new SpoonacularSearchGateway(new JavaHttpGateway(), recipeFactory);
        SearchRecipesOutputBoundary presenter = new SearchPresenter(viewModel);
        SearchRecipesInputBoundary interactor = new SearchRecipesInteractor(gateway, presenter);
        this.searchController = new SearchController(interactor, viewModel, 5);
        
        this.viewModel.addPropertyChangeListener(this);
    }
    
    public static JFrame show(String username, NavigationController navigationController) {
        SearchPageView view = new SearchPageView(username, navigationController);
        view.buildUI();
        return view.frame;
    }
    
    /**
     * Builds the search UI: sidebar + form + scrollable results
     */
    private void buildUI() {
        frame = new JFrame("Snack Overflow - Search");
        frame.setMinimumSize(new Dimension(720, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        SidebarView sidebar = new SidebarView(navigationController, username, frame);
        frame.add(sidebar, BorderLayout.WEST);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 235, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Find recipes by ingredients");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(55, 0, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchPanel.add(titleLabel);
        
        JLabel hintLabel = new JLabel("Enter comma-separated ingredients (e.g., apples,flour,sugar)");
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        hintLabel.setForeground(new Color(60, 60, 60));
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchPanel.add(Box.createVerticalStrut(6));
        searchPanel.add(hintLabel);
        
        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setOpaque(false);
        ingredientsField = new JTextField();
        ingredientsField.setFont(new Font("Arial", Font.PLAIN, 14));
        ingredientsField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(147, 112, 219)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(65, 0, 140));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(45, 0, 100), 2),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        searchButton.setFont(new Font("Arial", Font.BOLD, 15));
        searchButton.setPreferredSize(new Dimension(140, 42));
        searchButton.setOpaque(true);
        searchButton.addActionListener(e -> triggerSearch());
        
        inputRow.add(ingredientsField, BorderLayout.CENTER);
        inputRow.add(searchButton, BorderLayout.EAST);
        
        searchPanel.add(Box.createVerticalStrut(12));
        searchPanel.add(inputRow);
        
        searchPanel.add(Box.createVerticalStrut(16));
        searchPanel.add(buildFiltersPanel());
        
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(90, 90, 90));
        searchPanel.add(Box.createVerticalStrut(8));
        searchPanel.add(statusLabel);
        
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        
        resultsPanel = new JPanel();
        resultsPanel.setOpaque(true);
        resultsPanel.setBackground(new Color(250, 248, 255));
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setPreferredSize(new Dimension(0, 320));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void triggerSearch() {
        String ingredients = ingredientsField.getText().trim();
        SearchFilters filters = buildFilters();
        
        if (filters == null) {
            // validation error already shown
            return;
        }
        
        if ((ingredients == null || ingredients.isEmpty()) && filters.isEmpty()) {
            statusLabel.setText("No filters are active; showing current results.");
            statusLabel.setForeground(new Color(90, 90, 90));
            return;
        }
        
        statusLabel.setText("Searching...");
        statusLabel.setForeground(new Color(75, 0, 130));
        searchController.search(ingredients, filters);
    }
    
    /**
     * Update status/error text and results list based on the latest state
     */
    private void renderResults() {
        if (resultsPanel == null || statusLabel == null) {
            return;
        }
        
        resultsPanel.removeAll();
        
        if (viewModel.isSearching()) {
            statusLabel.setText("Searching...");
            statusLabel.setForeground(new Color(75, 0, 130));
        } else if (viewModel.getErrorMessage() != null) {
            statusLabel.setText(viewModel.getErrorMessage());
            statusLabel.setForeground(new Color(200, 0, 0));
        } else {
            List<Recipe> recipes = viewModel.getRecipes();
            if (recipes.isEmpty()) {
                statusLabel.setText("No recipes were found for these filters.");
                statusLabel.setForeground(new Color(200, 0, 0));
                
                JLabel emptyLabel = new JLabel("Try loosening filters or removing exclusions.");
                emptyLabel.setFont(new Font("Arial", Font.PLAIN, 13));
                emptyLabel.setForeground(new Color(90, 90, 90));
                resultsPanel.add(emptyLabel);
            } else {
                JLabel header = new JLabel("Results");
                header.setFont(new Font("Arial", Font.BOLD, 15));
                header.setForeground(new Color(70, 50, 120));
                header.setAlignmentX(Component.LEFT_ALIGNMENT);
                resultsPanel.add(header);
                resultsPanel.add(Box.createVerticalStrut(6));
                
                statusLabel.setText("Found " + recipes.size() + " recipes");
                statusLabel.setForeground(new Color(33, 150, 83));
                
                for (Recipe recipe : recipes) {
                    resultsPanel.add(createRecipeCard(recipe));
                    resultsPanel.add(Box.createVerticalStrut(10));
                }
            }
        }
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
        if (frame != null) {
            frame.revalidate();
            frame.repaint();
        }
    }
    
    private SearchFilters buildFilters() {
        Integer maxCookingTime = null;
        String cookingText = cookingTimeField.getText().trim();
        if (!cookingText.isEmpty()) {
            try {
                maxCookingTime = Integer.parseInt(cookingText);
            } catch (NumberFormatException e) {
                statusLabel.setText("Invalid cooking time. Please enter a positive number.");
                statusLabel.setForeground(new Color(200, 0, 0));
                return null;
            }
        }
        
        String diet = dietCombo.getSelectedItem() != null && !"None".equals(dietCombo.getSelectedItem())
                ? dietCombo.getSelectedItem().toString()
                : null;
        
        String mealType = mealTypeCombo.getSelectedItem() != null
                && !"Any".equals(mealTypeCombo.getSelectedItem())
                ? mealTypeCombo.getSelectedItem().toString()
                : null;
        
        List<String> allergens = csvToList(allergensField.getText());
        
        // Add filter inputs into the value object consumed by the use case
        return new SearchFilters(
                maxCookingTime,
                diet,
                allergens,
                emptyToNull(cuisineField.getText()),
                mealType
        );
    }
    
    private List<String> csvToList(String text) {
        if (text == null || text.trim().isEmpty()) {
            return List.of();
        }
        // split on comma, trim, and drop blanks
        return List.of(text.split(",")).stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
    
    private String emptyToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    
    private JPanel createRecipeCard(Recipe recipe) {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 235)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(recipe.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(60, 60, 60));
        
        JLabel ingredientsLabel = new JLabel("Ingredients: " + formatIngredients(recipe.getIngredients()));
        ingredientsLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        ingredientsLabel.setForeground(new Color(90, 90, 90));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(ingredientsLabel, BorderLayout.CENTER);
        return card;
    }
    
    private String formatIngredients(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getName)
                .limit(6)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(", "));
    }
    
    private JPanel buildFiltersPanel() {
        JPanel filtersPanel = new JPanel();
        filtersPanel.setOpaque(false);
        filtersPanel.setLayout(new GridLayout(0, 2, 10, 10));
        
        cookingTimeField = new JTextField();
        cookingTimeField.setBorder(simpleBorder());
        filtersPanel.add(new JLabel("Max cooking time (min):"));
        filtersPanel.add(cookingTimeField);
        
        dietCombo = new JComboBox<>(new String[]{"None", "Vegetarian", "Vegan", "Gluten Free", "Keto", "Paleo"});
        dietCombo.setBorder(simpleBorder());
        filtersPanel.add(new JLabel("Dietary needs:"));
        filtersPanel.add(dietCombo);
        
        allergensField = new JTextField();
        allergensField.setBorder(simpleBorder());
        filtersPanel.add(new JLabel("Allergens to exclude (csv):"));
        filtersPanel.add(allergensField);
        
        cuisineField = new JTextField();
        cuisineField.setBorder(simpleBorder());
        filtersPanel.add(new JLabel("Cuisine:"));
        filtersPanel.add(cuisineField);
        
        mealTypeCombo = new JComboBox<>(new String[]{"Any", "breakfast", "lunch", "dinner", "snack", "dessert"});
        mealTypeCombo.setBorder(simpleBorder());
        filtersPanel.add(new JLabel("Meal type:"));
        filtersPanel.add(mealTypeCombo);
        
        return filtersPanel;
    }
    
    private javax.swing.border.Border simpleBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 190, 230)),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        );
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(this::renderResults);
    }
}
