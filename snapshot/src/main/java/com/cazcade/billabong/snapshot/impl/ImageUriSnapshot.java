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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;

/**
 * Trivial snapshot implementation.
 */
public class ImageUriSnapshot implements Snapshot {
    private final URI uri;

    public ImageUriSnapshot(URI uri) {
        this.uri = uri;
    }

    @Override
    public InputStream getImage() {
        try {
            return uri.toURL().openStream();
        } catch (IOException e) {
            System.err.println("Problem opening Image URI : " + uri);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    @Override
    public URI getURI() {
        return uri;
    }
}
