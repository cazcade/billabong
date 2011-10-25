package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.ImageProcessor;
import com.cazcade.billabong.processing.Scaler;
import com.cazcade.billabong.processing.Scaling;

import java.awt.image.BufferedImage;

/**
 * An image processor that implements a scaling.
 */
public class ScalingImageProcessor implements ImageProcessor {

    private final Scaler scaler;
    private final Scaling scaling;

    public ScalingImageProcessor(Scaler scaler, Scaling scaling) {
        this.scaler = scaler;
        this.scaling = scaling;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        return scaler.scaleImage(image, scaling);
    }
}
