package interface_adapter.checkout_recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CheckoutRecipeViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CheckoutRecipeState state = new CheckoutRecipeState();

    public CheckoutRecipeState getState() {
        return state;
    }

    public void setState(CheckoutRecipeState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("checkoutRecipe", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
