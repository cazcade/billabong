package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.snapshot.Capturer;
import com.cazcade.billabong.snapshot.Snapshot;

import java.net.URI;

/**
 * Trivial Capturer implementation.
 */
public class ImageURICapturer implements Capturer {

    @Override
    public Snapshot getSnapshot(URI uri, int delay, String waitForWindowStatus) {
        return new ImageUriSnapshot(uri);
    }
}
