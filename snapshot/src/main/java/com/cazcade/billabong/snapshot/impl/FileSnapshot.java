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
    public static final int FILE_READ_RETRY_COUNT = 10;
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
        int count = 0;
        InputStream result = null;
        //Retry due to async nature of file systems.
        //no guarantee that the file is ready to read at this stage.
        while (result == null)
            try {
                result = new FileDeletingInputStream(outputFile);
            } catch (FileNotFoundException e) {
                if (count++ == FILE_READ_RETRY_COUNT) {
                    throw new RuntimeException(e);
                } else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e1) {
                        return null;
                    }
                }
            }
        return result;
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
