package view;

import interface_adapter.tagging.AddTagController;
import interface_adapter.tagging.TaggingState;
import interface_adapter.tagging.TaggingViewModel;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import use_case.tagging.AddTagInteractor;
import interface_adapter.tagging.AddTagPresenter;
import data_access.TaggingDataAccessInterface;
import entity.Recipe;
import entity.Tag;


public class AddTagView extends JPanel implements PropertyChangeListener {
    private static final String FONT_ARIAL = "Arial";
    private final int recipeId;
    private final AddTagController addTagController;
    private final TaggingViewModel taggingViewModel;
    private final JFrame tagFrame;
    private final JTextField tagNameTextField;
    private final JLabel message;

    public AddTagView(JFrame tagFrame, int recipeId, AddTagController addTagController, TaggingViewModel taggingViewModel) {
        this.tagFrame = tagFrame;
        this.recipeId = recipeId;
        this.addTagController = addTagController;
        this.taggingViewModel = taggingViewModel;
        this.taggingViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 235, 255));
        GridBagConstraints c = new GridBagConstraints();

        JLabel title = new JLabel("Add Your Tag");
        title.setFont(new Font(FONT_ARIAL, Font.BOLD, 28));
        title.setForeground(new Color(75, 0, 120));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.CENTER;
        add(title, c);

        //Tag name textfield
        tagNameTextField = new JTextField(20);
        tagNameTextField.setFont(new Font(FONT_ARIAL, Font.BOLD, 14));
        tagNameTextField.setBackground(Color.WHITE);
        tagNameTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 20, 0, 20);
        c.anchor = GridBagConstraints.CENTER;
        add(tagNameTextField, c);

        //Messages after tag entered
        message = new JLabel(" ");
        message.setFont(new Font(FONT_ARIAL, Font.BOLD, 14));
        message.setForeground(Color.RED);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        add(message, c);

        //Add Tag button
        JButton addTagButton = new JButton("Add Tag");
        addTagButton.setFont(new Font(FONT_ARIAL, Font.BOLD, 14));
        addTagButton.setBackground(Color.BLUE);
        addTagButton.setForeground(Color.WHITE);
        addTagButton.setFocusPainted(false);
        addTagButton.setBorderPainted(false);
        addTagButton.setOpaque(true);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.CENTER;
        add(addTagButton, c);
        addTagButton.addActionListener(e -> submitTag());
        tagNameTextField.addActionListener(e -> submitTag());
    }

    private static void showAddTagPage(int recipeId, AddTagController addTagController,
                                       TaggingViewModel taggingViewModel) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Tag");
            frame.setMinimumSize(new Dimension(480, 360));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            AddTagView view = new AddTagView(frame, recipeId, addTagController, taggingViewModel);
            frame.setContentPane(view);
            frame.pack();
            frame.setVisible(true);
        });
    }
    private void submitTag() {
        String newTagName = tagNameTextField.getText().trim();
        if (newTagName.isEmpty()) {
            message.setText("Please enter tag name");
            return;
        }
        addTagController.addTag(recipeId, newTagName);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        TaggingState taggingState = taggingViewModel.getState();
        message.setText(taggingState.getMessage());
        if (!taggingState.isSuccess()) {
            tagNameTextField.setText("");
            return;
        }
        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> tagFrame.dispose());
        timer.setRepeats(false);
        timer.start();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create temporary fake DAO for testing
            TaggingDataAccessInterface fakeDao = new TaggingDataAccessInterface() {
                @Override
                public Recipe getRecipebyId(int id) {
                    ArrayList<String> ingredients = new ArrayList<>();
                    ingredients.add("Test 1");
                    ingredients.add("Test 2");
                    ArrayList<Tag> tags = new ArrayList<>();
                    return new Recipe(id, "Test Recipe", ingredients, "cook it", "canadian :)",
                            10, "Dinner", 1, tags);
                }


                @Override
                public void saveRecipe(Recipe recipe) {
                    System.out.println("Saved recipe with tags: " + recipe.getTags());
                }
            };

            // Build the clean-arch stack
            TaggingViewModel vm = new TaggingViewModel();
            AddTagPresenter presenter = new AddTagPresenter(vm);
            AddTagInteractor interactor = new AddTagInteractor(fakeDao, presenter);
            AddTagController controller = new AddTagController(interactor);

            // Temporary frame for testing
            JFrame frame = new JFrame("Temporary Recipe Page");
            frame.setMinimumSize(new Dimension(720, 480));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBackground(new Color(240, 235, 255));
            GridBagConstraints gbc = new GridBagConstraints();

            JButton createTagButton = new JButton("New Tag");
            createTagButton.setFont(new Font(FONT_ARIAL, Font.BOLD, 14));
            createTagButton.setBackground(new Color(138, 43, 226));
            createTagButton.setForeground(Color.WHITE);
            createTagButton.setFocusPainted(false);
            createTagButton.setBorderPainted(false);
            createTagButton.setOpaque(true);
            createTagButton.setPreferredSize(new Dimension(120, 40));

            // open the real AddTag popup
            createTagButton.addActionListener(e ->
                    AddTagView.showAddTagPage(123, controller, vm)
            );

            mainPanel.add(createTagButton, gbc);
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }
}