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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * File backed implementation of BinaryStoreEntry.
 */
public class FileBinaryStoreEntry implements BinaryStoreEntry {

    private final Date dateCreated;
    private final File file;
    private int timesAccessed = 0;
    private final Object mutex = new Object();

    public FileBinaryStoreEntry(File file) {
        //To change body of created methods use File | Settings | File Templates.
        this.dateCreated = new Date(file.lastModified());
        this.file = file;
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
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTimesAccessed() {
        return timesAccessed;
    }

}
