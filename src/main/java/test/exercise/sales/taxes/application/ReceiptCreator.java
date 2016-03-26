package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Receipt;
import test.exercise.sales.taxes.model.ShoppingBasket;

public interface ReceiptCreator {

    Receipt apply(ShoppingBasket shoppingBasket);
}
