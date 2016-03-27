package test.exercise.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.application.taxes.SalesTaxCalculator;
import test.exercise.model.Category;
import test.exercise.model.Product;
import test.exercise.model.Receipt;
import test.exercise.model.ShoppingBasket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaxingReceiptCreatorTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();
    private Product product = new Product("some product", new Category("some category"), false, 10);
    private Product product1 = new Product("some product", new Category("some category"), false, 124.99);
    private final SalesTaxCalculator salesTaxCalculator = mock(SalesTaxCalculator.class);
    private final TaxingReceiptCreator createReceipt = new TaxingReceiptCreator(salesTaxCalculator);
    private final ShoppingBasket shoppingBasket = new ShoppingBasket();

    @Test
    public void canCreateReceiptForEmptyShoppingBasket(){
        final Receipt receipt = createReceipt.apply(shoppingBasket);

        assertThat(receipt.getPurchasedItems()).isEmpty();
        assertThat(receipt.getSalesTaxes()).isZero();
        assertThat(receipt.getTotal()).isZero();
    }

    @Test
    public void canCreateReceiptForSingleProduct(){
        final int quantity = 2;
        shoppingBasket.add(product, quantity);
        final double salesTax = 1.15;
        when(salesTaxCalculator.apply(product)).thenReturn(salesTax);

        final Receipt receipt = createReceipt.apply(shoppingBasket);

        assertThat(receipt.getPurchasedItems()).hasSize(1);
        assertThat(receipt.getSalesTaxes()).isEqualTo(salesTax * quantity);
        assertThat(receipt.getTotal()).isEqualTo(salesTax * quantity + product.getPrice()*quantity);
    }

    @Test
    public void canCreateReceiptForMultipleProducts(){
        final int quantity = 2;
        final int quantity1 = 3;
        shoppingBasket.add(product, quantity);
        shoppingBasket.add(product1, quantity1);
        final double salesTax = 1.15;
        final double salesTax1 = 10.15;
        when(salesTaxCalculator.apply(product)).thenReturn(salesTax);
        when(salesTaxCalculator.apply(product1)).thenReturn(salesTax1);

        final Receipt receipt = createReceipt.apply(shoppingBasket);

        final double expectedSalesTax = salesTax * quantity + salesTax1 * quantity1;
        final double expectedTotalForProduct = (salesTax + product.getPrice()) * quantity;
        final double expectedTotalForProduct1 = (salesTax1 + product1.getPrice()) * quantity1;
        final double expectedTotal = expectedTotalForProduct + expectedTotalForProduct1;

        assertThat(receipt.getPurchasedItems()).hasSize(2);
        assertThat(receipt.getSalesTaxes()).isEqualTo(expectedSalesTax);
        assertThat(receipt.getTotal()).isEqualTo(expectedTotal);
    }

    @Test
    public void calculateSalesTaxShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("salesTaxCalculator should not be null");

        new TaxingReceiptCreator(null);
    }

    @Test
    public void shoppingBasketShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("shoppingBasket should not be null");

        final TaxingReceiptCreator createReceipt = new TaxingReceiptCreator(p -> 1);

        createReceipt.apply(null);
    }

}