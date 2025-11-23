package view;

import interface_adapter.generate_meal_plan.MealPlanController;
import interface_adapter.generate_meal_plan.MealPlanViewModel;
import interface_adapter.generate_meal_plan.MealPlanState;
import interface_adapter.navigation.NavigationController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class MealPlanningPageView extends JPanel implements PropertyChangeListener {
    private String username;

    private final NavigationController navigationController;
    private final MealPlanController controller;
    private final MealPlanViewModel viewModel;

    private JComboBox<String> dietCombo;
    private JComboBox<String> calorieCombo;
    private JComboBox<Integer> mealsPerDayCombo;
    private JButton generateButton;
    private Map<String, java.util.List<String>> savedMealPlan;

    private JPanel resultPanel;

    private void styleComboBox(JComboBox<?> combo) {
        combo.setBackground(Color.WHITE);
        combo.setForeground(new Color(75, 0, 130));
        combo.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 220), 2, true)); // 圆角边框
        combo.setFont(new Font("Arial", Font.BOLD, 14));
        combo.setPreferredSize(new Dimension(250, 30));
    }

    public MealPlanningPageView(String username,
                                NavigationController navigationController,
                                MealPlanController controller,
                                MealPlanViewModel viewModel) {
        this.username = username;
        this.navigationController = navigationController;
        this.controller = controller;
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        SidebarView sidebar = new SidebarView(navigationController, username, null);
        add(sidebar, BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 235, 255));
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(240, 235, 255));
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dietCombo = new JComboBox<>(new String[]{"None", "Vegetarian", "Vegan"});
        styleComboBox(dietCombo);

        calorieCombo = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        styleComboBox(calorieCombo);

        mealsPerDayCombo = new JComboBox<>(new Integer[]{1, 2, 3});
        styleComboBox(mealsPerDayCombo);

        generateButton = new JButton("Generate Weekly Plan");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(138, 43, 226));
        generateButton.setFocusPainted(false);
        generateButton.setBorderPainted(false);
        generateButton.setOpaque(true);
        generateButton.addActionListener(e -> {
            String diet = (String) dietCombo.getSelectedItem();
            String calorieLevel = (String) calorieCombo.getSelectedItem();
            Integer mealsPerDay = (Integer) mealsPerDayCombo.getSelectedItem();

            controller.execute(diet, calorieLevel, mealsPerDay);
        });

        JButton savePlanButton = new JButton("Save this Plan");
        savePlanButton.setFont(new Font("Arial", Font.BOLD, 14));
        savePlanButton.setForeground(Color.WHITE);
        savePlanButton.setBackground(new Color(138, 43, 226));
        savePlanButton.setFocusPainted(false);
        savePlanButton.setBorderPainted(false);
        savePlanButton.setOpaque(true);
        savePlanButton.addActionListener(e -> {
            MealPlanState state = viewModel.getState();
            if (state.getMealPlan() != null && !state.getMealPlan().isEmpty()) {
                savedMealPlan = new LinkedHashMap<>(state.getMealPlan());
                gateways.MealPlanStorage.saveMealPlan(username, savedMealPlan);

                JOptionPane.showMessageDialog(
                        this,
                        "Weekly Meal Plan saved!",
                        "Saved",
                        JOptionPane.INFORMATION_MESSAGE
                );

                renderResults(state);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No meal plan to save yet.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        JLabel dietLabel = new JLabel("Diet:");
        dietLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dietLabel.setForeground(new Color(75, 0, 130));
        inputPanel.add(dietLabel);
        inputPanel.add(dietCombo);

        JLabel calorieLabel = new JLabel("Calorie Level:");
        calorieLabel.setFont(new Font("Arial", Font.BOLD, 14));
        calorieLabel.setForeground(new Color(75, 0, 130));
        inputPanel.add(calorieLabel);
        inputPanel.add(calorieCombo);

        JLabel mealsPerDayLabel = new JLabel("Meals Per Day:");
        mealsPerDayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mealsPerDayLabel.setForeground(new Color(75, 0, 130));
        inputPanel.add(mealsPerDayLabel);
        inputPanel.add(mealsPerDayCombo);

        inputPanel.add(savePlanButton);
        inputPanel.add(generateButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        resultPanel = new JPanel();
        resultPanel.setBackground(new Color(240, 235, 255));
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        savedMealPlan = gateways.MealPlanStorage.loadMealPlan(username);
        if (savedMealPlan != null) {
            MealPlanState state = viewModel.getState();
            state.setMealPlan(savedMealPlan);
            renderResults(state);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MealPlanState state = (MealPlanState) evt.getNewValue();
        renderResults(state);
    }


    private void renderResults(MealPlanState state) {

        resultPanel.removeAll();

        if (state.getErrorMessage() != null) {
            JLabel error = new JLabel(state.getErrorMessage());
            error.setForeground(Color.RED);
            resultPanel.add(error);
            revalidate();
            repaint();
            return;
        }

        state.getMealPlan().forEach((day, meals) -> {
            Color loginPurple = new Color(75, 0, 130);

            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
            dayPanel.setBackground(Color.WHITE);

            dayPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 200, 240), 1, true),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));

            dayPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel dayLabel = new JLabel(day.substring(0, 1).toUpperCase() + day.substring(1));
            dayLabel.setFont(new Font("Arial", Font.BOLD, 16));
            dayLabel.setForeground(loginPurple);
            dayLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

            dayPanel.add(dayLabel);

            meals.forEach(mealTitle -> {
                JLabel mealLabel = new JLabel("• " + mealTitle);
                mealLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                mealLabel.setForeground(new Color(90, 60, 160));
                mealLabel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 0));

                mealLabel.setForeground(new Color(90, 60, 160));
                mealLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                mealLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        JOptionPane.showMessageDialog(
                                null,
                                "You clicked on: " + mealTitle,
                                "Meal Selected",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                });
                dayPanel.add(mealLabel);
            });

            JLabel summary = new JLabel("Today’s Meal Overview");
            summary.setFont(new Font("Arial", Font.ITALIC, 12));
            summary.setForeground(new Color(130, 120, 150));
            summary.setBorder(BorderFactory.createEmptyBorder(8, 2, 0, 0));

            resultPanel.add(summary);
            resultPanel.add(dayPanel);
            resultPanel.add(Box.createVerticalStrut(12));
        });

        revalidate();
        repaint();
    }


    public static void show(String username,
                            NavigationController navigationController,
                            MealPlanController controller,
                            MealPlanViewModel viewModel
                            ) {

        SwingUtilities.invokeLater(() -> {

            for (Frame frame : Frame.getFrames()) {
                if (frame.isVisible()) {
                    frame.dispose();
                }
            }

            JFrame frame = new JFrame("Snack Overflow - Meal Planning");
            frame.setMinimumSize(new Dimension(900, 600));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            MealPlanningPageView page =
                    new MealPlanningPageView(username, navigationController, controller, viewModel);

            frame.setContentPane(page);
            frame.setVisible(true);
        });
    }
}