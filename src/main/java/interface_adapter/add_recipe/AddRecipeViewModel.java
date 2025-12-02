package interface_adapter.add_recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddRecipeViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Notifies all registered listeners that the view model's message has changed.
     *
     * <p>
     * Fires a {@link PropertyChangeEvent} with the property name "addRecipeMessage",
     * the old value as null, and the new value as the current message.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("addRecipeMessage", null, message);
    }

    /**
     * Registers a {@link PropertyChangeListener} to receive notifications when
     * properties in the view model change.
     *
     * @param listener the listener to add, which will be notified of property changes
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

}
