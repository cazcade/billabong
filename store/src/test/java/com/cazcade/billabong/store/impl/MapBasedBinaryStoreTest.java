package com.cazcade.billabong.store.impl;

import com.cazcade.billabong.store.BinaryStore;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MapBasedBinaryStoreTest {
    
    private static final String STORE_KEY = "STORE_KEY";
    private static final String STORE_KEY2 = "STORE_KEY2";

    @Test
    public void testMapBasedBinaryStore() throws IOException {
        BinaryStore store = new MapBasedBinaryStore();

        InputStream storeEntry = IOUtils.toInputStream("TESTY");
        Assert.assertTrue(store.placeInStore(STORE_KEY, storeEntry, false));
        storeEntry = IOUtils.toInputStream("TESTY2");
        Assert.assertTrue(store.placeInStore(STORE_KEY2, storeEntry, true));

        Assert.assertEquals("TESTY", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));

        storeEntry = IOUtils.toInputStream("TESTY3");
        Assert.assertFalse(store.placeInStore(STORE_KEY, storeEntry, false));
        Assert.assertEquals("TESTY", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));
        Assert.assertTrue(store.placeInStore(STORE_KEY, storeEntry, true));
        Assert.assertEquals("TESTY2", IOUtils.toString(store.retrieveFromStore(STORE_KEY2).getContent()));
        Assert.assertEquals("TESTY3", IOUtils.toString(store.retrieveFromStore(STORE_KEY).getContent()));

    }
}
