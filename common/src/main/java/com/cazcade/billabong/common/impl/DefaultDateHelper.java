package com.cazcade.billabong.common.impl;

import com.cazcade.billabong.common.DateHelper;

import java.util.Date;

/**
 * Default implementation of the DateHelper that just returns the current system time.
 */
public class DefaultDateHelper implements DateHelper {

    @Override
    public Date current() {
        return new Date();
    }
}
