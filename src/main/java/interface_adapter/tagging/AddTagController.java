package interface_adapter.tagging;

import use_case.tagging.AddTagInputBoundary;
import use_case.tagging.AddTagInputData;

public class AddTagController {
    private final AddTagInputBoundary interactor;

    public AddTagController(AddTagInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addTag(int recipeId, String tagName) {
        interactor.execute(new AddTagInputData(recipeId, tagName));
    }
}
