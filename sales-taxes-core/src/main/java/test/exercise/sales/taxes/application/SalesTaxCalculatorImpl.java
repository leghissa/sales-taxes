package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Product;
import test.exercise.sales.taxes.util.DBC;

import java.util.Set;

public class SalesTaxCalculatorImpl implements SalesTaxCalculator {

    private final Set<SalesTaxRateProvider> saltesTaxRateProviders;
    private final SalesTaxRounder salesTaxRounder;

    public SalesTaxCalculatorImpl(Set<SalesTaxRateProvider> salesTaxRateProviders, SalesTaxRounder salesTaxRounder) {
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
