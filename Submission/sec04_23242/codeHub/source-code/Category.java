interface Taxable {
    double FOOD_TAX = 0.05;
    double SHOP_TAX = 0.1;
    double OTHER_TAX = 0.08;
    double calculateTax(double amount);
}

abstract class Category implements Taxable {
    private int id;
    private String name, description;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getCategoryType();
}

class ShoppingCategory extends Category {
    public ShoppingCategory(int id) {
        super(id, "Shopping", "Shopping Transaction");
    }

    public String getCategoryType() {
        return "Shopping";
    }

    public double calculateTax(double amount) {
        return amount * SHOP_TAX;
    }
}

class FoodCategory extends Category {
    public FoodCategory(int id) {
        super(id, "Food", "Food Transaction");
    }

    public String getCategoryType() {
        return "Food";
    }

    public double calculateTax(double amount) {
        return amount * FOOD_TAX;
    }
}

class Deposit extends Category {
    public Deposit(int id) {
        super(id, "Deposit", "Deposit Money to Account");
    }

    public String getCategoryType() {
        return "Deposit";
    }

    public double calculateTax(double amount) {
        return 0;
    }
}

class OtherCategory extends Category {
    public OtherCategory(int id) {
        super(id, "Other", "Other Transaction");
    }

    public String getCategoryType() {
        return "Other";
    }

    public double calculateTax(double amount) {
        return amount * OTHER_TAX;
    }
}