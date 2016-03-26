package test.exercise.sales.taxes.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
        final PurchasedItem purchasedItem = new PurchasedItem(product, salesTax);

        receipt.addItem(purchasedItem, quantity);

        final double expectedTotal = (product.getPrice() + salesTax) * quantity;
        final double expectedSalesTax = salesTax * quantity;

        assertThat(receipt.getPurchasedItems()).hasSize(1);
        assertThat(receipt.getPurchasedItems().get(purchasedItem)).isEqualTo(quantity);
        assertThat(receipt.getTotal()).isEqualTo(expectedTotal);
        assertThat(receipt.getSalesTaxes()).isEqualTo(expectedSalesTax);
    }

    @Test
    public void canAddMultipleItems(){
        final double salesTax = 2;
        final double salesTax1 = 3.15;
        final int quantity = 1;
        final int quantity1 = 3;
        final PurchasedItem purchasedItem = new PurchasedItem(product, salesTax);
        final PurchasedItem purchasedItem1 = new PurchasedItem(product1, salesTax1);

        receipt.addItem(purchasedItem, quantity);
        receipt.addItem(purchasedItem1, quantity1);

        final double expectedTotal = (product.getPrice() + salesTax) * quantity +  (product1.getPrice() + salesTax1) * quantity1;
        final double expectedSalesTax = salesTax * quantity + salesTax1 * quantity1;

        assertThat(receipt.getPurchasedItems()).hasSize(2);
        assertThat(receipt.getPurchasedItems().get(purchasedItem)).isEqualTo(quantity);
        assertThat(receipt.getPurchasedItems().get(purchasedItem1)).isEqualTo(quantity1);
        assertThat(receipt.getTotal()).isEqualTo(expectedTotal);
        assertThat(receipt.getSalesTaxes()).isEqualTo(expectedSalesTax);
    }

    @Test
    public void cannotAddSameItem(){
        final PurchasedItem purchasedItem = new PurchasedItem(product, 1);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(String.format("receipt already contains product %s",product.getName()));

        receipt.addItem(purchasedItem, 1);
        receipt.addItem(purchasedItem, 2);
    }

    @Test
    public void cannotAddWithQuantityZero(){
        final PurchasedItem purchasedItem = new PurchasedItem(product, 1);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("quantity should not be less than 1");

        receipt.addItem(purchasedItem, 0);
    }

    @Test
    public void cannotAddWithNegativeQuantity(){
        final PurchasedItem purchasedItem = new PurchasedItem(product, 1);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("quantity should not be less than 1");

        receipt.addItem(purchasedItem, -1);
    }

}