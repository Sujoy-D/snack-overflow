package interface_adapter.generate_meal_plan;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for the Meal Planning page.
 * Stores the MealPlanState and notifies any listeners (Views)
 * whenever the state changes.
 */
public class MealPlanViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private MealPlanState state = new MealPlanState();

    public MealPlanState getState() {
        return state;
    }

    public void setState(MealPlanState state) {
        this.state = state;
    }

    /**
     * Notify the view that the state has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("mealPlan", null, this.state);
    }

    /**
     * Add a listener (usually a View) to watch for updates.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

