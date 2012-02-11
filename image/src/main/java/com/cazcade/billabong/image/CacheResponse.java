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

import com.cazcade.billabong.processing.Tuple2dInteger;

import java.net.URI;

/**
 * The response object to a request for a cache URI
 */
public class CacheResponse {
    private final URI uri;
    private final long refreshIndicator;
    private final Tuple2dInteger imageSize;

    public CacheResponse(URI uri, long refreshIndicator, Tuple2dInteger imageSize) {
        this.uri = uri;
        this.refreshIndicator = refreshIndicator;
        this.imageSize = imageSize;
    }

    /**
     * The URI to use for the cached content. This may be a holding image or a stale version of the content. Refer to
     * the refresh indicator.
     *
     * @return The URI to use for the cached content.
     */
    public URI getURI() {

        return uri;
    }

    /**
     * The refresh indicator defines how many milliseconds before a refresh is needed. -ve indicates no immediate
     * refresh is pending. 0...n indicates that a refresh is coming and is estimated in n milliseconds.
     *
     * @return a refresh indicator indicating how many milliseconds before a refresh is needed.
     */
    public long getRefreshIndicator() {

        return refreshIndicator;
    }

    /**
     * Get the best known details about the image size. If any details are unknown then a negative value will be returned.
     *
     * @return the best known details about the image size.
     */
    public Tuple2dInteger getImageSize() {
        return imageSize;
    }

}
