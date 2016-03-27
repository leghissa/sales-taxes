package test.exercise.format;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.model.Category;
import test.exercise.model.Product;
import test.exercise.model.PurchasedItem;
import test.exercise.model.Receipt;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultReceiptFormatterTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final ReceiptFormatter receiptFormatter = new DefaultReceiptFormatter();

    @Test
    public void formatImportedProduct() {
        final Receipt receipt = new Receipt();
        receipt.addItem(new PurchasedItem(new Product("box of chocolates", new Category("book"), true, 12.67), 2, 1));

        final String formatted = receiptFormatter.format(receipt);

        assertThat(formatted).isEqualTo("1 imported box of chocolates: 14.67\n" +
                "Sales Taxes: 2.00\n" +
                "Total: 14.67");
    }

    @Test
    public void formatNonImportedProduct() {
        final Receipt receipt = new Receipt();
        receipt.addItem(new PurchasedItem(new Product("packet of headache pills", new Category("medical_product"), false, 9.75), 0, 2));

        final String formatted = receiptFormatter.format(receipt);

        assertThat(formatted).isEqualTo("2 packet of headache pills: 19.50\n" +
                "Sales Taxes: 0.00\n" +
                "Total: 19.50");
    }

    @Test
    public void receiptShouldNotBeNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("receipt should not be null");

        receiptFormatter.format(null);
    }

}