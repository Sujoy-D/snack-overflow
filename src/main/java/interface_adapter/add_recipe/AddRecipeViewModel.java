package interface_adapter.add_recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddRecipeViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        this.message = m;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("addRecipeMessage", null, message);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
