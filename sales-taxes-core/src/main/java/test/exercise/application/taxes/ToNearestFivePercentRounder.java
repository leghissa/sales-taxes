package test.exercise.application.taxes;

import test.exercise.util.DBC;

public class ToNearestFivePercentRounder implements SalesTaxRounder {

    @Override
    public double apply(double value) {
        DBC.precondition(value >=0, "value should not be less than zero");

        return Math.ceil(value * 20)/20;
    }

}
