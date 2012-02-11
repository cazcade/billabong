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

package com.cazcade.billabong.image.impl;

import com.cazcade.billabong.common.DateHelper;
import com.cazcade.billabong.common.impl.DefaultDateHelper;
import com.cazcade.billabong.image.CacheManager;
import com.cazcade.billabong.image.CacheResponse;
import com.cazcade.billabong.image.ImageService;
import com.cazcade.billabong.image.ImageSize;
import com.cazcade.billabong.processing.Tuple2dInteger;
import com.cazcade.billabong.store.BinaryStore;
import com.cazcade.billabong.store.BinaryStoreRetrievalResult;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;

/**
 *
 */
public class DefaultImageService implements ImageService {
    private long defaultRefresh = 10000l;
    private final URI holdingURI;
    private final String cachePrefix;
    private final BinaryStore store;
    private String encoding = "UTF-8";
    private DateHelper dateHelper = new DefaultDateHelper();
    private double renewalThreshold = 25.5d;
    private final CacheManager cacheManager;
    private final HashMap<String, Tuple2dInteger> uriSizeMap;
    private final HashMap<String, Tuple2dInteger> imageUriSizeMap;


    public DefaultImageService(URI holdingURI, String cachePrefix, BinaryStore store, CacheManager cacheManager,
                               HashMap<String, Tuple2dInteger> uriSizeMap, HashMap<String, Tuple2dInteger> imageUriSizeMap) {
        this.holdingURI = holdingURI;
        this.cachePrefix = cachePrefix;
        this.store = store;
        this.cacheManager = cacheManager;
        this.uriSizeMap = uriSizeMap;
        this.imageUriSizeMap = imageUriSizeMap;
    }

    @Override
    public CacheResponse getCacheURI(URI uri, ImageSize imageSize) {
        return getCacheURI(uri, imageSize, 0, null, true);
    }

    @Override
    public CacheResponse getCacheURI(URI uri, ImageSize imageSize, int delay, String waitForStatus, boolean generate) {
        //Generate the cache store key
        String storeKey = generateStoreKey(uri);
        long refreshIndicator = defaultRefresh;
        URI cacheURI = holdingURI;
        BinaryStoreRetrievalResult result = store.retrieveFromStore(storeKey + imageSize);
        try {
            if (result.entryFound()) {
                refreshIndicator = pendingRenewal(result, storeKey, uri, delay, waitForStatus, generate);
                cacheURI = generateCacheURI(storeKey + imageSize);
            }
            else {
                if (generate) {
                    cacheManager.generateCacheRequest(storeKey, uri, delay, waitForStatus);
                }
            }
        } catch (RejectedExecutionException e) {
            throw new RuntimeException(e);
        }

        Tuple2dInteger imageSizeTuple = uriSizeMap.get(imageSize.name());
        return new CacheResponse(cacheURI, refreshIndicator, imageSizeTuple);
    }

    @Override
    public CacheResponse getCacheURIForImage(URI imageUri, ImageSize imageSize, boolean generate) {
        //Generate the cache store key
        String storeKey = generateStoreKey(imageUri);
        long refreshIndicator = defaultRefresh;
        URI cacheURI = holdingURI;
        BinaryStoreRetrievalResult result = store.retrieveFromStore(storeKey + imageSize);
        try {
            if (result.entryFound()) {
                refreshIndicator = pendingRenewal(result, storeKey, imageUri, 0 /*Images should be generated immediately*/,
                                                  null, generate
                                                 );
                cacheURI = generateCacheURI(storeKey + imageSize);
            }
            else {
                if (generate) {
                    cacheManager.generateImageCacheRequest(storeKey, imageUri);
                }
            }
        } catch (RejectedExecutionException e) {
            throw new RuntimeException(e);
        }

        Tuple2dInteger imageSizeTuple = imageUriSizeMap.get(imageSize.name());
        return new CacheResponse(cacheURI, refreshIndicator, imageSizeTuple);
    }

    @Override
    public InputStream getDataLocally(String key, ImageSize imageSize) {
        return store.retrieveFromStore(key + imageSize).getContent();
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setDefaultRefresh(long defaultRefresh) {
        this.defaultRefresh = defaultRefresh;
    }

    public void setDateHelper(DateHelper dateHelper) {
        this.dateHelper = dateHelper;
    }

    public void setRenewalThreshold(double renewalThreshold) {
        this.renewalThreshold = renewalThreshold;
    }

    private String generateStoreKey(URI uri) {
        try {
            String storeKey = URLEncoder.encode(uri.toString(), encoding).replace('%', 'P');
            if (storeKey.getBytes(encoding).length > 1024) {
                // TODO this check is in place to ensure that we do not exceed the limits imposed by CloudFiles. Replace
                // with proper handling - probably generate an UUID as the store key and persist the relationship...
                throw new RuntimeException("Unexpectedly large store key - will not be handled for now.");
            }
            return storeKey;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private long pendingRenewal(BinaryStoreRetrievalResult result, String storeKey, URI uri, int delay, String waitForStatus,
                                boolean generate) {
        if (getRenewalValue(result) > renewalThreshold) {
            if (generate) {
                cacheManager.generateCacheRequest(storeKey, uri, delay, waitForStatus);
            }
            return defaultRefresh;
        }
        return -1;
    }

    private double getRenewalValue(BinaryStoreRetrievalResult result) {
        return Math.log(dateHelper.current().getTime() - result.getTimeStored().getTime()) + Math.log(result.retrievalCount());
    }

    private URI generateCacheURI(String storeKey) {
        try {
            return new URI(cachePrefix + storeKey);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

}
