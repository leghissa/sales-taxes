package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Product;
import test.exercise.sales.taxes.util.DBC;

import java.util.function.Supplier;

public class ImportDutySalesTaxRateProvider implements SalesTaxRateProvider {

    private final Supplier<Double> taxRateSupplier;

    public ImportDutySalesTaxRateProvider(Supplier<Double> taxRateSupplier) {
        DBC.notNull(taxRateSupplier, "taxRateSupplier should not be null");
        this.taxRateSupplier = taxRateSupplier;
    }

    @Override
    public double apply(Product product) {
        DBC.notNull(product, "product should not be null");
        return product.isImported() ? taxRateSupplier.get() : 0;
    }
}
