package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.PurchasedItem;
import test.exercise.sales.taxes.model.Receipt;
import test.exercise.sales.taxes.model.ShoppingBasket;
import test.exercise.sales.taxes.util.DBC;

public class CreateReceiptImpl implements CreateReceipt {

    private final CalculateSalesTax calculateSalesTax;

    public CreateReceiptImpl(CalculateSalesTax calculateSalesTax) {
        DBC.notNull(calculateSalesTax, "calculateSalesTax should not be null");
        this.calculateSalesTax = calculateSalesTax;
    }

    @Override
    public Receipt apply(ShoppingBasket shoppingBasket) {
        DBC.notNull(shoppingBasket, "shoppingBasket should not be null");

        final Receipt receipt = new Receipt();
        shoppingBasket.getItems().forEach((p, q) ->
                receipt.addItem(
                    new PurchasedItem(p, calculateSalesTax.apply(p)),
                    q
                )
        );
        return receipt;
    }

}
