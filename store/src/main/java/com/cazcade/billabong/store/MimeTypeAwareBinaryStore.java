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

/**
 * A version of Binary Store that is aware of mime types for storage and retrieval.
 */
public interface MimeTypeAwareBinaryStore extends BinaryStore {

    /**
     * Place an entry into the store. if null data is passed with an override in then any existing entry is removed.
     *
     * @param storeKey   A string intended to uniquely identify the entry in the store.
     * @param storeEntry The entry to add to the store.
     * @param override   Whether to force an override of a pre-existing entry.
     * @return True if a write occurred, either as a result of there being no other entry in the store or as a
     *         result of an override.
     */
    public boolean placeInStore(String storeKey, InputStream storeEntry, String mimeType, boolean override);

    /**
     * Set the default mime type to use when storing the content.
     *
     * @param mimeType The mime type
     */
    public void setDefaultMimeType(String mimeType);

}
