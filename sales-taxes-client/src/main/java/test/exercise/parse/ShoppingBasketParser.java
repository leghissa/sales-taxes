package test.exercise.parse;

import test.exercise.model.ShoppingBasket;

import java.util.List;

public interface ShoppingBasketParser {

    ShoppingBasket parse(List<String> lines);

}
