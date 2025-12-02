package interface_adapter.generate_meal_plan;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for the Meal Planning page.
 * Stores the MealPlanState and notifies any listeners (Views)
 * whenever the state changes.
 */
public class MealPlanViewModel {

    /** Supports property change notifications for the view. */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /** The current state of the meal plan. */
    private MealPlanState state = new MealPlanState();

    /**
     * Returns the current meal plan state.
     *
     * @return the current {@link MealPlanState}
     */
    public MealPlanState getState() {
        return state;
    }

    /**
     * Sets the meal plan state.
     *
     * @param state the new {@link MealPlanState}
     */
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
     * Adds a listener to observe state changes.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}

