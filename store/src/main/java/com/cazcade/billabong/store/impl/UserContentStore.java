/*
 * Copyright 2012 Cazcade Limited
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
