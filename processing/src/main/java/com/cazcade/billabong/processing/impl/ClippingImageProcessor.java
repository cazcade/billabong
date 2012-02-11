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
