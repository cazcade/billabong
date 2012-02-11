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

import java.io.*;

/**
 * Class that decorates a standard FileInputStream to provide deletion of the file when the input stream is closed. Used
 * for transient files only.
 */
public class FileDeletingInputStream extends InputStream {
    private final File file;
    private FileInputStream fileInputStream;

    public FileDeletingInputStream(File file) throws FileNotFoundException {

        this.file = file;
        this.fileInputStream = new FileInputStream(file);
    }

    @Override
    public int read() throws IOException {
        return fileInputStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return fileInputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return fileInputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return fileInputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return fileInputStream.available();
    }

    @Override
    public void close() throws IOException {
        fileInputStream.close();
        file.delete();
    }

    @Override
    public void mark(int readLimit) {
        fileInputStream.mark(readLimit);
    }

    @Override
    public void reset() throws IOException {
        fileInputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return fileInputStream.markSupported();
    }
}
