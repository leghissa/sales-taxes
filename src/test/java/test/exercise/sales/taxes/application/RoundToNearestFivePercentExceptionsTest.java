package test.exercise.sales.taxes.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RoundToNearestFivePercentExceptionsTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();
    private final SalesTaxRounder salesTaxRounder = new ToNearestFivePercentRounder();

    @Test
    public void valueShouldNotBeNegative(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("value should not be less than zero");

        salesTaxRounder.apply(-0.1);
    }

}