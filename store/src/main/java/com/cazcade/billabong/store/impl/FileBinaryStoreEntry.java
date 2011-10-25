package com.cazcade.billabong.store.impl;

import com.cazcade.billabong.store.BinaryStoreEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * File backed implementation of BinaryStoreEntry.
 */
public class FileBinaryStoreEntry implements BinaryStoreEntry {

    private final Date dateCreated;
    private final File file;
    private int timesAccessed = 0;
    private final Object mutex = new Object();

    public FileBinaryStoreEntry(File file) {
        //To change body of created methods use File | Settings | File Templates.
        this.dateCreated = new Date(file.lastModified());
        this.file = file;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public InputStream getData() {
        synchronized(mutex){
            timesAccessed++;
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTimesAccessed() {
        return timesAccessed;
    }

}
