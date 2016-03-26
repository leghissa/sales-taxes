package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Product;

public interface SalesTaxCalculator {

    double apply(Product product);

}
