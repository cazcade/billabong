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

import com.cazcade.billabong.processing.Scaler;
import com.cazcade.billabong.processing.Scaling;
import com.cazcade.billabong.processing.Tuple2dInteger;

import java.awt.*;
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
     *
     * @param hint The hint from java.awt.RenderingHints to use to render the scaled image.
     */
    public DefaultScaler(Object hint) {
        this.hint = hint;
    }

    @Override
    public BufferedImage scaleImage(BufferedImage imageToScale, Scaling scaling) {
        Tuple2dInteger scaledDimensions = scaling.getScaledSize(new Tuple2dInteger(imageToScale.getWidth(),
                                                                                   imageToScale.getHeight()
        )
                                                               );
        BufferedImage scaledBI = new BufferedImage(scaledDimensions.getX(), scaledDimensions.getY(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
        g.drawImage(imageToScale, 0, 0, scaledDimensions.getX(), scaledDimensions.getY(), null);
        g.dispose();
        return scaledBI;
    }
}
