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
