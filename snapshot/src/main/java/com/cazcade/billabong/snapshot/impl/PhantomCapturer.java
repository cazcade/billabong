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

package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.common.DateHelper;
import com.cazcade.billabong.snapshot.Capturer;
import com.cazcade.billabong.snapshot.Snapshot;

import java.io.File;
import java.net.URI;
import java.util.UUID;

/**
 * Wrapper class for the CutyCapt executable.
 */
public class PhantomCapturer implements Capturer {
    public static final int TIMEOUT_GRACE_PERIOD = 5000;
    private String outputType = "png";
    private String outputPath = System.getProperty("cazcade.home", ".") + "/billabong/phantom/tmp";
    private int maxWait = 30000;


    private final DateHelper dateHelper;
    private final String userAgent = "Billabong 1.1 (Phantom) Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) " +
            "AppleWebKit/534.52.7 (KHTML, " +
            "like Gecko) Version/5.1.2 Safari/534.52.7";

    @SuppressWarnings({"SameParameterValue", "SameParameterValue"})
    public PhantomCapturer(DateHelper dateHelper) {
        this.dateHelper = dateHelper;
    }

    @Override
    public Snapshot getSnapshot(URI uri, final int delayInSeconds, String waitForWindowStatus) throws InterruptedException {

        initOutputPath();
        UUID uuid = UUID.randomUUID();
        File outputFile = new File(outputPath, uuid.toString() + "." + outputType);
        outputFile.getParentFile().mkdirs();
        final String delayString = String.valueOf(delayInSeconds * 1000);
        ProcessBuilder processBuilder;

        processBuilder = new ProcessBuilder(
                System.getProperty("user.home", ".") + "/phantomjs/bin/phantomjs",
                System.getProperty("user.home", ".") + "/etc/render_snapito.js",
                uri.toString(),
                outputFile.toString(),
                delayString
        );

        int result = ProcessExecutor.execute(processBuilder, System.currentTimeMillis() + maxWait + delayInSeconds * 1000 + TIMEOUT_GRACE_PERIOD);


        if (!outputFile.exists()) {
            if (result != 0) {
                System.out.println("Process exited with value " + result);
                throw new RuntimeException("Failed to capture URI image successfully: " +
                        uri + " result was " + result);
            } else {
                throw new RuntimeException("Failed to capture URI image successfully: " +
                        uri + ", image not found.");
            }
        }
        return new FileSnapshot(uri, outputFile, dateHelper.current());

    }


    @SuppressWarnings({"SameParameterValue", "SameParameterValue"})
    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    @SuppressWarnings({"SameParameterValue"})
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;

    }

    private void initOutputPath() {
        File outputPathFile = new File(outputPath);
        if (!outputPathFile.exists()) {
            outputPathFile.mkdirs();
        }
    }


}
