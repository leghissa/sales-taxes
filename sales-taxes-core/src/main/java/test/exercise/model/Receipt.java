package test.exercise.model;

import test.exercise.util.DBC;

import java.util.LinkedHashSet;
import java.util.Set;

public class Receipt {

    private final Set<PurchasedItem> purchasedItems = new LinkedHashSet<>();
    private double salesTaxes = 0;
    private double total =0 ;

    public void addItem(PurchasedItem purchasedItem){
        DBC.notNull(purchasedItem, "purchasedItem should not be null");
        DBC.precondition(!purchasedItems.contains(purchasedItem), String.format("receipt already contains product %s",purchasedItem.getProduct().getName()));

        purchasedItems.add(purchasedItem);
        salesTaxes+= purchasedItem.getSalesTax();
        total+= purchasedItem.getTotal();
    }

    public Set<PurchasedItem> getPurchasedItems() {
        return purchasedItems;
    }

    public double getSalesTaxes() {
        return salesTaxes;
    }

    public double getTotal() {
        return total;
    }
}
