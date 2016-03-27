package test.exercise.model;

import test.exercise.util.DBC;

public class PurchasedItem {

    private final Product product;
    private final double salesTax;
    private final double totalPrice;
    private final int quantity;

    public PurchasedItem(Product product, double singleSalesTax, int quantity) {
        this.quantity = quantity;
        DBC.notNull(product, "product should not be null");
        DBC.precondition(singleSalesTax >= 0, "singleSalesTax should not be less than 0");
        DBC.precondition(quantity > 0, "quantity should not be less than 1");
        this.product = product;
        this.salesTax = singleSalesTax * quantity;
        this.totalPrice = (product.getPrice() + singleSalesTax) * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public double getTotal(){
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
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
