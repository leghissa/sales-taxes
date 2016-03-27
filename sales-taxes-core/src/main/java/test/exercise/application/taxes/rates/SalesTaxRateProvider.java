package test.exercise.application.taxes.rates;

import test.exercise.model.Product;

public interface SalesTaxRateProvider {

    double apply(Product product);

}
