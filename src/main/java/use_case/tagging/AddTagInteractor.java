package use_case.tagging;

/** Interactor for the Add Tag use case.
 * Implements the rules for adding a tag(no symbols, less than 20 characters and no duplicates)
 * Add tag to the recipe.
 */

import data_access.TaggingDataAccessInterface;
import entity.Recipe;
import entity.Tag;

public class AddTagInteractor implements AddTagInputBoundary {
    private final TaggingDataAccessInterface recipeGateway;
    private final AddTagOutputBoundary presenter;

    public AddTagInteractor(TaggingDataAccessInterface recipeGateway, AddTagOutputBoundary presenter) {
        this.recipeGateway = recipeGateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddTagInputData tagData) {
        String name = tagData.getTagName().trim();

        if (name.length() > 20) {
            presenter.present(new AddTagOutputData(false, "Invalid tag name, too long"));
            return;
        }
        for (char c1: name.toCharArray()) {
            if (!Character.isLetterOrDigit(c1)) {
                presenter.present(new AddTagOutputData(false, "Invalid tag name"));
                return;
            }
        }

        Recipe recipe = recipeGateway.getRecipebyId(tagData.getRecipeId());

        boolean exists = false;
        for (Tag tag: recipe.getTags()) {
            if (tag.getName().equalsIgnoreCase(name)) {
                exists = true;
                break;
            }
        }

        if (exists) {
            presenter.present(new AddTagOutputData(false, "Tag already exists"));
            return;
        }

        recipe.getTags().add(new Tag(0, name));
        recipeGateway.saveRecipe(recipe);
        presenter.present(new AddTagOutputData(true, "Tag Added"));


    }
}
