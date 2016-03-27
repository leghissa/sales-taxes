package test.exercise.model;

import test.exercise.util.DBC;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingBasket {

    private final Map<Product, Integer> items = new LinkedHashMap<>();

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void add(Product product, int quantity){
        DBC.notNull(product, "product should not be null");
        DBC.precondition(quantity > 0, "quantity should not be less than 1");

        if(items.containsKey(product)){
            final Integer oldQuantity = items.get(product);
            items.put(product, oldQuantity + quantity);
        }else {
            items.put(product, quantity);
        }
    }
}
