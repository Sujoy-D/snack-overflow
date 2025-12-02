package use_case.generate_meal_plan;

/**
 * Input Data for the Generate Weekly Meal Plan Use Case.
 */
public class MealPlanInputData {
    /** Dietary preference: None, Vegetarian, or Vegan. */
    private final String diet;

    /** Calorie level preference: Low, Medium, or High. */
    private final String calorieLevel;

    /** Number of meals per day: 1, 2, or 3. */
    private final int mealsPerDay;

    /**
     * Constructs the input data for generating a weekly meal plan.
     *
     * @param diet the dietary preference
     * @param calorieLevel the calorie level preference
     * @param mealsPerDay number of meals per day
     */
    public MealPlanInputData(String diet, String calorieLevel, int mealsPerDay) {
        this.diet = diet;
        this.calorieLevel = calorieLevel;
        this.mealsPerDay = mealsPerDay;
    }

    /**
     * Returns the dietary preference.
     *
     * @return the diet type
     */
    public String getDiet() {
        return diet;
    }

    /**
     * Returns the calorie level preference.
     *
     * @return the calorie level
     */
    public String getCalorieLevel() {
        return calorieLevel;
    }

    /**
     * Returns the number of meals per day.
     *
     * @return the meal count per day
     */
    public int getMealsPerDay() {
        return mealsPerDay;
    }
}

