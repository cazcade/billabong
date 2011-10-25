package com.cazcade.billabong.processing;

import java.awt.image.BufferedImage;

/**
 * Defining the responsibilities of an object that scales images to a range of sizes.
 */
public interface Scaler {

    public BufferedImage scaleImage(BufferedImage imageToScale, Scaling scaling);

}
