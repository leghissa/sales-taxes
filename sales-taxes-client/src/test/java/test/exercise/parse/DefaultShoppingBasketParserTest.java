package test.exercise.parse;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.model.Product;
import test.exercise.model.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultShoppingBasketParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    private final CategoryRepository categoryRepository = new HardcodedCategoryRepository();
    private final ShoppingBasketParser shoppingBasketParser = new DefaultShoppingBasketParser(categoryRepository);

    @Test
    public void canParseEmptyBasket() {
        final ShoppingBasket shoppingBasket = shoppingBasketParser.parse(emptyList());

        Assertions.assertThat(shoppingBasket.getItems()).isEmpty();
    }

    @Test
    public void canParseSingleNonImportedProduct() {
        final ShoppingBasket shoppingBasket = shoppingBasketParser.parse(singletonList(
                "1 book at 12.49"
        ));

        final List<Product> products = new ArrayList<>(shoppingBasket.getItems().keySet());
        final Product product = products.get(0);
        final Integer quantity = shoppingBasket.getItems().get(product);

        assertThat(products).hasSize(1);
        assertProduct(product, "book", "book", 12.49);
        assertThat(quantity).isEqualTo(1);
    }

    @Test
    public void canParseSingleImportedProduct() {
        final ShoppingBasket shoppingBasket = shoppingBasketParser.parse(asList(
                "2 imported bottle of perfume at 27.99"
        ));

        final List<Product> products = new ArrayList<>(shoppingBasket.getItems().keySet());
        final Product product = products.get(0);
        final Integer quantity = shoppingBasket.getItems().get(product);

        assertThat(products).hasSize(1);
        assertProduct(product, "bottle of perfume", "other", 27.99);
        assertThat(quantity).isEqualTo(2);
    }

    @Test
    public void canParseMultipleLines() {
        final ShoppingBasket shoppingBasket = shoppingBasketParser.parse(asList(
                "1 packet of headache pills at 9.75",
                "10 imported box of chocolates at 11.25"
        ));

        final List<Product> products = new ArrayList<>(shoppingBasket.getItems().keySet());
        final Product product = products.get(0);
        final Integer quantity = shoppingBasket.getItems().get(product);
        final Product product1 = products.get(1);
        final Integer quantity1 = shoppingBasket.getItems().get(product1);

        assertThat(products).hasSize(2);
        assertProduct(product, "packet of headache pills", "medical_product", 9.75);
        assertThat(quantity).isEqualTo(1);
        assertProduct(product1, "box of chocolates", "food", 11.25);
        assertThat(quantity1).isEqualTo(10);
    }

    @Test
    public void throwExceptionOnInvalidLine() {
        final String line = "1 book at";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("error parsing line: " + line);

        shoppingBasketParser.parse(asList(
                line
        ));
    }


    @Test
    public void linesShouldNotBeNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("lines should not be null");

        shoppingBasketParser.parse(null);
    }

    private void assertProduct(Product product, String expectedName, String expectedCategory, double expectedPrice) {
        assertThat(product.getName()).isEqualTo(expectedName);
        assertThat(product.getCategory().getName()).isEqualTo(expectedCategory);
        assertThat(product.getPrice()).isEqualTo(expectedPrice);
    }

}