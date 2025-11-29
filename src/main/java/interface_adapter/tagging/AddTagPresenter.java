package interface_adapter.tagging;

import use_case.tagging.AddTagOutputBoundary;
import use_case.tagging.AddTagOutputData;
/**
 * Presenter for the Add Tag use case.
 */
public class AddTagPresenter implements AddTagOutputBoundary {
    private final TaggingViewModel taggingViewModel;

    public AddTagPresenter(TaggingViewModel taggingViewModel) {
        this.taggingViewModel = taggingViewModel;
    }

    @Override
    public void prepareSuccessView(AddTagOutputData outputTagData) {
        TaggingState state = taggingViewModel.getState();
        state.setMessage("Tag '" + outputTagData.getNewTag() + "' added successfully");
        state.setSuccess(true);
        taggingViewModel.setState(state);
        taggingViewModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        TaggingState state = taggingViewModel.getState();
        state.setMessage(errorMessage);
        state.setSuccess(false);
        taggingViewModel.setState(state);
        taggingViewModel.firePropertyChanged();
    }
}
