package test.exercise.sales.taxes.application;

import test.exercise.sales.taxes.util.DBC;

public class RoundToNearestFivePercent implements RoundSalesTax {

    @Override
    public double apply(double value) {
        DBC.precondition(value >=0, "value should not be less than zero");

        return Math.ceil(value * 20)/20;
    }

}
