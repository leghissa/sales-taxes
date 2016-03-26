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

public class CalculateAggregatedSalesTaxTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    private RoundSalesTax roundSalesTax = mock(RoundSalesTax.class);

    @Test
    public void taxIsZeroIfNoSalesTaxRateProvidersAreSupplied(){
        final double expectedTax = 0;
        final CalculateSalesTax calculateAggregatedSalesTax = new CalculateAggregatedSalesTax(emptySet(), roundSalesTax);
        when(roundSalesTax.apply(expectedTax)).thenReturn(expectedTax);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(tax).isZero();
    }

    @Test
    public void taxIsExpectedWithSingleSalesTaxRateProvider(){
        final double expectedTax = 0.1;
        final CalculateSalesTax calculateAggregatedSalesTax = new CalculateAggregatedSalesTax(singleton((p) -> 10), roundSalesTax);
        when(roundSalesTax.apply(expectedTax)).thenReturn(expectedTax);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(tax).isEqualTo(expectedTax);
    }

    @Test
    public void taxIsAggregatedWithMultipleSalesTaxRateProviders(){
        final GetSalesTaxRate provider1 = (p) -> 10;
        final GetSalesTaxRate provider2 = (p) -> 20;
        final double expectedTax = 0.3d;
        final CalculateSalesTax calculateAggregatedSalesTax = new CalculateAggregatedSalesTax(new HashSet<>(asList(provider1, provider2)), roundSalesTax);
        when(roundSalesTax.apply(expectedTax)).thenReturn(expectedTax);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(tax).isEqualTo(expectedTax);
    }

    @Test
    public void taxIsRounded(){
        final GetSalesTaxRate provider1 = (p) -> 10;
        final double expectedUnrounded = 12.56;
        final double rounded = 12.60;
        final CalculateSalesTax calculateAggregatedSalesTax = new CalculateAggregatedSalesTax(singleton(provider1), roundSalesTax);
        when(roundSalesTax.apply(expectedUnrounded)).thenReturn(rounded);

        final double tax = calculateAggregatedSalesTax.apply(new Product("some product", new Category("some category"), false, 125.6d));

        assertThat(tax).isEqualTo(rounded);
    }

    @Test
    public void salesTaxRateProvidersShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("saltesTaxRateProviders should not be null");

        new CalculateAggregatedSalesTax(null, roundSalesTax);
    }

    @Test
    public void roundSalesTaxShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("roundSalesTax should not be null");

        new CalculateAggregatedSalesTax(emptySet(), null);
    }

    @Test
    public void productShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        final CalculateSalesTax calculateAggregatedSalesTax = new CalculateAggregatedSalesTax(emptySet(), roundSalesTax);

        calculateAggregatedSalesTax.apply(null);
    }

}