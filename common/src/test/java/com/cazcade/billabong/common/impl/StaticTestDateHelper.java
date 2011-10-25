package com.cazcade.billabong.common.impl;

import com.cazcade.billabong.common.DateHelper;

import java.util.Date;

/**
 * Simple Test Date Helper that provides a static date for test purposes.
 */
public class StaticTestDateHelper implements DateHelper {
    private Date date;

    public StaticTestDateHelper() {
        date = new Date();
    }

    public StaticTestDateHelper(Date date) {
        this.date = date;
    }

    @Override
    public Date current() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
