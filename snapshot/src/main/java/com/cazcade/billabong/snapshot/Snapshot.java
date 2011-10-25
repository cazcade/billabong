package com.cazcade.billabong.snapshot;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;

/**
 * A snapshot of an URI.
 */
public interface Snapshot {

    /**
     * Get the image representing the snapshot image.
     * @return an input stream containing the image data.
     */
    public InputStream getImage();

    /**
     * Get the date (and time) that the snapshot was taken.
     * @return the date and time that the snapshot was taken.
     */
    public Date getDate();

    /**
     * The URI associated with the snapshot.
     * @return  URI associated with the snapshot.
     */
    public URI getURI();

}
