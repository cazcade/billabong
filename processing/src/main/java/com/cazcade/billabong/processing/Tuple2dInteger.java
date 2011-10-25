package com.cazcade.billabong.processing;

/**
 * A simple 2D tuple that stores integer values for X and Y. Depending on the context it can either indicate a point in
 * 2D space or a width and height.
 */
public class Tuple2dInteger {

    private final int x;
    private final int y;

    public Tuple2dInteger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
