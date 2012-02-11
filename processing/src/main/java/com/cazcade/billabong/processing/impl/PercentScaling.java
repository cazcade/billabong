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

package com.cazcade.billabong.processing.impl;

import com.cazcade.billabong.processing.Scaling;
import com.cazcade.billabong.processing.Tuple2dInteger;

/**
 * Define a scaling in terms of percent.
 */
public class PercentScaling implements Scaling {

    private final double percent;

    @SuppressWarnings({"SameParameterValue"})
    public PercentScaling(double percent) {
        this.percent = percent;
    }

    @Override
    public Tuple2dInteger getScaledSize(Tuple2dInteger orginalSize) {
        return new Tuple2dInteger(
                (int) (orginalSize.getX() * percent / 100.0d),
                (int) (orginalSize.getY() * percent / 100.0d)
        );
    }
}
