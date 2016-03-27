package test.exercise;

import test.exercise.application.ReceiptCreator;
import test.exercise.application.TaxingReceiptCreator;
import test.exercise.application.taxes.AggregatingSalesTaxCalculator;
import test.exercise.application.taxes.SalesTaxCalculator;
import test.exercise.application.taxes.ToNearestFivePercentRounder;
import test.exercise.application.taxes.rates.BasicSalesTaxRateProvider;
import test.exercise.application.taxes.rates.ImportDutySalesTaxRateProvider;
import test.exercise.application.taxes.rates.SalesTaxRateProvider;
import test.exercise.format.DefaultReceiptFormatter;
import test.exercise.format.ReceiptFormatter;
import test.exercise.model.Category;
import test.exercise.parse.CategoryRepository;
import test.exercise.parse.DefaultShoppingBasketParser;
import test.exercise.parse.HardcodedCategoryRepository;
import test.exercise.parse.ShoppingBasketParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.lang.Double.valueOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;

public final class ApplicationConfiguration {

    private ApplicationConfiguration(){}

    public static ReceiptCreator receiptCreator(){
        return new TaxingReceiptCreator(salesTaxCalculator());
    }

    public static ShoppingBasketParser shoppingBasketParser(){
        return new DefaultShoppingBasketParser(categoryRepository());
    }

    public static ReceiptFormatter receiptFormatter(){
        return new DefaultReceiptFormatter();
    }

    private static SalesTaxCalculator salesTaxCalculator() {
        return new AggregatingSalesTaxCalculator(salesTaxRateProviders(), new ToNearestFivePercentRounder());
    }

    private static Set<SalesTaxRateProvider> salesTaxRateProviders() {
        return new HashSet<>(asList(
                basicSalesTaxRateProvider(),
                importDutySalesTaxRateProvider()
        ));
    }

    private static ImportDutySalesTaxRateProvider importDutySalesTaxRateProvider() {
        final Supplier<Double> dynamicPropertyTaxRateSupplier = () -> valueOf(properties().getProperty("salestaxrate.import.value"));
        return new ImportDutySalesTaxRateProvider(dynamicPropertyTaxRateSupplier);
    }

    private static BasicSalesTaxRateProvider basicSalesTaxRateProvider() {
        final Supplier<Double> dyanmicPropertyTaxRateSupplier = () -> valueOf(properties().getProperty("salestaxrate.basic.value"));

        return new BasicSalesTaxRateProvider(dyanmicPropertyTaxRateSupplier, exceptionalCategoriesSupplier());
    }

    private static Supplier<Set<Category>> exceptionalCategoriesSupplier(){
        final String[] categories = properties().getProperty("salestaxrate.basic.exceptionalcategories").split(",");
        return () -> asList(categories).stream()
                .map(Category::new)
                .collect(toCollection(HashSet::new));
    }

    private static CategoryRepository categoryRepository(){
        return new HardcodedCategoryRepository();
    }

    private static Properties properties(){
        final Properties properties = new Properties();
        try {
            properties.load(getSystemResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
