package test.exercise;

import test.exercise.application.ReceiptCreator;
import test.exercise.format.ReceiptFormatter;
import test.exercise.model.Receipt;
import test.exercise.model.ShoppingBasket;
import test.exercise.parse.ShoppingBasketParser;
import test.exercise.util.DBC;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class FormattedReceipt {

    private final ShoppingBasketParser shoppingBasketParser;
    private final ReceiptCreator receiptCreator;
    private final ReceiptFormatter receiptFormatter;

    public FormattedReceipt() {
        shoppingBasketParser = ApplicationConfiguration.shoppingBasketParser();
        receiptCreator = ApplicationConfiguration.receiptCreator();
        receiptFormatter = ApplicationConfiguration.receiptFormatter();
    }

    public void create(InputStream inputStream, OutputStream outputStream) {
        DBC.notNull(inputStream, "inputStream should not be null");
        DBC.notNull(outputStream, "outputStream should not be null");
        final List<String> lines = new BufferedReader(new InputStreamReader(inputStream,
                        StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        final ShoppingBasket shoppingBasket = shoppingBasketParser.parse(lines);
        final Receipt receipt = receiptCreator.apply(shoppingBasket);
        final String formattedReceipt = receiptFormatter.format(receipt);
        printReceipt(outputStream, formattedReceipt);
    }

    private void printReceipt(OutputStream outputStream, String formattedReceipt) {
        final PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.append(formattedReceipt);
        printWriter.close();
    }

}
