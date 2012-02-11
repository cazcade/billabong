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
 * Define the scaling in terms of the fixed size that is desired.
 */
public class AbsoluteScaling implements Scaling {

    private final Tuple2dInteger scalingTuple;

    @SuppressWarnings({"SameParameterValue", "SameParameterValue"})
    public AbsoluteScaling(int x, int y) {
        scalingTuple = new Tuple2dInteger(x, y);
    }

    @Override
    public Tuple2dInteger getScaledSize(Tuple2dInteger originalSize) {
        return scalingTuple;
    }
}
