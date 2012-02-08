package com.cazcade.billabong.image;

import java.io.InputStream;
import java.net.URI;

/**
 * The basic contract of the image service.
 *
 * TODO think about security concerns: When file/image upload is enabled how do we secure access? How do we handle
 * secured URIs where we do not have access to the underlying resource?
 */
public interface ImageService {

    /**
     * This method returns a URI that indicates how to retrieve the cached image. Will always generate an entry if
     * required through entry or staleness.
     * @param uri The URI to retrieve from the cache.
     * @param imageSize The size of the image to retrieve.
     * @return The response to the cache request.
     */
    public CacheResponse getCacheURI(URI uri, ImageSize imageSize);

    /**
     * This method returns a URI that indicates how to retrieve the cached image
     *
     * @param uri The URI to retrieve from the cache.
     * @param imageSize The size of the image to retrieve.
     * @param delay the delay in seconds before generating snapshots of web pages.
     * @param waitForStatus  wait until window.status has this value (if not null)
     *@param generate whether to generate the entry in the cache.  @return The response to the cache request.
     */
    public CacheResponse getCacheURI(URI uri, ImageSize imageSize, int delay, String waitForStatus, boolean generate);

    /**
     * This method returns a URI that indicates how to retrieve the cached image.
     * @param imageUri The image URI to retrieve from the cache.
     * @param imageSize The size of the image to retrieve.
     * @param generate whether to generate the entry in the cache.
     * @return the response to the cache request.
     */
    public CacheResponse getCacheURIForImage(URI imageUri, ImageSize imageSize, boolean generate);

    /**
     * Method for retrieving data from a local source. This method is intended to allow access to in-memory, file based
     * and secured remote binary stores.
     * @param key The key to retrieve.
     * @param imageSize The size of the image to retrieve.
     * @return A stream representation of the data.
     */
    public InputStream getDataLocally(String key, ImageSize imageSize);

}
