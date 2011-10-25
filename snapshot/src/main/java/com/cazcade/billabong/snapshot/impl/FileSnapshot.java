package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.snapshot.Snapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;

/**
 * File based implementation of the Snapshot interface.
 */
public class FileSnapshot implements Snapshot {
    private final URI uri;
    private final File outputFile;
    private final Date date;

    public FileSnapshot(URI uri, File outputFile, Date date) {
        this.uri = uri;
        this.outputFile = outputFile;
        this.date = date;
    }

    @Override
    public InputStream getImage() {
        try {
            return new FileDeletingInputStream(outputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public URI getURI() {
        return uri;
    }
}
