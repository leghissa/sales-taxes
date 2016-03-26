package test.exercise.sales.taxes.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.sales.taxes.model.Category;
import test.exercise.sales.taxes.model.Product;
import test.exercise.sales.taxes.model.Receipt;
import test.exercise.sales.taxes.model.ShoppingBasket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateReceiptImplTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();
    private Product product = new Product("some product", new Category("some category"), false, 10);
    private Product product1 = new Product("some product", new Category("some category"), false, 124.99);
    private final CalculateSalesTax calculateSalesTax = mock(CalculateSalesTax.class);
    private final CreateReceiptImpl createReceipt = new CreateReceiptImpl(calculateSalesTax);
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
        when(calculateSalesTax.apply(product)).thenReturn(salesTax);

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
        when(calculateSalesTax.apply(product)).thenReturn(salesTax);
        when(calculateSalesTax.apply(product1)).thenReturn(salesTax1);

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
        expectedException.expectMessage("calculateSalesTax should not be null");

        new CreateReceiptImpl(null);
    }

    @Test
    public void shoppingBasketShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("shoppingBasket should not be null");

        final CreateReceiptImpl createReceipt = new CreateReceiptImpl(p -> 1);

        createReceipt.apply(null);
    }

}