package test.exercise.application;

import test.exercise.model.Receipt;
import test.exercise.model.ShoppingBasket;

public interface ReceiptCreator {

    Receipt apply(ShoppingBasket shoppingBasket);
}
