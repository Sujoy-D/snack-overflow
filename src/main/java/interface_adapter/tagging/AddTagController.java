package interface_adapter.tagging;

import use_case.tagging.AddTagInputBoundary;
import use_case.tagging.AddTagInputData;

public class AddTagController {
    private final AddTagInputBoundary interactor;

    public AddTagController(AddTagInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addTag(String username, int recipeId, String tagName) {
        AddTagInputData inputData = new AddTagInputData(username, recipeId, tagName);
        interactor.execute(inputData);

    }
}
