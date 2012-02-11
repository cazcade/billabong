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
