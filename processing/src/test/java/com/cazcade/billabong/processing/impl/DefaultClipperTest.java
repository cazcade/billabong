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
                new Tuple2dInteger(0,0), new Tuple2dInteger(39,39)
        );
        BufferedImage clippedImage = clipper.clipImage(imageToClip, clipping);

        Assert.assertEquals(40, clippedImage.getWidth());
        Assert.assertEquals(40, clippedImage.getHeight());
    }
}
