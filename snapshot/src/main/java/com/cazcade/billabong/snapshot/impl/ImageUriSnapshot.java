package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.snapshot.Snapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;

/**
 * Trivial snapshot implementation.
 */
public class ImageUriSnapshot implements Snapshot {
    private final URI uri;

    public ImageUriSnapshot(URI uri) {
        this.uri = uri;
    }

    @Override
    public InputStream getImage() {
        try {
            return uri.toURL().openStream();
        } catch (IOException e) {
            System.err.println("Problem opening Image URI : " + uri);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    @Override
    public URI getURI() {
        return uri;
    }
}
