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

import java.net.URI;

/**
 * The basic contract of the image service.
 * <p/>
 * TODO think about security concerns: When file/image upload is enabled how do we secure access? How do we handle
 * secured URIs where we do not have access to the underlying resource?
 */
public interface ImageService {

    /**
     * This method returns a URI that indicates how to retrieve the cached image. Will always generate an entry if
     * required through entry or staleness.
     *
     * @param uri        The URI to retrieve from the cache.
     * @param imageSize  The size of the image to retrieve.
     * @param requestKey
     * @return The response to the cache request.
     */
    @Deprecated
    public CacheResponse getCacheURI(URI uri, ImageSize imageSize, String requestKey);

    /**
     * This method returns a URI that indicates how to retrieve the cached image
     *
     * @param imageServiceRequest
     */
    public CacheResponse getCacheURI(ImageServiceRequest imageServiceRequest);

    /**
     * This method returns a URI that indicates how to retrieve the cached image.
     *
     * @param imageServiceRequest@return the response to the cache request.
     */
    public CacheResponse getCacheURIForImage(ImageServiceRequest imageServiceRequest);

}
