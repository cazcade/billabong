package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Scaling;
import com.cazcade.billabong.processing.Tuple2dInteger;

/**
 * Define a scaling in terms of percent.
 */
public class PercentScaling implements Scaling {

    private final double percent;

    @SuppressWarnings({"SameParameterValue"})
    public PercentScaling(double percent) {
        this.percent = percent;
    }

    @Override
    public Tuple2dInteger getScaledSize(Tuple2dInteger orginalSize) {
        return new Tuple2dInteger(
                (int)(orginalSize.getX() * percent / 100.0d),
                (int)(orginalSize.getY() * percent / 100.0d));
    }
}
