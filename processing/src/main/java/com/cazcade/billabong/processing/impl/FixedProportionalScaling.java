package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Scaling;
import com.cazcade.billabong.processing.Tuple2dInteger;

/**
 * This class returns a scaling that preserves the original proportion of the image while ensuring it fits within 
 */
public class FixedProportionalScaling implements Scaling {
    private int width;
    private int height;
    private double scalingProportion;

    public FixedProportionalScaling(int width, int height) {
        this.width = width;
        this.height = height;
        scalingProportion = ((double)width) / ((double)height);

    }

    @Override
    public Tuple2dInteger getScaledSize(Tuple2dInteger originalSize) {
        double originalSizeProportion = ((double)originalSize.getX()) / ((double)originalSize.getY());
        double scaling = 1.0d;
        if(originalSizeProportion > scalingProportion){//original proportionally wider.
            scaling = ((double)width) / ((double)originalSize.getX());
            if(scaling > 1.0d){ //never want to upscale.
                return originalSize;
            }
            return new Tuple2dInteger(width, (int)(originalSize.getY() * scaling));
        } else { // original proportionally taller
            scaling = ((double)height) / ((double)originalSize.getY());
            if(scaling >= 1.0d){ //never want to upscale.
                return originalSize;
            }
            return new Tuple2dInteger((int)(originalSize.getX() * scaling), height);
        }
    }
}
