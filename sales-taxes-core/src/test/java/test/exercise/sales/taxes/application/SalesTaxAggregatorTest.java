package test.exercise.sales.taxes.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.sales.taxes.model.Category;
import test.exercise.sales.taxes.model.Product;

import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SalesTaxAggregatorTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    private SalesTaxRounder salesTaxRounder = mock(SalesTaxRounder.class);

    @Test
    public void taxIsZeroIfNoSalesTaxRateProvidersAreSupplied(){
        final double expectedTax = 0;
        final SalesTaxCalculator calculateAggregatedSalesTax = new SalesTaxCalculatorImpl(emptySet(), salesTaxRounder);
        when(salesTaxRounder.apply(expectedTax)).thenReturn(expectedTax);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(tax).isZero();
    }

    @Test
    public void taxIsExpectedWithSingleSalesTaxRateProvider(){
        final double expectedTax = 0.1;
        final SalesTaxCalculator calculateAggregatedSalesTax = new SalesTaxCalculatorImpl(singleton((p) -> 10), salesTaxRounder);
        when(salesTaxRounder.apply(expectedTax)).thenReturn(expectedTax);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(tax).isEqualTo(expectedTax);
    }

    @Test
    public void taxIsAggregatedWithMultipleSalesTaxRateProviders(){
        final SalesTaxRateProvider provider1 = (p) -> 10;
        final SalesTaxRateProvider provider2 = (p) -> 20;
        final double expectedTax = 0.3d;
        final SalesTaxCalculator calculateAggregatedSalesTax = new SalesTaxCalculatorImpl(new HashSet<>(asList(provider1, provider2)), salesTaxRounder);
        when(salesTaxRounder.apply(expectedTax)).thenReturn(expectedTax);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(tax).isEqualTo(expectedTax);
    }

    @Test
    public void taxIsRounded(){
        final SalesTaxRateProvider provider1 = (p) -> 10;
        final double expectedUnrounded = 12.56;
        final double rounded = 12.60;
        final SalesTaxCalculator calculateAggregatedSalesTax = new SalesTaxCalculatorImpl(singleton(provider1), salesTaxRounder);
        when(salesTaxRounder.apply(expectedUnrounded)).thenReturn(rounded);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 125.6d));

        assertThat(tax).isEqualTo(rounded);
    }

    @Test
    public void salesTaxRateProvidersShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("saltesTaxRateProviders should not be null");

        new SalesTaxCalculatorImpl(null, salesTaxRounder);
    }

    @Test
    public void roundSalesTaxShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("salesTaxRounder should not be null");

        new SalesTaxCalculatorImpl(emptySet(), null);
    }

    @Test
    public void productShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        final SalesTaxCalculator calculateAggregatedSalesTax = new SalesTaxCalculatorImpl(emptySet(), salesTaxRounder);

        calculateAggregatedSalesTax.apply(null);
    }

}