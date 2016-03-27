package test.exercise.format;

import test.exercise.model.Receipt;
import test.exercise.util.DBC;

public class DefaultReceiptFormatter implements ReceiptFormatter {

    private final static String PRODUCT_PATTERN = "%d %s%s: %.2f\n";

    @Override
    public String format(Receipt receipt) {
        DBC.notNull(receipt, "receipt should not be null");

        final StringBuilder buffer = new StringBuilder();
        receipt.getPurchasedItems()
                .forEach(i -> {
                    final String name = i.getProduct().getName();
                    final String imported = i.getProduct().isImported() ? "imported " : "";
                    buffer.append(String.format(PRODUCT_PATTERN, i.getQuantity(), imported, name, i.getTotal()));
                });

        buffer.append(String.format("Sales Taxes: %.2f\n", receipt.getSalesTaxes()));
        buffer.append(String.format("Total: %.2f", receipt.getTotal()));
        return buffer.toString();
    }

}
