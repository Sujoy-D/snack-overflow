package use_case.generate_meal_plan;

/**
 * Input Data for the Generate Weekly Meal Plan Use Case.
 */
public class MealPlanInputData {

    private final String diet;          // None, Vegetarian, Vegan
    private final String calorieLevel;  // Low, Medium, High
    private final int mealsPerDay;      // 1, 2, or 3

    public MealPlanInputData(String diet, String calorieLevel, int mealsPerDay) {
        this.diet = diet;
        this.calorieLevel = calorieLevel;
        this.mealsPerDay = mealsPerDay;
    }

    public String getDiet() {
        return diet;
    }

    public String getCalorieLevel() {
        return calorieLevel;
    }

    public int getMealsPerDay() {
        return mealsPerDay;
    }
}

