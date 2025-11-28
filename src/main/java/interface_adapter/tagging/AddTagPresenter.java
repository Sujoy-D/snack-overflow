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
    public void present(AddTagOutputData outputTagData) {
        TaggingState state = taggingViewModel.getState();
        state.setMessage(outputTagData.getMessage());
        state.setSuccess(outputTagData.isSuccess());
        taggingViewModel.setState(state);
        taggingViewModel.firePropertyChanged();

    }
}
