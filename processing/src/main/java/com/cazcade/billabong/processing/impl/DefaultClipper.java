package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Clipper;
import com.cazcade.billabong.processing.Clipping;
import com.cazcade.billabong.processing.Tuple2dInteger;

import java.awt.image.BufferedImage;

/**
 * Default implementation of the Clipper using the default Java image manipulation and the hardware acceleration that
 * they support.
 */
public class DefaultClipper implements Clipper {

    @Override
    public BufferedImage clipImage(BufferedImage imageToClip, Clipping clipping) {
        Clipping actualClipping = getActualClipping(imageToClip, clipping);
        int x = actualClipping.getTopLeftCorner().getX();
        int y = actualClipping.getTopLeftCorner().getY();
        int w = actualClipping.getBottomRightCorner().getX() - x + 1;
        int h = actualClipping.getBottomRightCorner().getY() - y + 1;
        return imageToClip.getSubimage(x,y,w,h);
    }

    private Clipping getActualClipping(BufferedImage imageToClip, Clipping clipping) {
        //Just need to handle the case when the bottom right corner is outside the image.
        Tuple2dInteger bottomCorner = clipping.getBottomRightCorner();
        int maxX = imageToClip.getWidth() -1;
        int maxY = imageToClip.getHeight() -1;
        return new Clipping(clipping.getTopLeftCorner(),
                new Tuple2dInteger(
                        bottomCorner.getX() > maxX ? maxX : bottomCorner.getX(),
                        bottomCorner.getY() > maxY ? maxY : bottomCorner.getY()));

    }


}
