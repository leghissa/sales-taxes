package test.exercise.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    private final Receipt receipt = new Receipt();
    private Product product = new Product("some product", new Category("some category"), false, 10);
    private Product product1 = new Product("some product", new Category("some category"), false, 124.99);

    @Test
    public void canCreateEmptyReceipt(){
        assertThat(receipt.getPurchasedItems()).isEmpty();
        assertThat(receipt.getSalesTaxes()).isZero();
        assertThat(receipt.getTotal()).isZero();
    }

    @Test
    public void canAddItem(){
        final double salesTax = 2;
        final int quantity = 1;
        final PurchasedItem purchasedItem = new PurchasedItem(product, salesTax, quantity);

        receipt.addItem(purchasedItem);

        assertThat(receipt.getPurchasedItems()).isEqualTo(singleton(purchasedItem));
        assertThat(receipt.getTotal()).isEqualTo(purchasedItem.getTotal());
        assertThat(receipt.getSalesTaxes()).isEqualTo(purchasedItem.getSalesTax());
    }

    @Test
    public void canAddMultipleItems(){
        final double salesTax = 2;
        final double salesTax1 = 3.15;
        final int quantity = 1;
        final int quantity1 = 3;
        final PurchasedItem purchasedItem = new PurchasedItem(product, salesTax, quantity);
        final PurchasedItem purchasedItem1 = new PurchasedItem(product1, salesTax1, quantity1);

        receipt.addItem(purchasedItem);
        receipt.addItem(purchasedItem1);

        final List<PurchasedItem> purchasedItems = new ArrayList<>(receipt.getPurchasedItems());
        double expectedTotal = purchasedItem.getTotal() + purchasedItem1.getTotal();
        double expectedSalesTax = purchasedItem.getSalesTax() + purchasedItem1.getSalesTax();

        assertThat(receipt.getPurchasedItems()).hasSize(2);
        assertThat(purchasedItems.get(0)).isEqualTo(purchasedItem);
        assertThat(purchasedItems.get(1)).isEqualTo(purchasedItem1);
        assertThat(receipt.getTotal()).isEqualTo(expectedTotal);
        assertThat(receipt.getSalesTaxes()).isEqualTo(expectedSalesTax);
    }

    @Test
    public void cannotAddSameItem(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(String.format("receipt already contains product %s",product.getName()));

        receipt.addItem(new PurchasedItem(product, 1, 1));
        receipt.addItem(new PurchasedItem(product, 1, 2));
    }

}