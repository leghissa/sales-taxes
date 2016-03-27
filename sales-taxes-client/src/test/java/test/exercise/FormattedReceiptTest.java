package test.exercise;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.ClassLoader.getSystemResource;
import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(Parameterized.class)
public class FormattedReceiptTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "non imported products", getSystemResourceAsStream("input1.txt"), getSystemResource("output1.txt")},
                { "imported products", getSystemResourceAsStream("input2.txt"), getSystemResource("output2.txt")},
                { "mixed products", getSystemResourceAsStream("input3.txt"), getSystemResource("output3.txt")},
                { "quantity bigger than one", getSystemResourceAsStream("input4.txt"), getSystemResource("output4.txt")}
        });
    }

    @Parameter(value = 0)
    public String title;

    @Parameter(value = 1)
    public InputStream input;

    @Parameter(value = 2)
    public URL expectedOutput;

    private final FormattedReceipt formattedReceipt = new FormattedReceipt();

    @Test
    public void createReceipt() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        formattedReceipt.create(input, outputStream);

        assertThat(outputStream.toString()).isEqualTo(IOUtils.toString(expectedOutput));
    }

}