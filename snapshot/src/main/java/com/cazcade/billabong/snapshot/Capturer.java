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

package com.cazcade.billabong.snapshot;

import java.net.URI;

/**
 * A class that implements this captures images that are snapshots of URIs.
 */
public interface Capturer {

    /**
     * Get a snapshot of the URI as an image
     *
     * @param uri                  The URI to take a snapshot of.
     * @param delay                The delay in seconds before the snapshot should be taken.
     * @param waitForWindowStatus, this applies to website snapshots only, if non-null wait until the window.status variable has
     *                             this value.
     * @return the snapshot that has been captured.
     */
    public Snapshot getSnapshot(URI uri, int delay, String waitForWindowStatus);
}
