package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Product;

public interface SalesTaxRateProvider {

    double apply(Product product);

}
