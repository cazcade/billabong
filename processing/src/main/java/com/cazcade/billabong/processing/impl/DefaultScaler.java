package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Scaler;
import com.cazcade.billabong.processing.Scaling;
import com.cazcade.billabong.processing.Tuple2dInteger;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Default implementation of scaling that uses the basic Java APIs. This implementation uses only the hardware
 * acceleration built into the JVM and does everything in-memory.
 */
public class DefaultScaler implements Scaler {

    private Object hint = RenderingHints.VALUE_INTERPOLATION_BICUBIC;

    public DefaultScaler() {
    }

    /**
     * Override the default Bicubic render hint.
     * @param hint The hint from java.awt.RenderingHints to use to render the scaled image.
     */
    public DefaultScaler(Object hint) {
        this.hint = hint;
    }

    @Override
    public BufferedImage scaleImage(BufferedImage imageToScale, Scaling scaling) {
        Tuple2dInteger scaledDimensions = scaling.getScaledSize(new Tuple2dInteger(imageToScale.getWidth(), imageToScale.getHeight()));
        BufferedImage scaledBI = new BufferedImage(scaledDimensions.getX(), scaledDimensions.getY(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
        g.drawImage(imageToScale, 0, 0, scaledDimensions.getX(), scaledDimensions.getY(), null);
        g.dispose();
        return scaledBI;
    }
}
