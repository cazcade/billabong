package com.cazcade.billabong.store;

import java.io.InputStream;
import java.util.Date;

/**
 * The results of a store retrieval request. If no content is found then the entry found flag will be set to false and
 * all other methods will return null.
 */
public class BinaryStoreRetrievalResult {
    private final String storeKey;
    private final BinaryStoreEntry entry;

    public BinaryStoreRetrievalResult(String storeKey, BinaryStoreEntry entry) {
        this.storeKey = storeKey;
        this.entry = entry;
    }

    /**
     * The content of the store that is being returned.
     * @return the content of the cache.
     */
    public InputStream getContent(){
        return entry == null ? null : entry.getData();
    }

    /**
     * The time that the content was stored in the store.
     * @return the time that the content was stored in the store.
     */
    public Date getTimeStored(){
        return entry == null ? null : entry.getDateCreated();
    }

    /**
     * Simple flag indicating whether the content was found or not.
     * @return true if the entry was found and there is content.
     */
    public boolean entryFound(){
        return entry != null;
    }

    /**
     * The number of times that the content has been retrieved since the time it was stored in the cache. This only
     * counts the times that the binary data has been accessed not the overall entry. This distinction allows checks
     * to be made of the cache content without artificially inflating the access count.
     * @return The number of times that the content has been retrieved.
     */
    public Integer retrievalCount(){
        return entry == null ? null : entry.getTimesAccessed();
    }

    /**
     * Get the key that the entry was stored against.
     * @return the key that the entry was stored against.
     */
    public String getStoreKey() {
        return storeKey;
    }
}
