package interface_adapter.tagging;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for the Tagging and Add Tag feature.
 */
public class TaggingViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private TaggingState state = new TaggingState();

    public TaggingState getState() {
        return state;
    }

    public void setState(TaggingState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("tagging", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
