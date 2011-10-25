package com.cazcade.billabong.processing;

/**
 * This class defines the portion of an image to be clipped.
 */
public class Clipping {

    private final Tuple2dInteger topLeftCorner;
    private final Tuple2dInteger bottomRightCorner;

    public Clipping(Tuple2dInteger topLeftCorner, Tuple2dInteger bottomRightCorner) {
        this.topLeftCorner = topLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }

    public Tuple2dInteger getTopLeftCorner() {
        return topLeftCorner;
    }

    public Tuple2dInteger getBottomRightCorner() {
        return bottomRightCorner;
    }
}
