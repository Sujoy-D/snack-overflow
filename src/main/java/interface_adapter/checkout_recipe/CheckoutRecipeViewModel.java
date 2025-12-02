package interface_adapter.checkout_recipe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for the Checkout Recipe Use Case.
 */
public class CheckoutRecipeViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private CheckoutRecipeState state = new CheckoutRecipeState();

    public CheckoutRecipeState getState() {
        return state;
    }

    public void setState(CheckoutRecipeState state) {
        this.state = state;
    }

    /**
     * Fires the property change of this ViewModel's support.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("checkoutRecipe", null, this.state);
    }

    /**
     * Adds a property change listener to this ViewModel's support.
     * @param listener the property change listener to add to this ViewModel's support.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
