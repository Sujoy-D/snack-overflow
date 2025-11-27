package entity;

public class Ingredient {
    private final String name;
    private final String quantity;
    private final String unit;

    public Ingredient(String name, String quantity, String unit) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name required");
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
