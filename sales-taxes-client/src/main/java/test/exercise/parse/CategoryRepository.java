package test.exercise.parse;

import test.exercise.model.Category;

public interface CategoryRepository {

    Category findByProductName(String productName);
}
