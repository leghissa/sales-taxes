package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Product;
import test.exercise.sales.taxes.util.DBC;

import java.util.Set;

public class CalculateAggregatedSalesTax implements CalculateSalesTax {

    private final Set<GetSalesTaxRate> saltesTaxRateProviders;
    private final RoundSalesTax roundSalesTax;

    public CalculateAggregatedSalesTax(Set<GetSalesTaxRate> salesTaxRateProviders, RoundSalesTax roundSalesTax) {
        DBC.notNull(salesTaxRateProviders, "saltesTaxRateProviders should not be null");
        DBC.notNull(roundSalesTax, "roundSalesTax should not be null");
        this.saltesTaxRateProviders = salesTaxRateProviders;
        this.roundSalesTax = roundSalesTax;
    }

    @Override
    public double apply(final Product product) {
        DBC.notNull(product, "product should not be null");

        final double totalSalesTaxRate = saltesTaxRateProviders.parallelStream()
                .mapToDouble(provider -> provider.apply(product))
                .sum();
        final double unroundedSalesTax = totalSalesTaxRate * product.getPrice() / 100;
        return roundSalesTax.apply(unroundedSalesTax);
    }
}
