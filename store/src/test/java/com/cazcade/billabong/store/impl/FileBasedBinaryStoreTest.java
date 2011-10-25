package com.cazcade.billabong.store.impl;

import com.cazcade.billabong.common.impl.StaticTestDateHelper;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class FileBasedBinaryStoreTest {

    private static final String STORE_KEY = "STORE_KEY";
    private static final String STORE_KEY2 = "STORE_KEY2";
    private final File storeDirectory = new File("target/teststore");
    private final File storeFile = new File(storeDirectory, STORE_KEY);
    private final File storeFile2 = new File(storeDirectory, STORE_KEY2);
    private final Date lastModifiedDate = new Date();

    @Test
    public void testEntryAdditionAndReplacement() throws IOException {
        StaticTestDateHelper dateHelper = new StaticTestDateHelper();
        dateHelper.setDate(lastModifiedDate);
        FileBasedBinaryStore store = new FileBasedBinaryStore(storeDirectory);
        store.setDateHelper(dateHelper);

        Assert.assertEquals(0, storeDirectory.list().length);

        InputStream storeEntry = IOUtils.toInputStream("TESTY");
        Assert.assertTrue(store.placeInStore(STORE_KEY, storeEntry, false));
        Assert.assertEquals(1, storeDirectory.list().length);
        Assert.assertTrue(storeFile.exists());
        Assert.assertFalse(storeFile2.exists());

        storeEntry = IOUtils.toInputStream("TESTY2");
        Assert.assertTrue(store.placeInStore(STORE_KEY2, storeEntry, true));
        Assert.assertEquals(2, storeDirectory.list().length);
        Assert.assertTrue(storeFile.exists());
        Assert.assertTrue(storeFile2.exists());

        Assert.assertEquals("TESTY", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));
        Assert.assertEquals("TESTY2", IOUtils.toString(store.retrieveFromStore(STORE_KEY2).getContent()));

        storeEntry = IOUtils.toInputStream("TESTY3");
        Assert.assertFalse(store.placeInStore(STORE_KEY, storeEntry, false));
        Assert.assertEquals("TESTY", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));
        Assert.assertEquals(2, storeDirectory.list().length);
        Assert.assertTrue(storeFile.exists());
        Assert.assertTrue(storeFile2.exists());

        Assert.assertTrue(store.placeInStore(STORE_KEY, storeEntry, true));
        Assert.assertEquals("TESTY2", IOUtils.toString(store.retrieveFromStore(STORE_KEY2).getContent()));
        Assert.assertEquals("TESTY3", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));
        Assert.assertEquals(2, storeDirectory.list().length);
        Assert.assertTrue(storeFile.exists());
        Assert.assertTrue(storeFile2.exists());

        //last modified only accurate to the nearest second last 3 digits 000.

        Assert.assertTrue(Math.abs(lastModifiedDate.getTime() - storeFile.lastModified()) <=1000l);
        Assert.assertTrue(Math.abs(lastModifiedDate.getTime() - storeFile2.lastModified()) <=1000l);
    }

    @Test
    public void testEntryRemoval() throws IOException {
        StaticTestDateHelper dateHelper = new StaticTestDateHelper();
        dateHelper.setDate(lastModifiedDate);
        FileBasedBinaryStore store = new FileBasedBinaryStore(storeDirectory);
        store.setDateHelper(dateHelper);

        Assert.assertEquals(0, storeDirectory.list().length);

        InputStream storeEntry = IOUtils.toInputStream("TESTY");
        Assert.assertTrue(store.placeInStore(STORE_KEY, storeEntry, false));
        Assert.assertEquals(1, storeDirectory.list().length);
        Assert.assertTrue(storeFile.exists());
        Assert.assertFalse(storeFile2.exists());

        storeEntry = IOUtils.toInputStream("TESTY2");
        Assert.assertTrue(store.placeInStore(STORE_KEY2, storeEntry, true));
        Assert.assertEquals(2, storeDirectory.list().length);
        Assert.assertTrue(storeFile.exists());
        Assert.assertTrue(storeFile2.exists());

        Assert.assertEquals("TESTY", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));
        Assert.assertEquals("TESTY2", IOUtils.toString(store.retrieveFromStore(STORE_KEY2).getContent()));

        Assert.assertTrue(store.placeInStore(STORE_KEY, null, true));
        Assert.assertEquals(1, storeDirectory.list().length);
        Assert.assertFalse(storeFile.exists());
        Assert.assertTrue(storeFile2.exists());
    }

    @After
    public void cleanup() {
        for(File fileToDelete : storeDirectory.listFiles()){
            fileToDelete.delete();
        }

        storeDirectory.delete();
    }

}