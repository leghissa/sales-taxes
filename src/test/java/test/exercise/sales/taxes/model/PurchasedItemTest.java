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
        final double singleSalesTax = 123;
        final int quantity = 2;

        final PurchasedItem purchasedItem = new PurchasedItem(product, singleSalesTax, quantity);

        assertThat(purchasedItem.getProduct()).isEqualTo(product);
        assertThat(purchasedItem.getSalesTax()).isEqualTo(singleSalesTax * quantity);
        assertThat(purchasedItem.getTotal()).isEqualTo((singleSalesTax + product.getPrice()) * quantity);
        assertThat(purchasedItem.getQuantity()).isEqualTo(quantity);
    }

    @Test
    public void productShouldNotBeNull(){
        final Product product = null;
        final double singleSalesTax = 123;
        final int quantity = 2;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        new PurchasedItem(product, singleSalesTax, quantity);
    }

    @Test
    public void salesTaxShouldNotBeLessThanZero(){
        final Product product = new Product("name", new Category("category"), false, 1);
        final double singleSalesTax = -0.1;
        final int quantity = 2;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("singleSalesTax should not be less than 0");

        new PurchasedItem(product, singleSalesTax, quantity);
    }

    @Test
    public void salesTaxCanBeZero(){
        final Product product = new Product("name", new Category("category"), false, 1);
        final double singleSalesTax = 0;
        final int quantity = 2;

        final PurchasedItem purchasedItem = new PurchasedItem(product, singleSalesTax, quantity);

        assertThat(purchasedItem.getSalesTax()).isEqualTo(singleSalesTax);
    }

    @Test
    public void quantityNotBeLessThanOne(){
        final Product product = new Product("name", new Category("category"), false, 1);
        final double singleSalesTax = 0.1;
        final int quantity = -1;
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("quantity should not be less than 1");

        new PurchasedItem(product, singleSalesTax, quantity);
    }

}