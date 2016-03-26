package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Product;

public interface GetSalesTaxRate {

    double apply(Product product);

}
