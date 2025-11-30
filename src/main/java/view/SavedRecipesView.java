package view;

import data_access.AddRecipeDataAccessInterface;
import entity.Ingredient;
import entity.Recipe;
import entity.Tag;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SavedRecipesView {
    private final String username;
    private final NavigationController navigationController;
    private final AddRecipeDataAccessInterface recipeDataAccess;

    private JFrame frame;
    private JPanel list;
    private JTextField tagFilterField;
    private JLabel statusLabel;
    private List<Recipe> allRecipes = new ArrayList<>();
    private static final String FONT_ARIAL = "Arial";

    private SavedRecipesView(String username, NavigationController navigationController,
                             AddRecipeDataAccessInterface recipeDataAccess) {
        this.username = username;
        this.navigationController = navigationController;
        this.recipeDataAccess = recipeDataAccess;
    }

    public static JFrame show(String username, NavigationController navigationController,
                              AddRecipeDataAccessInterface recipeDataAccess) {
        SavedRecipesView view = new SavedRecipesView(username, navigationController, recipeDataAccess);
        view.buildUI();
        return view.frame;
    }

    private void buildUI() {
        frame = new JFrame("Snack Overflow - Saved Recipes");
        frame.setMinimumSize(new Dimension(720, 480));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        SidebarView sidebar = new SidebarView(navigationController, username, frame);
        frame.add(sidebar, BorderLayout.WEST);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 235, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(panel, BorderLayout.CENTER);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Your Saved Recipes");
        title.setFont(new Font(FONT_ARIAL, Font.BOLD, 22));
        title.setForeground(new Color(90, 0, 120));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(title);
        header.add(Box.createVerticalStrut(8));

        JPanel filterRow = new JPanel(new BorderLayout(8, 0));
        filterRow.setOpaque(false);

        JLabel filterLabel = new JLabel("Filter by tags (comma separated):");
        filterLabel.setFont(new Font(FONT_ARIAL, Font.PLAIN, 13));

        tagFilterField = new JTextField();
        tagFilterField.setFont(new Font(FONT_ARIAL, Font.PLAIN, 13));
        tagFilterField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 190, 230)),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));

        JButton filterButton = new JButton("Filter");
        filterButton.setFont(new Font(FONT_ARIAL, Font.BOLD, 13));
        filterButton.setBackground(new Color(65, 0, 140));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        filterButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0), 2),
                BorderFactory.createEmptyBorder(6, 10, 4, 10)
        ));
        filterButton.setOpaque(true);
        filterButton.addActionListener(e -> applyTagFilter());

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font(FONT_ARIAL, Font.PLAIN, 13));
        clearButton.addActionListener(e -> {
            tagFilterField.setText("");
            renderRecipeList(allRecipes);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(filterButton);
        buttonPanel.add(clearButton);

        JPanel leftFilter = new JPanel(new BorderLayout(6, 0));
        leftFilter.setOpaque(false);
        leftFilter.add(filterLabel, BorderLayout.WEST);
        leftFilter.add(tagFilterField, BorderLayout.CENTER);

        filterRow.add(leftFilter, BorderLayout.CENTER);
        filterRow.add(buttonPanel, BorderLayout.EAST);

        header.add(filterRow);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font(FONT_ARIAL, Font.PLAIN, 12));
        statusLabel.setForeground(new Color(90, 90, 90));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(Box.createVerticalStrut(6));
        header.add(statusLabel);

        panel.add(header, BorderLayout.NORTH);

        list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        panel.add(scrollPane, BorderLayout.CENTER);

        allRecipes = recipeDataAccess.loadRecipes(username);
        renderRecipeList(allRecipes);

        frame.pack();
        frame.setVisible(true);
    }

    private void applyTagFilter() {
        String rawInput = tagFilterField.getText();
        if (rawInput == null || rawInput.trim().isEmpty()) {
            statusLabel.setText("Showing all saved recipes.");
            statusLabel.setForeground(new Color(90, 90, 90));
            renderRecipeList(allRecipes);
            return;
        }
        String[] pieces = rawInput.split(",");
        List<String> wanted = new ArrayList<>();
        for (String piece : pieces) {
            String t = piece.trim().toLowerCase();
            if (!t.isEmpty()) {
                wanted.add(t);
            }
        }

        if (wanted.isEmpty()) {
            statusLabel.setText("Showing all saved recipes.");
            statusLabel.setForeground(new Color(90, 90, 90));
            renderRecipeList(allRecipes);
            return;
        }

        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : allRecipes) {
            if (matchesAllTags(r, wanted)) {
                filtered.add(r);
            }
        }

        if (filtered.isEmpty()) {
            statusLabel.setText("No recipes found.");
            statusLabel.setForeground(new Color(200, 0, 0));
        } else {
            statusLabel.setText("Showing" + filtered.size() + " recipe(s) matching your tags.");
            statusLabel.setForeground(new Color(33, 150, 83));
        }

        renderRecipeList(filtered);
    }

    private boolean matchesAllTags(Recipe recipe, List<String> wantedTags) {
        List<Tag> tags = recipe.getTags();
        if (tags == null || tags.isEmpty()) {
            return false;
        }
        List<String> contains  = new ArrayList<>();
        for (Tag t : tags) {
            if (t != null && t.getName() != null) {
                contains.add(t.getName().toLowerCase());
            }
        }
        if (contains.isEmpty()) return false;

        for (String wantedTag : wantedTags) {
            if (!contains.contains(wantedTag)) {
                return false;
            }
        }
        return true;
    }

    private void renderRecipeList(List<Recipe> recipes) {
        list.removeAll();
        if (recipes == null || recipes.isEmpty()) {
            JLabel emptyLabel = new  JLabel("No recipes found.");
            emptyLabel.setFont(new Font(FONT_ARIAL, Font.PLAIN, 14));
            emptyLabel.setForeground(new Color(90, 90, 90));
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            list.add(Box.createVerticalStrut(10));
            list.add(emptyLabel);
        } else {
            for (Recipe recipe : recipes) {
                JPanel section = createRecipeSection(recipe);
                list.add(section);
                list.add(Box.createVerticalStrut(10));
            }
        }

        list.revalidate();
        list.repaint();
        if (frame != null) {
            frame.revalidate();
            frame.repaint();
        }
    }

    private static JPanel createRecipeSection(Recipe recipe) {
        JPanel recipeSection = new JPanel(new BorderLayout());
        recipeSection.setOpaque(true);
        recipeSection.setBackground(Color.WHITE);
        recipeSection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 235)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        recipeSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel(recipe.getTitle());
        title.setFont(new Font(FONT_ARIAL, Font.BOLD, 16));
        title.setForeground(new Color(60, 60, 60));

        String ingredientsText = "Ingredients: " + formatIngredients(recipe.getIngredients());
        JLabel ingredientsLabel = new JLabel(ingredientsText);
        ingredientsLabel.setFont(new Font(FONT_ARIAL, Font.PLAIN, 13));
        ingredientsLabel.setForeground(new Color(90, 90, 90));

        recipeSection.add(title, BorderLayout.NORTH);
        recipeSection.add(ingredientsLabel, BorderLayout.CENTER);

        recipeSection.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        recipeSection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int recipeId = recipe.getRecipeId();
                //TODO: Pull up recipe page when implemented. And remove the temporary replacement below
                JOptionPane.showMessageDialog(recipeSection, "Open Recipe ID: " + recipeId, "Recipe Selected",
                        JOptionPane.INFORMATION_MESSAGE);

            }
        });
        return recipeSection;

    }

    private static String formatIngredients(List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return "No ingredients";
        }
        StringBuilder sb = new StringBuilder();
        int limit = Math.min(ingredients.size(), 6);
        for (int i = 0; i < limit; i++) {
            Ingredient ingredient = ingredients.get(i);
            String name = ingredient != null ? ingredient.getName() : null;
            if (name == null || name.isBlank()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(name.trim());
        }
        if (sb.length() == 0) {
            return "No ingredients";
        }
        return sb.toString();
    }
}
