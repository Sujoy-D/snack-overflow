package interface_adapter.similar_recipes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SimilarRecipesViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private SimilarRecipesState state = new SimilarRecipesState();

    public SimilarRecipesState getState() {
        return state;
    }

    public void setState(SimilarRecipesState state) {
        this.state = state;
    }

    /**
     * Notify the view that the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("similarRecipes", null, this.state);
    }

    /**
     * Add a listener (usually a View) to watch for updates.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
