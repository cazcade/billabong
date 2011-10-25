package com.cazcade.billabong.store.impl;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Binary store implementation based on the in memory map but stores its files in a location in the file system.
 * TODO support System.getProperty("cazcade.home", ".") to provide a configurable string property.
 */
public class FileBasedBinaryStore extends MapBasedBinaryStore{

    private final File storeDirectory;

    public FileBasedBinaryStore(File storeDirectory) {
        this.storeDirectory = storeDirectory;
        init();
    }

    @Override
    protected void addToMap(String storeKey, InputStream data) {
        //Create file
        File storeFile = new File(storeDirectory, storeKey);
        try {
            if(data != null){
                FileOutputStream outputStream = new FileOutputStream(storeFile, false);
                try{
                    IOUtils.copy(data, outputStream);
                } finally {
                    outputStream.close();
                }
                storeFile.setLastModified(dateHelper.current().getTime());

                //add entry to map.
                map.put(storeKey, new FileBinaryStoreEntry(storeFile));
            } else {
                map.remove(storeKey);
                storeFile.delete();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void init() {
        if(storeDirectory.exists()){
            for(File storeFile : storeDirectory.listFiles()){
                map.put(storeFile.getName(), new FileBinaryStoreEntry(storeFile));
            }
        } else {
            storeDirectory.mkdirs();
        }
    }
}
