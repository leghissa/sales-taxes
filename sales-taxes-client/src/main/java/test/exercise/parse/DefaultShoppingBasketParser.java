package test.exercise.parse;

import test.exercise.model.Product;
import test.exercise.model.ShoppingBasket;
import test.exercise.util.DBC;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultShoppingBasketParser implements ShoppingBasketParser {

    private final static Pattern LINE_PATTERN = Pattern.compile("(\\d+) ?(imported)? (.+) at (\\d*\\.\\d*)");
    private final CategoryRepository categoryRepository;

    public DefaultShoppingBasketParser(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ShoppingBasket parse(List<String> lines) {
        DBC.notNull(lines, "lines should not be null");

        final ShoppingBasket shoppingBasket = new ShoppingBasket();
        lines.forEach(l -> addProduct(shoppingBasket, l));
        return shoppingBasket;
    }

    private void addProduct(ShoppingBasket shoppingBasket, String line) {
        final Matcher matcher = LINE_PATTERN.matcher(line);
        final boolean succeded = matcher.lookingAt();
        DBC.precondition(succeded, "error parsing line: " + line);

        final int quantity = Integer.valueOf(matcher.group(1));
        final boolean imported = matcher.group(2) != null;
        final String name = matcher.group(3);
        final double price = Double.valueOf(matcher.group(4));

        final Product product = new Product(name, categoryRepository.findByProductName(name), imported, price);

        shoppingBasket.add(product, quantity);
    }
}
