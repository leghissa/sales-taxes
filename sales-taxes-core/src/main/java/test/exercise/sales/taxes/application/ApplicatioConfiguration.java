package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.model.Category;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public final class ApplicatioConfiguration {

    public static ReceiptCreator receiptCreator(){
        return new TaxingReceiptCreator(salesTaxCalculator());
    }

    private static SalesTaxCalculator salesTaxCalculator() {
        return new SalesTaxCalculatorImpl(salesTaxRateProviders(), new ToNearestFivePercentRounder());
    }

    private static Set<SalesTaxRateProvider> salesTaxRateProviders() {
        return new HashSet<>(asList(
                basicSalesTaxRateProvider(),
                importDutySalesTaxRateProvider()
        ));
    }

    private static ImportDutySalesTaxRateProvider importDutySalesTaxRateProvider() {
        final Supplier<Double> fixedFivePercentTaxRateSupplier = () -> 5d;
        return new ImportDutySalesTaxRateProvider(fixedFivePercentTaxRateSupplier);
    }

    private static BasicSalesTaxRateProvider basicSalesTaxRateProvider() {
        final Supplier<Double> fixedTenPercentTaxRateSupplier = () -> 10d;

        final Supplier<Set<Category>> fixedExceptionalCategoriesSuppliers = () -> new HashSet<>(asList(
                new Category("book"),
                new Category("food"),
                new Category("medical product")
        ));

        return new BasicSalesTaxRateProvider(fixedTenPercentTaxRateSupplier, fixedExceptionalCategoriesSuppliers);
    }

    public enum FixedCategories {

        BOOK(new Category("book")),
        FOOD(new Category("food")),
        MEDICAL_PRODUCT(new Category("medical product")),
        OTHER(new Category("other"));

        private final Category category;

        FixedCategories(Category category) {
            this.category = category;
        }

        public Category getCategory() {
            return category;
        }
    }

}
