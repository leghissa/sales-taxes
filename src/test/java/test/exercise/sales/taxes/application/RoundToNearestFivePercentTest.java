package test.exercise.sales.taxes.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(Parameterized.class)
public class RoundToNearestFivePercentTest {

    @Parameters(name = "unrounded {0} -> rounded {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 0d, 0d},
                { 0.1d, 0.1d },
                { 1.11d, 1.15d },
                { 1.15d, 1.15d },
                { 1.151d, 1.2d },
                { 99999999.12345d, 99999999.15d }
        });
    }

    @Parameter(value = 0)
    public double unrounded;

    @Parameter(value = 1)
    public double expected;

    private final RoundSalesTax roundSalesTax = new RoundToNearestFivePercent();

    @Test
    public void roundTest(){
        final double rounded = roundSalesTax.apply(unrounded);
        assertThat(rounded).isEqualTo(expected);
    }
}