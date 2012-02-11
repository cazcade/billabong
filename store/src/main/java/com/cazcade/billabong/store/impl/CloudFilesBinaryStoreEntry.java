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

import com.cazcade.billabong.store.BinaryStoreEntry;
import com.rackspacecloud.client.cloudfiles.FilesClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Cloudfiles based implementation of binary store entry.
 */
public class CloudFilesBinaryStoreEntry implements BinaryStoreEntry {
    private transient FilesClient client;
    private final String containerName;
    private final String containerObject;
    private final Date lastModified;
    private final Object mutex = new Object();
    private int timesAccessed = 0;


    public CloudFilesBinaryStoreEntry(FilesClient client, String containerName, String containerObject, Date lastModified) {
        this.client = client;
        this.containerName = containerName;
        this.containerObject = containerObject;
        this.lastModified = lastModified;
    }

    @Override
    public Date getDateCreated() {
        return lastModified;
    }

    @Override
    public InputStream getData() {
        synchronized (mutex) {
            timesAccessed++;
        }
        try {
            return client.getObjectAsStream(containerName, containerObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTimesAccessed() {
        return timesAccessed;
    }

    public void setClient(FilesClient client) {
        this.client = client;
    }
}
