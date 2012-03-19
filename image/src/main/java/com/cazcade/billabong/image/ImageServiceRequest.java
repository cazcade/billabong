package com.cazcade.billabong.image;

import org.apache.commons.codec.digest.DigestUtils;

import java.net.URI;

public class ImageServiceRequest {
    private final URI uri;
    private final ImageSize imageSize;
    private int delay;
    private int freshness;
    private String waitForStatus;
    private boolean generate;
    private String requestKey;

    /**
     * @param uri           The URI to retrieve from the cache.
     * @param imageSize     The size of the image to retrieve.
     * @param delay         the delay in seconds before generating snapshots of web pages.
     * @param waitForStatus wait until window.status has this value (if not null)
     * @param generate      whether to generate the entry in the cache.  @return The response to the cache request.
     * @param requestKey
     */
    public ImageServiceRequest(URI uri, ImageSize imageSize, int delay, int freshness, String waitForStatus, boolean generate, String requestKey) {
        this.uri = uri;
        this.imageSize = imageSize;
        this.delay = delay;
        this.freshness = freshness;
        this.waitForStatus = waitForStatus;
        this.generate = generate;
        this.requestKey = requestKey;
    }

    public ImageServiceRequest(URI uri, ImageSize imageSize, boolean generate, String requestKey) {

        this.uri = uri;
        this.imageSize = imageSize;
        this.generate = generate;
        this.requestKey = requestKey;
    }

    public URI getUri() {
        return uri;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public int getDelay() {
        return delay;
    }

    public String getWaitForStatus() {
        return waitForStatus;
    }

    public boolean isGenerate() {
        return generate;
    }

    public URI getImageUri() {
        return uri;
    }

    public String getStoreURI() {
        if (freshness > 0) {
            return "store:" + delay + ":" + ((int) ((double) System.currentTimeMillis() / ((double) freshness * 1000))) + ":" + imageSize.name().toLowerCase() + ":" + uri;
        } else {
            return "store::" + delay + ":" + imageSize.name().toLowerCase() + ":" + uri;
        }
    }

    public String getHash() {
        return DigestUtils.shaHex(getStoreURI());
    }


    public String getRequestKey() {
        return requestKey;
    }
}
