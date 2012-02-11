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

import java.awt.image.BufferedImage;

/**
 * Classes implementing this interface provide the capability to clip an image to a given size. This is complementary
 * to the Scaler.
 */
public interface Clipper {

    /**
     * Clip an image. Clipping an image means creating a new image from a subsection of the image.
     *
     * @param imageToClip The image to clip.
     * @param clipping    The clipping to apply.
     * @return The clipped image.
     */
    public BufferedImage clipImage(BufferedImage imageToClip, Clipping clipping);
}
