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

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

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
