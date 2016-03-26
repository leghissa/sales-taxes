package test.exercise.sales.taxes.model;

import test.exercise.sales.taxes.util.DBC;

public class PurchasedItem {

    private final Product product;
    private final double salesTax;

    public PurchasedItem(Product product, double salesTax) {
        DBC.notNull(product, "product should not be null");
        DBC.precondition(salesTax >= 0, "salesTax should not be less than 0");
        this.product = product;
        this.salesTax = salesTax;
    }

    public Product getProduct() {
        return product;
    }

    public double getSalesTax() {
        return salesTax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasedItem that = (PurchasedItem) o;

        return product.equals(that.product);

    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }
}
