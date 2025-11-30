package view;

import interface_adapter.tagging.AddTagController;
import interface_adapter.tagging.TaggingState;
import interface_adapter.tagging.TaggingViewModel;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AddTagView extends JPanel implements PropertyChangeListener {
    private static final String FONT_ARIAL = "Arial";
    private final String username;
    private final int recipeId;
    private final AddTagController addTagController;
    private final TaggingViewModel taggingViewModel;
    private final JFrame tagFrame;
    private final JTextField tagNameTextField;
    private final JLabel message;

    public AddTagView(JFrame tagFrame, String username, int recipeId, AddTagController addTagController, TaggingViewModel taggingViewModel) {
        this.tagFrame = tagFrame;
        this.username = username;
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

    private static void showAddTagPage(String username, int recipeId, AddTagController addTagController,
                                       TaggingViewModel taggingViewModel) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Add Tag");
            frame.setMinimumSize(new Dimension(480, 360));
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            AddTagView view = new AddTagView(frame, username, recipeId, addTagController, taggingViewModel);
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
        addTagController.addTag(username, recipeId, newTagName);
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
}