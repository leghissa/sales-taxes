package test.exercise.sales.taxes.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    @Test
    public void canCreateNewProduct(){
        final String name = "book";
        final Category category = new Category("category");
        final boolean imported = true;
        final double price = 1;
        
        final Product product = new Product(name, category, imported, price);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.isImported()).isEqualTo(imported);
    }

    @Test
    public void nameShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name should not be blank");
        final String name = null;
        final Category category = new Category("category");
        final boolean imported = true;
        final double price = 1;

        new Product(name, category, imported, price);
    }

    @Test
    public void nameShouldNotBeBlank(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name should not be blank");
        final String name = "   ";
        final Category category = new Category("category");
        final boolean imported = true;
        final double price = 1;

        new Product(name, category, imported, price);
    }

    @Test
    public void categoryShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("category should not be null");
        final String name = "name";
        final Category category = null;
        final boolean imported = true;
        final double price = 1;

        new Product(name, category, imported, price);
    }

    @Test
    public void priceShouldNotBeLessThanZero(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("price should not be less than 0");
        final String name = "name";
        final Category category = new Category("category");
        final boolean imported = true;
        final double price = -0.1;

        new Product(name, category, imported, price);
    }

    @Test
    public void priceCanBeZero(){
        final String name = "name";
        final Category category = new Category("category");
        final boolean imported = true;
        final double price = 0;

        new Product(name, category, imported, price);
    }
    
}