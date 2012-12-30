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

package com.cazcade.billabong.image;

/**
 * Enumeration of available images sizes. The actual pixel sizes are defined by configuration and so may be changed
 * with time.
 */
public enum ImageSize {

    LARGE(1024,-1), CLIPPED_LARGE(1024,768), CLIPPED_MEDIUM(512,384), CLIPPED_SMALL(320,200), PROFILE_SMALL(80,50);

    public int width;
    public int height;

    private ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
