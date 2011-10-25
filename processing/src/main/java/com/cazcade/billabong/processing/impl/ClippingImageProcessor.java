package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Clipper;
import com.cazcade.billabong.processing.Clipping;
import com.cazcade.billabong.processing.ImageProcessor;

import java.awt.image.BufferedImage;

/**
 * An image processor that implements a clipping.
 */
public class ClippingImageProcessor implements ImageProcessor {

    private final Clipper clipper;
    private final Clipping clipping;

    public ClippingImageProcessor(Clipper clipper, Clipping clipping) {
        this.clipper = clipper;
        this.clipping = clipping;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        return clipper.clipImage(image, clipping);
    }
}
