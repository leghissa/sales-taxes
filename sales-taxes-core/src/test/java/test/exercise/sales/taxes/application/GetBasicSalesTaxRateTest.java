package test.exercise.sales.taxes.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.exercise.sales.taxes.model.Category;
import test.exercise.sales.taxes.model.Product;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetBasicSalesTaxRateTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    private final Supplier<Double> taxRateSupplier = mock(Supplier.class);
    private final Supplier<Set<Category>> exceptionalCategoriesSupplier = mock(Supplier.class);
    private SalesTaxRateProvider salesTaxRateProvider = new BasicSalesTaxRateProvider(taxRateSupplier, exceptionalCategoriesSupplier);

    @Test
    public void rateOfTenPercentForNonExceptionalCategory(){
        final double basicTaxRate = 10;
        when(taxRateSupplier.get()).thenReturn(basicTaxRate);
        when(exceptionalCategoriesSupplier.get()).thenReturn(emptySet());

        final double got = salesTaxRateProvider.apply(new Product("some product", new Category("nonExceptional"), false, 1));

        assertThat(got).isEqualTo(basicTaxRate);
    }

    @Test
    public void rateIsZeroIfProductHasExceptionalCategory(){
        final Category exceptionalCategory = new Category("exceptional");
        when(taxRateSupplier.get()).thenReturn(10d);
        when(exceptionalCategoriesSupplier.get()).thenReturn(singleton(exceptionalCategory));

        final double got = salesTaxRateProvider.apply(new Product("some product", exceptionalCategory, false, 1));

        assertThat(got).isEqualTo(0);
    }

    @Test
    public void taxRateCanBeDynamicallyModified(){
        when(taxRateSupplier.get())
                .thenReturn(10d)
                .thenReturn(20d);
        when(exceptionalCategoriesSupplier.get()).thenReturn(emptySet());

        final double first = salesTaxRateProvider.apply(new Product("some product", new Category("nonExceptional"), false, 1));
        final double second = salesTaxRateProvider.apply(new Product("some product", new Category("nonExceptional"), false, 1));

        assertThat(first).isEqualTo(10);
        assertThat(second).isEqualTo(20);
    }

    @Test
    public void rateIsZeroIfProductHasOneOfMultipleExceptionalCategories(){
        final Category exceptionalCategory = new Category("exceptionalCategory");
        final Category exceptionalCategory1 = new Category("exceptionalCategory1");
        final HashSet<Category> exceptionalCategories = new HashSet<>(asList(exceptionalCategory, exceptionalCategory1));
        when(taxRateSupplier.get()).thenReturn(10d);
        when(exceptionalCategoriesSupplier.get()).thenReturn(exceptionalCategories);

        final double got = salesTaxRateProvider.apply(new Product("some product", exceptionalCategory1, false, 1));

        assertThat(got).isEqualTo(0d);
    }

    @Test
    public void exceptionalCategoriesCanBeDynamicallyModified(){
        final Category exceptionalCategory = new Category("exceptional");
        final Set<Category> exceptionalCategories = new HashSet<>();
        when(taxRateSupplier.get()).thenReturn(10d);
        when(exceptionalCategoriesSupplier.get()).thenReturn(exceptionalCategories);

        final double rateWhenNotExceptional = salesTaxRateProvider.apply(new Product("some product", exceptionalCategory, false, 1));
        exceptionalCategories.add(exceptionalCategory);
        final double rateWhenExceptional = salesTaxRateProvider.apply(new Product("some product", exceptionalCategory, false, 1));

        assertThat(rateWhenNotExceptional).isEqualTo(10);
        assertThat(rateWhenExceptional).isEqualTo(0d);
    }

    @Test
    public void productShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("product should not be null");

        salesTaxRateProvider.apply(null);
    }

    @Test
    public void exceptionalCategoriesSupplierShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("exceptionalCategoriesSupplier should not be null");

        new BasicSalesTaxRateProvider(taxRateSupplier, null);
    }

    @Test
    public void taxRateSupplierShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("taxRateSupplier should not be null");

        new BasicSalesTaxRateProvider(null, exceptionalCategoriesSupplier);
    }

}