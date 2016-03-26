package test.exercise.sales.taxes.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchasedItemTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    @Test
    public void canCreateNewPurchasedItem(){
        final Product product = new Product("name", new Category("category"), false, 1);
        final double salesTax = 123;

        final PurchasedItem purchasedItem = new PurchasedItem(product, salesTax);

        assertThat(purchasedItem.getProduct()).isEqualTo(product);
        assertThat(purchasedItem.getSalesTax()).isEqualTo(salesTax);
    }

    @Test
    public void productShouldNotBeNull(){
        final Product product = null;
        final double salesTax = 123;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        new PurchasedItem(product, salesTax);
    }

    @Test
    public void salesTaxShouldNotBeLessThanZero(){
        final Product product = new Product("name", new Category("category"), false, 1);
        final double salesTax = -0.1;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("salesTax should not be less than 0");

        new PurchasedItem(product, salesTax);
    }

    @Test
    public void salesTaxCanBeZero(){
        final Product product = new Product("name", new Category("category"), false, 1);
        final double salesTax = 0;

        final PurchasedItem purchasedItem = new PurchasedItem(product, salesTax);

        assertThat(purchasedItem.getSalesTax()).isEqualTo(salesTax);
    }

}