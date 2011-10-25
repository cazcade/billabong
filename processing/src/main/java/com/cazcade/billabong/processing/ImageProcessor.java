package com.cazcade.billabong.processing;

import java.awt.image.BufferedImage;

/**
 * An object that processes images.
 */
public interface ImageProcessor {
    BufferedImage process(BufferedImage image);
}
