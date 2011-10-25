package com.cazcade.billabong.processing;

import java.awt.image.BufferedImage;

/**
 * Classes implementing this interface provide the capability to clip an image to a given size. This is complementary
 * to the Scaler.
 */
public interface Clipper {

    /**
     * Clip an image. Clipping an image means creating a new image from a subsection of the image.
     * @param imageToClip The image to clip.
     * @param clipping The clipping to apply.
     * @return The clipped image.
     */
    public BufferedImage clipImage(BufferedImage imageToClip, Clipping clipping);
}
