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

package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.snapshot.Snapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;

/**
 * File based implementation of the Snapshot interface.
 */
public class FileSnapshot implements Snapshot {
    public static final int FILE_READ_RETRY_COUNT = 10;
    private final URI uri;
    private final File outputFile;
    private final Date date;

    public FileSnapshot(URI uri, File outputFile, Date date) {
        this.uri = uri;
        this.outputFile = outputFile;
        this.date = date;
    }

    @Override
    public InputStream getImage() {
        int count = 0;
        InputStream result = null;
        //Retry due to async nature of file systems.
        //no guarantee that the file is ready to read at this stage.
        while (result == null) {
            try {
                result = new FileDeletingInputStream(outputFile);
            } catch (FileNotFoundException e) {
                if (count++ == FILE_READ_RETRY_COUNT) {
                    throw new RuntimeException(e);
                }
                else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e1) {
                        return null;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public URI getURI() {
        return uri;
    }
}
