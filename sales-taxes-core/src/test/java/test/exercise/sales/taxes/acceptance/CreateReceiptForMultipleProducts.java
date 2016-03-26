package test.exercise.sales.taxes.acceptance;

import org.junit.Test;
import test.exercise.sales.taxes.application.ApplicatioConfiguration;
import test.exercise.sales.taxes.application.ReceiptCreator;
import test.exercise.sales.taxes.model.Product;
import test.exercise.sales.taxes.model.PurchasedItem;
import test.exercise.sales.taxes.model.Receipt;
import test.exercise.sales.taxes.model.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static test.exercise.sales.taxes.application.ApplicatioConfiguration.FixedCategories.*;

public class CreateReceiptForMultipleProducts {

    private ReceiptCreator receiptCreator = ApplicatioConfiguration.receiptCreator();

    @Test
    public void createReceiptForMultipleProducts(){
        final Product book = new Product("book", BOOK.getCategory(), false, 12.49);
        final Product musicCd = new Product("music CD", OTHER.getCategory(), false, 14.99);
        final Product chocolateBar = new Product("chocolate bar", FOOD.getCategory(), false, 0.85);
        final Product importedBoxOfChocolates = new Product("box of chocolates", FOOD.getCategory(), true, 10.00);
        final Product importedBottleOfPerfume = new Product("bottle of perfume", OTHER.getCategory(), true, 47.50);
        final Product packetOfHeadachePills = new Product("packet of headache pills", MEDICAL_PRODUCT.getCategory(), false, 9.75);

        final ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.add(book, 1);
        shoppingBasket.add(musicCd, 1);
        shoppingBasket.add(chocolateBar, 1);
        shoppingBasket.add(importedBoxOfChocolates, 3);
        shoppingBasket.add(importedBottleOfPerfume, 2);
        shoppingBasket.add(packetOfHeadachePills, 1);

        final Receipt receipt = receiptCreator.apply(shoppingBasket);

        final List<PurchasedItem> purchasedItems = new ArrayList<>(receipt.getPurchasedItems());
        assertThat(purchasedItems).hasSize(6);
        assertPurchasedItem(purchasedItems.get(0), book, 12.49, 1);
        assertPurchasedItem(purchasedItems.get(1), musicCd, 16.49, 1);
        assertPurchasedItem(purchasedItems.get(2), chocolateBar, 0.85, 1);
        assertPurchasedItem(purchasedItems.get(3), importedBoxOfChocolates, 10.50 * 3, 3);
        assertPurchasedItem(purchasedItems.get(4), importedBottleOfPerfume, 54.65 * 2, 2);
        assertPurchasedItem(purchasedItems.get(5), packetOfHeadachePills, 9.75, 1);

        assertThat(receipt.getSalesTaxes()).isEqualTo(17.30);
        assertThat(receipt.getTotal()).isEqualTo(180.38);
    }

    private void assertPurchasedItem(PurchasedItem purchasedItem, Product expectedProduct, double expectedPrice, int expectedQuantity) {
        assertThat(purchasedItem.getProduct()).isEqualTo(expectedProduct);
        assertThat(purchasedItem.getTotal()).isEqualTo(expectedPrice,offset(0.00000000000001));
        assertThat(purchasedItem.getQuantity()).isEqualTo(expectedQuantity);
    }

}
