package com.cazcade.billabong.image.impl;

import com.cazcade.billabong.image.CacheManager;
import com.cazcade.billabong.processing.ImageProcessor;
import com.cazcade.billabong.image.ImageSize;
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
    private final Capturer capturer;
    private final Capturer imageCapturer;
    private final Map<ImageSize, ImageProcessor> imageUriSizes;
    private final BinaryStore store;
    private final Map<ImageSize, ImageProcessor> uriSizes;

    private final Object mutex = new Object();

    //This object must only be accessed in a block synchronized by the mutex object.
    private final  Map<String, Future> futureMap = new HashMap<String, Future>();

    public DefaultCacheManager(ExecutorService executor, Capturer capturer, Capturer imageCapturer,
                               BinaryStore store, Map<ImageSize, ImageProcessor> uriSizes,
                               Map<ImageSize, ImageProcessor> imageUriSizes) {
        this.executor = executor;
        this.capturer = capturer;
        this.store = store;
        this.uriSizes = uriSizes;
        this.imageCapturer = imageCapturer;
        this.imageUriSizes = imageUriSizes;
    }

    @Override
    public void generateCacheRequest(String storeKey, URI uri) {
        synchronized(mutex){
            if(!futureMap.containsKey(storeKey)){
                futureMap.put(storeKey, executor.submit(new CacheRequest(storeKey, uri)));
            }
        }
    }

    @Override
    public void generateImageCacheRequest(String storeKey, URI uri) {
        synchronized(mutex){
            if(!futureMap.containsKey(storeKey)){
                futureMap.put(storeKey, executor.submit(new ImageCacheRequest(storeKey, uri)));
            }
        }
    }

    private class CacheRequest implements Runnable {
        private final String storeKey;
        private final URI uri;

        private CacheRequest(String storeKey, URI uri) {
            this.storeKey = storeKey;
            this.uri = uri;

        }

        @Override
        public void run() {
            try {
                //Do work
                Snapshot snapshot = capturer.getSnapshot(uri);
                System.out.println("Got Snapshot: " + uri);
                long time = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(snapshot.getImage());
                System.out.println("Read Image: " + uri);
                for(ImageSize imageSize : uriSizes.keySet()){
                    BufferedImage processedImage = uriSizes.get(imageSize).process(image);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(processedImage, "jpeg", baos);
                    System.out.println("Placing Processed image in store: " + uri + imageSize);
                    store.placeInStore(storeKey + imageSize, new ByteArrayInputStream(baos.toByteArray()), true);
                }
                System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms.");
            } catch (IOException e) {
                //todo sort out proper logging.
                e.printStackTrace();
            } catch (RuntimeException e) {
                //todo sort out proper logging.
                e.printStackTrace();
            }
            //Register that the work is complete.
            synchronized(mutex){
                futureMap.remove(storeKey);
            }
        }
    }

    private class ImageCacheRequest implements Runnable {
        private final String storeKey;
        private final URI uri;

        private ImageCacheRequest(String storeKey, URI uri) {
            this.storeKey = storeKey;
            this.uri = uri;

        }

        @Override
        public void run() {
            try {
                //Do work
                Snapshot snapshot = imageCapturer.getSnapshot(uri);
                System.out.println("Got Snapshot: " + uri);
                long time = System.currentTimeMillis();
                BufferedImage image = ImageIO.read(snapshot.getImage());
                System.out.println("Read Image: " + uri);
                for(ImageSize imageSize : imageUriSizes.keySet()){
                    BufferedImage processedImage = imageUriSizes.get(imageSize).process(image);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(processedImage, "jpeg", baos);
                    System.out.println("Placing Processed image in store: " + uri + imageSize);
                    store.placeInStore(storeKey + imageSize, new ByteArrayInputStream(baos.toByteArray()), true);
                }
                System.out.println("Time: " + (System.currentTimeMillis() - time) + "ms.");
            } catch (IOException e) {
                //todo sort out proper logging.
                System.err.println("Problem processing URI : " + uri);
                e.printStackTrace();
            } catch (RuntimeException e) {
                //todo sort out proper logging.
                System.err.println("Problem processing URI : " + uri);
                e.printStackTrace();
            }
            //Register that the work is complete.
            synchronized(mutex){
                futureMap.remove(storeKey);
            }
        }
    }
}
