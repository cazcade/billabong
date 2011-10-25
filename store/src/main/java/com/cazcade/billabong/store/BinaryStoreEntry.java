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
     * @return the date that the entry was created.
     */
    Date getDateCreated();

    /**
     * The data held in the store as an input stream. By returning it as a stream we mask where the data is held
     * and potentially avoid holding large entries in memory at all.
     * @return the data held in the store as an input stream.
     */
    InputStream getData();

    /**
     * The number of times that the data in the entry has been accessed (not the number of times that the entry has been
     * retrieved). This distinction is important in that it allows the store to be interrogated for management purposes
     * without artificially inflating the access count.
     * @return The number of times that the data in the entry has been accessed.
     */
    int getTimesAccessed();
}
