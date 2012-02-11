/*
 * Copyright 2012 Cazcade Limited
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
        return imageToClip.getSubimage(x, y, w, h);
    }

    private Clipping getActualClipping(BufferedImage imageToClip, Clipping clipping) {
        //Just need to handle the case when the bottom right corner is outside the image.
        Tuple2dInteger bottomCorner = clipping.getBottomRightCorner();
        int maxX = imageToClip.getWidth() - 1;
        int maxY = imageToClip.getHeight() - 1;
        return new Clipping(clipping.getTopLeftCorner(),
                            new Tuple2dInteger(
                                    bottomCorner.getX() > maxX ? maxX : bottomCorner.getX(),
                                    bottomCorner.getY() > maxY ? maxY : bottomCorner.getY()
                            )
        );

    }


}
