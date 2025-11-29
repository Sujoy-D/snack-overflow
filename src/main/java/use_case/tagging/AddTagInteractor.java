package use_case.tagging;

/** Interactor for the Add Tag use case.
 * Implements the rules for adding a tag(no symbols, less than 20 characters and no duplicates)
 * Add tag to the recipe.
 */

import data_access.TaggingDataAccessInterface;

import java.util.List;

public class AddTagInteractor implements AddTagInputBoundary {
    private final TaggingDataAccessInterface taggingDataAccess;
    private final AddTagOutputBoundary presenter;

    public AddTagInteractor(TaggingDataAccessInterface taggingDataAccess, AddTagOutputBoundary presenter) {
        this.taggingDataAccess = taggingDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddTagInputData tagData) {
        String username = tagData.getUsername();
        int recipeId = tagData.getRecipeId();
        String tagName = tagData.getTagName().trim();

        if (tagName.isEmpty()) {
            presenter.prepareFailView("Tag name cannot be empty.");
            return;
        }

        if (tagName.length() > 20) {
            presenter.prepareFailView("Invalid tag name, too long.");
            return;
        }

        taggingDataAccess.addTagToRecipe(username, recipeId, tagName);
        List<String> allTags = taggingDataAccess.getTagsForRecipe(username, recipeId);

        AddTagOutputData outputData = new AddTagOutputData(recipeId, tagName, allTags);
        presenter.prepareSuccessView(outputData);
    }
}
