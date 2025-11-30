package view;

import data_access.AddRecipeDataAccessInterface;
import data_access.UserFileDataAccess;
import entity.Ingredient;
import entity.Recipe;
import interface_adapter.navigation.NavigationController;
import interface_adapter.navigation.NavigationViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SavedRecipesView {
    private static final String FONT_ARIAL = "Arial";
    public static JFrame show(String username, NavigationController navigationController,
                              AddRecipeDataAccessInterface recipeDataAccess) {
        JFrame frame = new JFrame("Snack Overflow - Saved Recipes");
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

        JLabel title = new JLabel("Your Saved Recipes");
        title.setFont(new Font(FONT_ARIAL, Font.BOLD, 22));
        title.setForeground(new Color(90, 0, 120));
        title.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        List<Recipe> recipes = recipeDataAccess.loadRecipes(username);

        if (recipes == null || recipes.isEmpty()) {
            JLabel emptyLabel = new JLabel("No Recipes Found");
            emptyLabel.setFont(new Font(FONT_ARIAL, Font.PLAIN, 14));
            emptyLabel.setForeground(new Color(90, 90, 90));
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            list.add(Box.createVerticalStrut(10));
            list.add(emptyLabel);
        } else {
            for (Recipe recipe : recipes) {
                JPanel card = createRecipeSection(recipe, username, navigationController);
                list.add(card);
                list.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    private static JPanel createRecipeSection(Recipe recipe, String username,
                                              NavigationController navigationController) {
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
