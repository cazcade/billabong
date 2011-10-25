package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Scaling;
import com.cazcade.billabong.processing.Tuple2dInteger;

/**
 * Define the scaling in terms of the fixed size that is desired.
 */
public class AbsoluteScaling implements Scaling {

    private final Tuple2dInteger scalingTuple;

    @SuppressWarnings({"SameParameterValue", "SameParameterValue"})
    public AbsoluteScaling(int x, int y){
        scalingTuple = new Tuple2dInteger(x, y);
    }

    @Override
    public Tuple2dInteger getScaledSize(Tuple2dInteger originalSize) {
        return scalingTuple;
    }
}
