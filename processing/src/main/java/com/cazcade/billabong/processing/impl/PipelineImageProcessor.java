package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Image processor that implements a pipeline of image processors. Each image processor is applied to the result
 * of the preceding image processor until a result is achieved.
 */
public class PipelineImageProcessor implements ImageProcessor {

    private final List<ImageProcessor> processors;


    public PipelineImageProcessor(List<ImageProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        for(ImageProcessor processor : processors){
            image = processor.process(image);
        }
        return image;
    }
}
