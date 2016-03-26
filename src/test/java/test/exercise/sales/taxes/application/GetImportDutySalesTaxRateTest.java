package test.exercise.sales.taxes.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.sales.taxes.model.Category;
import test.exercise.sales.taxes.model.Product;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetImportDutySalesTaxRateTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    private final Supplier<Double> taxRateSupplier = mock(Supplier.class);
    private GetSalesTaxRate getImportDutySalesTax = new GetImportDutySalesTaxRate(taxRateSupplier);

    @Test
    public void rateOfFivePercentForImportedProduct(){
        final double taxRate = 5;
        when(taxRateSupplier.get()).thenReturn(taxRate);

        final double got = getImportDutySalesTax.apply(new Product("some product", new Category("some category"), true, 1));

        assertThat(got).isEqualTo(taxRate);
    }

    @Test
    public void rateIsZeroIfProductIsNotImported(){
        final double taxRate = 5;
        when(taxRateSupplier.get()).thenReturn(taxRate);

        final double got = getImportDutySalesTax.apply(new Product("some product", new Category("some category"), false, 1));

        assertThat(got).isEqualTo(0);
    }

    @Test
    public void taxRateCanBeDynamicallyModified(){
        when(taxRateSupplier.get())
                .thenReturn(10d)
                .thenReturn(20d);

        final double first = getImportDutySalesTax.apply(new Product("some product", new Category("some category"), true, 1));
        final double second = getImportDutySalesTax.apply(new Product("some product", new Category("some category"), true, 1));

        assertThat(first).isEqualTo(10d);
        assertThat(second).isEqualTo(20d);
    }

    @Test
    public void productShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        getImportDutySalesTax.apply(null);
    }

    @Test
    public void taxRateSupplierShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("taxRateSupplier should not be null");

        new GetImportDutySalesTaxRate(null);
    }

}