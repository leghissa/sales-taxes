package test.exercise.application;

import test.exercise.application.taxes.SalesTaxCalculator;
import test.exercise.model.PurchasedItem;
import test.exercise.model.Receipt;
import test.exercise.model.ShoppingBasket;
import test.exercise.util.DBC;

public class TaxingReceiptCreator implements ReceiptCreator {

    private final SalesTaxCalculator salesTaxCalculator;

    public TaxingReceiptCreator(SalesTaxCalculator salesTaxCalculator) {
        DBC.notNull(salesTaxCalculator, "salesTaxCalculator should not be null");
        this.salesTaxCalculator = salesTaxCalculator;
    }

    @Override
    public Receipt apply(ShoppingBasket shoppingBasket) {
        DBC.notNull(shoppingBasket, "shoppingBasket should not be null");

        final Receipt receipt = new Receipt();
        shoppingBasket.getItems().forEach((p, q) ->
                receipt.addItem(
                    new PurchasedItem(p, salesTaxCalculator.apply(p), q)
                )
        );
        return receipt;
    }

}
