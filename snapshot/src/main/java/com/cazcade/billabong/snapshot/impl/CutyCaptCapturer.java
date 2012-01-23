package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.common.DateHelper;
import com.cazcade.billabong.snapshot.Capturer;
import com.cazcade.billabong.snapshot.Snapshot;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.UUID;

/**
 * Wrapper class for the CutyCapt executable.
 */
public class CutyCaptCapturer implements Capturer {
    private final String executable;
    private String outputType = "jpg";
    private String outputPath = System.getProperty("cazcade.home", ".") + "/billabong/CutyCapt/tmp";
    private int minWidth = 1024;
    private int minHeight = 768;
    private int maxWait = 30000;
    private int delay = 2000;
    private long maxProcessWait = maxWait + 10000l;

    private final DateHelper dateHelper;

    @SuppressWarnings({"SameParameterValue", "SameParameterValue"})
    public CutyCaptCapturer(String executable, DateHelper dateHelper) {
        this.executable = executable;
        this.dateHelper = dateHelper;
    }

    @Override
    public Snapshot getSnapshot(URI uri, final int delayInSeconds) {
        initOutputPath();
        this.delay= delayInSeconds*1000;
        UUID uuid = UUID.randomUUID();
        File outputFile = new File(outputPath, uuid.toString() + "." + outputType);
        outputFile.getParentFile().mkdirs();
        ProcessBuilder processBuilder = new ProcessBuilder(
                executable,
                "--url=" + uri.toString(),
                "--out=" + outputFile.toString(),
                "--min-width=" + minWidth,
                "--min-height=" + minHeight,
                "--max-wait=" + maxWait,
                "--delay=" + this.delay
                );

        processBuilder.redirectErrorStream(true);
        try {
            Process captureProcess = processBuilder.start();
            InputStreamReader inputStream = new InputStreamReader(
                    new BufferedInputStream(captureProcess.getInputStream()));
            try{
                boolean done = false;
                long maxEndTime = System.currentTimeMillis() + maxProcessWait;
                StringBuffer output = new StringBuffer();
                char[] buffer = new char[4096];
                while (!done && System.currentTimeMillis() < maxEndTime){
                    int length = inputStream.read(buffer);
                    if(length >= 0){
                        output.append(buffer, 0, length);
                    } else {
                        try{
                            int result = captureProcess.exitValue();
                            done = true;
                            if(result != 0){
                                throw new RuntimeException("Failed to capture URI image successfully:\n" +
                                        uri + "\n" + output.toString());
                            }
                        } catch (IllegalThreadStateException e){
                            //expected - work not yet done...
                            //The only case I've yet found where an empty catch block may be justified.
                        }
                    }

                }
                System.err.println(output);
            } finally{
                inputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        File outputPathFile = new File(outputPath) ;
        if(!outputPathFile.exists()){
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

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setMaxProcessWait(long maxProcessWait) {
        this.maxProcessWait = maxProcessWait;
    }
}
