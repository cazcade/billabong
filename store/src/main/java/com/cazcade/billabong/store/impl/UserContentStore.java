package com.cazcade.billabong.store.impl;

import com.cazcade.billabong.store.BinaryStoreRetrievalResult;
import com.cazcade.billabong.store.MimeTypeAwareBinaryStore;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Decorate a binary store to provide the ability to generate URIs for store keys.
 */
public class UserContentStore implements MimeTypeAwareBinaryStore {

    private final MimeTypeAwareBinaryStore store;
    private final String cachePrefix;

    public UserContentStore(MimeTypeAwareBinaryStore store, String cachePrefix) {
        this.store = store;
        this.cachePrefix = cachePrefix;
    }


    @Override
    public BinaryStoreRetrievalResult retrieveFromStore(String storeKey) {
        return store.retrieveFromStore(storeKey);
    }

    @Override
    public boolean placeInStore(String storeKey, InputStream storeEntry, boolean override) {
        return store.placeInStore(storeKey, storeEntry, override);
    }

    @Override
    public boolean placeInStore(String storeKey, InputStream storeEntry, String mimeType, boolean override) {
        return store.placeInStore(storeKey, storeEntry, mimeType, override);
    }

    @Override
    public void setDefaultMimeType(String mimeType) {
        store.setDefaultMimeType(mimeType);
    }

    public URI getStoreURI(String storeKey) {
        try {
            return new URI(cachePrefix + storeKey);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
