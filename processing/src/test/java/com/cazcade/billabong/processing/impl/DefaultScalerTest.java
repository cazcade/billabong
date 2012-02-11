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
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class DefaultScalerTest {

    @Test
    public void testAbsoluteScaling() throws IOException {
        Scaler scaler = new DefaultScaler();
        Scaling scaling = new AbsoluteScaling(640, 480);
        BufferedImage imageToScale = ImageIO.read(DefaultScalerTest.class.getResourceAsStream("/test-image.jpg"));
        BufferedImage scaledImage = scaler.scaleImage(imageToScale, scaling);
        Assert.assertEquals(640, scaledImage.getWidth());
        Assert.assertEquals(480, scaledImage.getHeight());

    }

    @Test
    public void testPercentScaling() throws IOException {
        Scaler scaler = new DefaultScaler();
        Scaling scaling = new PercentScaling(50);
        BufferedImage imageToScale = ImageIO.read(DefaultScalerTest.class.getResourceAsStream("/test-image.jpg"));
        BufferedImage scaledImage = scaler.scaleImage(imageToScale, scaling);
        Assert.assertEquals(imageToScale.getWidth() / 2, scaledImage.getWidth());
        Assert.assertEquals(imageToScale.getHeight() / 2, scaledImage.getHeight());

    }

}
