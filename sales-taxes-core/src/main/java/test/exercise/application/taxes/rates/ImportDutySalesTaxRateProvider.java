package test.exercise.application.taxes.rates;

import test.exercise.model.Product;
import test.exercise.util.DBC;

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
