package com.cazcade.billabong.snapshot.impl;

import com.cazcade.billabong.common.impl.StaticTestDateHelper;
import com.cazcade.billabong.snapshot.Snapshot;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class CutyCaptCapturerTest {
    private static final String OUTPUT_PATH = "target/testcapture";

    @Test
    public void testCapture() throws URISyntaxException, IOException {
     /*
        Date date = new Date();
        URI uri = new URI("http://www.google.co.uk");
        File outputPath = new File(OUTPUT_PATH);
        if(outputPath.exists()){
            for(File fileToDelete : outputPath.listFiles()){
                Assert.assertTrue(fileToDelete.delete());
            }
        }

        CutyCaptCapturer capturer = new CutyCaptCapturer("CutyCapt", new StaticTestDateHelper(date));
        capturer.setOutputPath(OUTPUT_PATH);
        capturer.setOutputType("svg");
        Snapshot snapshot = capturer.getSnapshot(uri);
        InputStreamReader reader = new InputStreamReader(snapshot.getImage());
        StringBuffer result = new StringBuffer();
        try{
            char[] buffer = new char[4096];
            for(int i = reader.read(buffer); i >=0 ; i = reader.read(buffer)){
                result.append(buffer, 0 , i);
            }
        }finally {
            reader.close();
        }

        Assert.assertTrue(result.indexOf("Google") >=0);
        Assert.assertEquals(date, snapshot.getDate());
        Assert.assertEquals(uri, snapshot.getURI());
        Assert.assertEquals(0, outputPath.list().length);
        Assert.assertTrue(outputPath.delete());
        */
    }

}
