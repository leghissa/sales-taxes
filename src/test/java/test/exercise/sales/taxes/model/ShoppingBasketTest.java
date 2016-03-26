package test.exercise.sales.taxes.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingBasketTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    private final Product product1 = new Product("some product", new Category("some category"), false, 1);
    private final Product product2 = new Product("some other product", new Category("some category"), false, 1);
    private final ShoppingBasket shoppingBasket = new ShoppingBasket();

    @Test
    public void canCreateEmptyShoppingBasket(){

        assertThat(shoppingBasket.getItems()).isEmpty();
    }

    @Test
    public void canAddNewItem(){
        shoppingBasket.add(product1, 1);

        assertThat(shoppingBasket.getItems()).hasSize(1);
        assertThat(shoppingBasket.getItems().get(product1)).isEqualTo(1);
    }

    @Test
    public void canAddExistingItem(){
        shoppingBasket.add(product1, 1);

        shoppingBasket.add(product1, 2);

        assertThat(shoppingBasket.getItems()).hasSize(1);
        assertThat(shoppingBasket.getItems().get(product1)).isEqualTo(3);
    }

    @Test
    public void canAddMultipleItems(){
        shoppingBasket.add(product1, 1);
        shoppingBasket.add(product2, 2);

        assertThat(shoppingBasket.getItems()).hasSize(2);
        assertThat(shoppingBasket.getItems().get(product1)).isEqualTo(1);
        assertThat(shoppingBasket.getItems().get(product2)).isEqualTo(2);
    }

    @Test
    public void cannotAddLessThanOneProduct(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("quantity should not be less than 1");

        shoppingBasket.add(product1, 0);
    }

    @Test
    public void cannotAddNullProduct(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        shoppingBasket.add(null, 1);
    }

}