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

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Binary store implementation based on the in memory map but stores its files in a location in the file system.
 * TODO support System.getProperty("cazcade.home", ".") to provide a configurable string property.
 */
public class FileBasedBinaryStore extends MapBasedBinaryStore {

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
            if (data != null) {
                FileOutputStream outputStream = new FileOutputStream(storeFile, false);
                try {
                    IOUtils.copy(data, outputStream);
                } finally {
                    outputStream.close();
                }
                storeFile.setLastModified(dateHelper.current().getTime());

                //add entry to map.
                map.put(storeKey, new FileBinaryStoreEntry(storeFile));
            }
            else {
                map.remove(storeKey);
                storeFile.delete();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void init() {
        if (storeDirectory.exists()) {
            for (File storeFile : storeDirectory.listFiles()) {
                map.put(storeFile.getName(), new FileBinaryStoreEntry(storeFile));
            }
        }
        else {
            storeDirectory.mkdirs();
        }
    }
}
