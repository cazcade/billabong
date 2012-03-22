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

import com.cazcade.billabong.image.CacheManager;
import com.cazcade.billabong.image.ImageSize;
import com.cazcade.billabong.processing.ImageProcessor;
import com.cazcade.billabong.snapshot.Capturer;
import com.cazcade.billabong.snapshot.Snapshot;
import com.cazcade.billabong.store.BinaryStore;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Trivial in-memory implementation of CacheManager.
 */
public class DefaultCacheManager implements CacheManager {

    private final ExecutorService executor;
    private final Capturer imageCapturer;
    private final Map<ImageSize, ImageProcessor> imageUriSizes;
    private final BinaryStore store;
    private final Map<ImageSize, ImageProcessor> uriSizes;

    private final Object mutex = new Object();

    //This object must only be accessed in a block synchronized by the mutex object.
    private final Map<String, Future> futureMap = new HashMap<String, Future>();
    private Capturer wkhtmlCapturer;
    private Capturer cutyCapturer;


    public void setType(String type) {
        this.type = type;
    }

    private String type = "png";

    public DefaultCacheManager(ExecutorService executor, Capturer wkhtmlCapturer, Capturer cutyCapturer, Capturer imageCapturer,
                               BinaryStore store, Map<ImageSize, ImageProcessor> uriSizes,
                               Map<ImageSize, ImageProcessor> imageUriSizes) {
        this.executor = executor;
        this.wkhtmlCapturer = wkhtmlCapturer;
        this.cutyCapturer = cutyCapturer;
        this.store = store;
        this.uriSizes = uriSizes;
        this.imageCapturer = imageCapturer;
        this.imageUriSizes = imageUriSizes;
    }

    @Override
    public void generateCacheRequest(String storeKey, URI uri, int delay, String waitForStatus, String requestKey, ImageSize imageSize) {
        synchronized (mutex) {
            if (!futureMap.containsKey(requestKey)) {
                futureMap.put(requestKey, executor.submit(new CacheRequest(storeKey, uri, delay, waitForStatus, requestKey, imageSize)));
            }
        }
    }

    @Override
    public void generateImageCacheRequest(String storeKey, URI uri, String requestKey) {
        synchronized (mutex) {
            if (!futureMap.containsKey(requestKey)) {
                futureMap.put(requestKey, executor.submit(new ImageCacheRequest(storeKey, uri, requestKey)));
            }
        }
    }

    private class CacheRequest implements Runnable {
        private final String storeKey;
        private final URI uri;
        private int delay;
        private String waitForStatus;
        private String requestKey;
        private ImageSize imageSize;


        private CacheRequest(String storeKey, URI uri, int delay, String waitForStatus, String requestKey, ImageSize imageSize) {
            this.storeKey = storeKey;
            this.uri = uri;

            this.delay = delay;
            this.waitForStatus = waitForStatus;
            this.requestKey = requestKey;
            this.imageSize = imageSize;
        }

        @Override
        public void run() {
            Capturer capturer = null;
            try {
                //Do work
                if (waitForStatus != null) {
                    capturer = wkhtmlCapturer;
                } else {
                    capturer = cutyCapturer;
                }
                long time = capture(capturer);
                System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms.");
            } catch (Exception e) {
                e.printStackTrace();
                //fallback to the alternate
                if (capturer == wkhtmlCapturer) {
                    try {
                        capture(cutyCapturer);
                    } catch (Exception e1) {
                        e.printStackTrace();
                    }
                }
                if (capturer == cutyCapturer) {
                    try {
                        capture(wkhtmlCapturer);
                    } catch (Exception e1) {
                        e.printStackTrace();
                    }
                }
            }
            //Register that the work is complete.
            synchronized (mutex) {
                futureMap.remove(requestKey);
            }
        }

        private long capture(Capturer capturer) throws IOException, InterruptedException {
            Snapshot snapshot = capturer.getSnapshot(uri, delay, waitForStatus);
            System.out.println("Got Snapshot: " + uri);
            long time = System.currentTimeMillis();
            BufferedImage image = ImageIO.read(snapshot.getImage());
            System.out.println("Read Image: " + uri);
            BufferedImage processedImage = uriSizes.get(imageSize).process(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processedImage, type, baos);
            System.out.println("Placing Processed image in store: " + uri + imageSize);
            store.placeInStore(storeKey, new ByteArrayInputStream(baos.toByteArray()), true);
            return time;
        }
    }

    private class ImageCacheRequest implements Runnable {
        private final String storeKey;
        private final URI uri;
        private String requestKey;
        private ImageSize imageSize;

        private ImageCacheRequest(String storeKey, URI uri, String requestKey) {
            this.storeKey = storeKey;
            this.uri = uri;

            this.requestKey = requestKey;
        }

        @Override
        public void run() {
            try {
                //Do work
                Snapshot snapshot = imageCapturer.getSnapshot(uri, 0, null);
                System.out.println("Got Snapshot: " + uri);
                long time = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(snapshot.getImage());
                System.out.println("Read Image: " + uri);
                BufferedImage processedImage = imageUriSizes.get(imageSize).process(image);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(processedImage, type, baos);
                System.out.println("Placing Processed image in store: " + uri + imageSize);
                store.placeInStore(storeKey, new ByteArrayInputStream(baos.toByteArray()), true);
                System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms.");
                //Register that the work is complete.
                synchronized (mutex) {
                    futureMap.remove(requestKey);
                }
            } catch (IOException e) {
                //todo sort out proper logging.
                System.err.println("Problem processing URI : " + uri);
                e.printStackTrace();
            } catch (RuntimeException e) {
                //todo sort out proper logging.
                System.err.println("Problem processing URI : " + uri);
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
                return;
            }
        }
    }
}
