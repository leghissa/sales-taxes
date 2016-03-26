package test.exercise.sales.taxes.model;

import test.exercise.sales.taxes.util.DBC;

public class Product {

    private final String name;
    private final Category category;
    private final boolean imported;
    private final double price;

    public Product(String name, Category category, boolean imported, double price) {
        DBC.notBlank(name, "name should not be blank");
        DBC.notNull(category, "category should not be null");
        DBC.precondition(price >=0 , "price should not be less than 0");
        this.name = name;
        this.category = category;
        this.imported = imported;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isImported() {
        return imported;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (imported != product.imported) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (!name.equals(product.name)) return false;
        return category.equals(product.category);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + (imported ? 1 : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
