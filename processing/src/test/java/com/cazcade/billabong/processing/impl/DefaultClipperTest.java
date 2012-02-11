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
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DefaultClipperTest {

    @Test
    public void testClipping() throws IOException {
        BufferedImage imageToClip = ImageIO.read(DefaultScalerTest.class.getResourceAsStream("/test-image.jpg"));

        Clipper clipper = new DefaultClipper();

        Clipping clipping = new Clipping(
                new Tuple2dInteger(0, 0), new Tuple2dInteger(39, 39)
        );
        BufferedImage clippedImage = clipper.clipImage(imageToClip, clipping);

        Assert.assertEquals(40, clippedImage.getWidth());
        Assert.assertEquals(40, clippedImage.getHeight());
    }
}
