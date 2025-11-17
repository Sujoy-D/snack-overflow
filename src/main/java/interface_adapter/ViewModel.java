package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewModel {
    private String viewName;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    public ViewModel(String viewName) {
        this.viewName = viewName;
    }
    
    public String getViewName() {
        return viewName;
    }
    
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, null);
    }
    
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, null);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
