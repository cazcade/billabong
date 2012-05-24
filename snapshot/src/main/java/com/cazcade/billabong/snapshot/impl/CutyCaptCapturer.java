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
public class CutyCaptCapturer implements Capturer {
    public static final int TIMEOUT_GRACE_PERIOD = 10000;
    private final String executable;
    private String outputType = "png";
    private String outputPath = System.getProperty("cazcade.home", ".") + "/billabong/CutyCapt/tmp";
    private int minWidth = 1024;
    private int minHeight = 768;
    private int maxWait = 10000;

    private final DateHelper dateHelper;

    @SuppressWarnings({"SameParameterValue", "SameParameterValue"})
    public CutyCaptCapturer(String executable, DateHelper dateHelper) {
        this.executable = executable;
        this.dateHelper = dateHelper;
    }

    @Override
    public Snapshot getSnapshot(URI uri, final int delayInSeconds, String waitForWindowStatus) throws InterruptedException {
        initOutputPath();
        UUID uuid = UUID.randomUUID();
        File outputFile = new File(outputPath, uuid.toString() + "." + outputType);
        outputFile.getParentFile().mkdirs();


        //now for real
        ProcessBuilder processBuilder = new ProcessBuilder(
                "/bin/bash",
                "-c",
                System.getProperty("user.home") + "/bin/CutyScript " +
                        " --http-proxy=http://localhost:3128 " +
                        " --url='" + uri.toString() + "'" +
                        " --out=" + outputFile.toString() +
                        " --min-width=" + minWidth +
                        " --min-height=" + minHeight +
                        " --max-wait=" + (maxWait + delayInSeconds * 1000) +
                        " --delay=" + delayInSeconds * 1000 +
                        " --plugins=on" +
                        " --user-agent='Billabong 1.1 (CutyCapt) Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_7; da-dk) AppleWebKit/533.211 " +
                        "(KHTML, like Gecko) Version/5.0.5 Safari/533.21.1'"
        );
        int result = ProcessExecutor.execute(processBuilder, System.currentTimeMillis() + maxWait + delayInSeconds * 1000 + TIMEOUT_GRACE_PERIOD);

        if (result != 0) {
            System.out.println("Process exited with value " + result);
//            throw new RuntimeException("Failed to capture URI image successfully: " +
//                    uri + " result was " + result);
        }

        if (!outputFile.exists()) {
            throw new RuntimeException("Failed to capture URI image successfully: " +
                    uri + ", image not found.");
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

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }


}
