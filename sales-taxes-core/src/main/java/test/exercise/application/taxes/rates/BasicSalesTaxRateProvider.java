package test.exercise.application.taxes.rates;

import test.exercise.model.Category;
import test.exercise.model.Product;
import test.exercise.util.DBC;

import java.util.Set;
import java.util.function.Supplier;

public class BasicSalesTaxRateProvider implements SalesTaxRateProvider {

    private final Supplier<Double> taxRateSupplier;
    private final Supplier<Set<Category>> exceptionalCategoriesSupplier;

    public BasicSalesTaxRateProvider(Supplier<Double> taxRateSupplier, Supplier<Set<Category>> exceptionalCategoriesSupplier) {
        DBC.notNull(taxRateSupplier, "taxRateSupplier should not be null");
        DBC.notNull(exceptionalCategoriesSupplier, "exceptionalCategoriesSupplier should not be null");
        this.taxRateSupplier = taxRateSupplier;
        this.exceptionalCategoriesSupplier = exceptionalCategoriesSupplier;
    }

    @Override
    public double apply(Product product) {
        DBC.notNull(product, "product should not be null");
        if(exceptionalCategoriesSupplier.get().contains(product.getCategory())){
            return 0;
        }else{
            return taxRateSupplier.get();
        }
    }

}
