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

    @Override
    public String toString() {
        return name + " " + quantity + " " + unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingredient that = (Ingredient) obj;
        return name.equals(that.name) && 
               quantity.equals(that.quantity) && 
               unit.equals(that.unit);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, quantity, unit);
    }
}
