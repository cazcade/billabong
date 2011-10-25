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
