package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.PurchasedItem;
import test.exercise.sales.taxes.model.Receipt;
import test.exercise.sales.taxes.model.ShoppingBasket;
import test.exercise.sales.taxes.util.DBC;

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
