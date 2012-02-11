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

package com.cazcade.billabong.store;

import java.io.InputStream;
import java.util.Date;

/**
 * Defining and entry held within the store. This interface is only public because of the limits of visibility modifiers
 * in Java.
 */
public interface BinaryStoreEntry {

    /**
     * The date that the entry was created.
     *
     * @return the date that the entry was created.
     */
    Date getDateCreated();

    /**
     * The data held in the store as an input stream. By returning it as a stream we mask where the data is held
     * and potentially avoid holding large entries in memory at all.
     *
     * @return the data held in the store as an input stream.
     */
    InputStream getData();

    /**
     * The number of times that the data in the entry has been accessed (not the number of times that the entry has been
     * retrieved). This distinction is important in that it allows the store to be interrogated for management purposes
     * without artificially inflating the access count.
     *
     * @return The number of times that the data in the entry has been accessed.
     */
    int getTimesAccessed();
}
