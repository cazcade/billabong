package com.cazcade.billabong.snapshot;

import java.net.URI;

/**
 * A class that implements this captures images that are snapshots of URIs.
 */
public interface Capturer {

    /**
     * Get a snapshot of the URI as an image
     *
     * @param uri The URI to take a snapshot of.
     * @param delay The delay in seconds before the snapshot should be taken.
     * @param waitForWindowStatus, this applies to website snapshots only, if non-null wait until the window.status variable has
     *                             this value.
     *
     * @return the snapshot that has been captured.
     */
    public Snapshot getSnapshot(URI uri, int delay, String waitForWindowStatus);
}
