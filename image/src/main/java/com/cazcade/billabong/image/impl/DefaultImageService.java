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
import com.cazcade.billabong.image.*;
import com.cazcade.billabong.processing.Tuple2dInteger;
import com.cazcade.billabong.store.BinaryStore;
import com.cazcade.billabong.store.BinaryStoreRetrievalResult;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
    public CacheResponse getCacheURI(URI uri, ImageSize imageSize, String requestKey) {
        return getCacheURI(new ImageServiceRequest(uri, imageSize, 0, 1, null, true, requestKey));
    }

    @Override
    public CacheResponse getCacheURI(ImageServiceRequest imageServiceRequest) {
        //Generate the cache store key
        String storeKey = generateStoreKey(imageServiceRequest);
        String requestKey = imageServiceRequest.getRequestKey();

        long refreshIndicator = defaultRefresh;
        URI cacheURI = holdingURI;
        BinaryStoreRetrievalResult result = store.retrieveFromStore(storeKey);
        try {
            if (result.entryFound()) {
                refreshIndicator = pendingRenewal(requestKey, result, storeKey, imageServiceRequest.getUri(), imageServiceRequest.getDelay(), imageServiceRequest.getWaitForStatus(), imageServiceRequest.isGenerate(), imageServiceRequest.getImageSize());
                cacheURI = generateCacheURI(storeKey);
            } else {
                if (imageServiceRequest.isGenerate()) {
                    cacheManager.generateCacheRequest(storeKey, imageServiceRequest.getUri(), imageServiceRequest.getDelay(), imageServiceRequest.getWaitForStatus(), requestKey, imageServiceRequest.getImageSize());
                }
            }
        } catch (RejectedExecutionException e) {
            throw new RuntimeException(e);
        }

        Tuple2dInteger imageSizeTuple = uriSizeMap.get(imageServiceRequest.getImageSize().name());
        return new CacheResponse(cacheURI, refreshIndicator, imageSizeTuple);
    }

    @Override
    public CacheResponse getCacheURIForImage(ImageServiceRequest imageServiceRequest) {
        //Generate the cache store key
        String storeKey = generateStoreKey(imageServiceRequest);
        long refreshIndicator = defaultRefresh;
        URI cacheURI = holdingURI;
        String requestKey = imageServiceRequest.getRequestKey();
        BinaryStoreRetrievalResult result = store.retrieveFromStore(storeKey);
        try {
            if (result.entryFound()) {
                refreshIndicator = pendingRenewal(requestKey, result, storeKey, imageServiceRequest.getImageUri(), 0 /*Images should be generated immediately*/,
                        null, imageServiceRequest.isGenerate(),
                        imageServiceRequest.getImageSize());
                cacheURI = generateCacheURI(storeKey);
            } else {
                if (imageServiceRequest.isGenerate()) {
                    cacheManager.generateImageCacheRequest(storeKey, imageServiceRequest.getImageUri(), requestKey);
                }
            }
        } catch (RejectedExecutionException e) {
            throw new RuntimeException(e);
        }

        Tuple2dInteger imageSizeTuple = imageUriSizeMap.get(imageServiceRequest.getImageSize().name());
        return new CacheResponse(cacheURI, refreshIndicator, imageSizeTuple);
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

    private String generateStoreKey(ImageServiceRequest req) {
        try {
            String storeKey = req.getHash();
            if (storeKey.getBytes(encoding).length > 1024) {
                throw new RuntimeException("Unexpectedly large store key - will not be handled for now.");
            }
            return storeKey;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private long pendingRenewal(String requestKey, BinaryStoreRetrievalResult result, String storeKey, URI uri, int delay, String waitForStatus,
                                boolean generate, ImageSize imageSize) {
        if (getRenewalValue(result) > renewalThreshold) {
            if (generate) {
                cacheManager.generateCacheRequest(storeKey, uri, delay, waitForStatus, requestKey, imageSize);
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
