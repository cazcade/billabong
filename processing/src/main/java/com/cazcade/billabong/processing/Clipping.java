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
