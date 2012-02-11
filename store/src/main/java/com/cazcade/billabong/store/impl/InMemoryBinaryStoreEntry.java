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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * Implementation of entry for an in-memory store.
 */
public class InMemoryBinaryStoreEntry implements BinaryStoreEntry {
    private final Date dateCreated;
    private final byte[] data;
    private int timesAccessed = 0;
    private final Object mutex = new Object();

    public InMemoryBinaryStoreEntry(Date dateCreated, byte[] data) {
        //To change body of created methods use File | Settings | File Templates.
        this.dateCreated = dateCreated;
        this.data = data;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public InputStream getData() {
        synchronized (mutex) {
            timesAccessed++;
        }
        return new ByteArrayInputStream(data);
    }

    @Override
    public int getTimesAccessed() {
        return timesAccessed;
    }
}
