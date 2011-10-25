package com.cazcade.billabong.processing;

/**
 * Objects implementing this interface define a scaling of a tuple. This will be used in this context to define how
 * a width and height are scaled.
 */
public interface Scaling {

    Tuple2dInteger getScaledSize(Tuple2dInteger originalSize);

}
