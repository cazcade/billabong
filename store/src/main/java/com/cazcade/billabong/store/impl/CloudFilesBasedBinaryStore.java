package com.cazcade.billabong.store.impl;

import com.cazcade.billabong.store.MimeTypeAwareBinaryStore;
import com.rackspacecloud.client.cloudfiles.FilesCDNContainer;
import com.rackspacecloud.client.cloudfiles.FilesClient;
import com.rackspacecloud.client.cloudfiles.FilesContainer;
import com.rackspacecloud.client.cloudfiles.FilesObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CloudFiles based implementation of BinaryStore still based on the map backed one.
 */
public class CloudFilesBasedBinaryStore extends MapBasedBinaryStore implements MimeTypeAwareBinaryStore {

    private final String containerName;
    private String defaultContainerContentType;
    private final FilesClient client;
    private static final Map<String, String> EMPTY_METADATA = Collections.emptyMap();

    public CloudFilesBasedBinaryStore(String containerName, String defaultContainerContentType, FilesClient client) {
        this.containerName = containerName;
        this.defaultContainerContentType = defaultContainerContentType;
        this.client = client;
        init();
    }

    @Override
    public boolean placeInStore(String storeKey, InputStream storeEntry, String mimeType, boolean override) {
        if (map.containsKey(storeKey)) {
            if (override) {
                addToMap(storeKey, storeEntry, mimeType);
                return true;
            }
            return false;
        } else {
            addToMap(storeKey, storeEntry, mimeType);
            return true;
        }
    }

    private void addToMap(String storeKey, InputStream data, String mimeType) {
        if (data != null) {
            try {
                client.storeObject(containerName, IOUtils.toByteArray(data), mimeType, storeKey, EMPTY_METADATA);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    data.close();
                } catch (IOException e) {
                    //todo do nothing for now but should log in the future..
                    e.printStackTrace();
                }
            }
            map.put(storeKey, new CloudFilesBinaryStoreEntry(client, containerName, storeKey, dateHelper.current()));
        } else {
            map.remove(storeKey);
            try {
                client.deleteObject(containerName, storeKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setDefaultMimeType(String mimeType) {
        defaultContainerContentType = mimeType;
    }

    @Override
    protected void addToMap(String storeKey, InputStream data) {
        this.addToMap(storeKey, data, defaultContainerContentType);
    }

    private void init() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            //TODO replace with proper client initialisation...
            client.login();
            int limit = 1000;
            String marker = null;
            for (List<FilesObject> files = client.listObjectsStartingWith(containerName, null, null, limit, marker);
                 files.size() > 0; files = client.listObjectsStartingWith(containerName, null, null, limit, marker)) {
                for (FilesObject file : files) {
                    map.put(file.getName(),
                            new CloudFilesBinaryStoreEntry(client, containerName, file.getName(),
                                    dateFormat.parse(file.getLastModified().substring(0, 25))));
                    marker = file.getName();
                }
            }
        } catch (Exception e) {
            //I've changed this so that it doesn't take down the whole of the server
//            throw new RuntimeException(e);

            //And we retry.
            e.printStackTrace();
            Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            }, 5, TimeUnit.SECONDS);
        }
    }


    public static void main(String[] args) throws Exception {
        FilesClient client = new FilesClient("cazcade", "bee5705ff5df90d7731eabf83b05f7a5");

        boolean loggedin = client.login();
        if (loggedin) {
            System.out.println("Logged in.");
            List<FilesContainer> containers = client.listContainers();
            System.out.println("Container count: " + containers.size());
            for (FilesContainer container : containers) {
                System.out.println("\tContainer: " + container.getName());
            }

            byte[] file = "<html><body>Hello World</body></html>".getBytes("UTF-8");
            client.storeObject("public", file, "text/html", "test.html", new HashMap<String, String>());

            List<FilesCDNContainer> cdnContainers = client.listCdnContainerInfo();
            System.out.println("CDN Container count: " + cdnContainers.size());
            for (FilesCDNContainer container : cdnContainers) {
                System.out.println("\tContainer: " + container.getName());
                System.out.println("\tContainer URL: " + container.getCdnURL());
                List<FilesObject> contents = client.listObjects(container.getName());
                System.out.println("\tFile Count: " + contents.size());
//                for (FilesObject fileObject : contents) {
//                    System.out.println("\t\tFile: " + fileObject.getName());
//                    System.out.println("\t\tContent Type: " + fileObject.getMimeType());
//                    System.out.println("\t\tModified:" + fileObject.getLastModified());
//                }
            }
        }
    }
}
