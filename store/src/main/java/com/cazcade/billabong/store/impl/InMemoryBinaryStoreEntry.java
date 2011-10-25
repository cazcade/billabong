package com.cazcade.billabong.store.impl;

import com.cazcade.billabong.store.BinaryStoreEntry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * Implementation of entry for an in-memory store.
 */
public class InMemoryBinaryStoreEntry implements BinaryStoreEntry {
    private final Date dateCreated;
    private final byte[] data;
    private int timesAccessed = 0;
    private final Object mutex = new Object();

    public InMemoryBinaryStoreEntry(Date dateCreated, byte[] data) {
        //To change body of created methods use File | Settings | File Templates.
        this.dateCreated = dateCreated;
        this.data = data;
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
        return new ByteArrayInputStream(data);
    }

    @Override
    public int getTimesAccessed() {
        return timesAccessed;
    }
}
