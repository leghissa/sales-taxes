package test.exercise.parse;

import test.exercise.model.Category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

public class HardcodedCategoryRepository implements  CategoryRepository {

    final Map<Category, Set<String>> productsByCategory;

    public HardcodedCategoryRepository() {
        this.productsByCategory = new HashMap<>();
        productsByCategory.put(new Category("book"), new HashSet<>(asList("book")));
        productsByCategory.put(new Category("food"), new HashSet<>(asList("chocolate bar", "box of chocolates")));
        productsByCategory.put(new Category("medical_product"), new HashSet<>(asList("packet of headache pills")));
        productsByCategory.put(new Category("other"), new HashSet<>(asList("music CD", "bottle of perfume")));
    }

    @Override
    public Category findByProductName(String productName) {
        return productsByCategory.entrySet().stream()
                .filter(e -> e.getValue().contains(productName))
                .findFirst().get().getKey();
    }
}
