package test.exercise.application.taxes;

import test.exercise.application.taxes.rates.SalesTaxRateProvider;
import test.exercise.model.Product;
import test.exercise.util.DBC;

import java.util.Set;

public class AggregatingSalesTaxCalculator implements SalesTaxCalculator {

    private final Set<SalesTaxRateProvider> saltesTaxRateProviders;
    private final SalesTaxRounder salesTaxRounder;

    public AggregatingSalesTaxCalculator(Set<SalesTaxRateProvider> salesTaxRateProviders, SalesTaxRounder salesTaxRounder) {
        DBC.notNull(salesTaxRateProviders, "saltesTaxRateProviders should not be null");
        DBC.notNull(salesTaxRounder, "salesTaxRounder should not be null");
        this.saltesTaxRateProviders = salesTaxRateProviders;
        this.salesTaxRounder = salesTaxRounder;
    }

    @Override
    public double apply(final Product product) {
        DBC.notNull(product, "product should not be null");

        final double totalSalesTaxRate = saltesTaxRateProviders.parallelStream()
                .mapToDouble(provider -> provider.apply(product))
                .sum();
        final double unroundedSalesTax = totalSalesTaxRate * product.getPrice() / 100;
        return salesTaxRounder.apply(unroundedSalesTax);
    }
}
