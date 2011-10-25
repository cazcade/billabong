package com.cazcade.billabong.snapshot;

import java.net.URI;

/**
 * A class that implements this captures images that are snapshots of URIs.
 */
public interface Capturer {
    /**
     * Get a snapshot of the URI as an image
     * @param uri The URI to take a snapshot of.
     * @return the snapshot that has been captured.
     */
    public Snapshot getSnapshot(URI uri);
}
