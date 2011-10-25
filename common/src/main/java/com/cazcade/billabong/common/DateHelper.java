package com.cazcade.billabong.common;

import java.util.Date;

/**
 * Important indirection to allow us to test the system in a time independent manner.
 */
public interface DateHelper {

    /**
     * Get the current date / time as perceived by the date helper.
     * @return the current date / time.
     */
    public Date current();
}
