package test.exercise.sales.taxes.model;

import test.exercise.sales.taxes.util.DBC;

import java.util.HashMap;
import java.util.Map;

public class Receipt {

    private final Map<PurchasedItem, Integer> purchasedItems = new HashMap<>();
    private double salesTaxes = 0;
    private double total =0 ;

    public void addItem(PurchasedItem purchasedItem, int quantity){
        DBC.notNull(purchasedItem, "purchasedItem should not be null");
        DBC.precondition(quantity > 0, "quantity should not be less than 1");
        DBC.precondition(!purchasedItems.containsKey(purchasedItem), String.format("receipt already contains product %s",purchasedItem.getProduct().getName()));

        purchasedItems.put(purchasedItem, quantity);
        final double salesTax = purchasedItem.getSalesTax();
        salesTaxes+= salesTax * quantity;
        total+= (salesTax+purchasedItem.getProduct().getPrice()) * quantity;
    }

    public Map<PurchasedItem, Integer> getPurchasedItems() {
        return purchasedItems;
    }

    public double getSalesTaxes() {
        return salesTaxes;
    }

    public double getTotal() {
        return total;
    }
}
