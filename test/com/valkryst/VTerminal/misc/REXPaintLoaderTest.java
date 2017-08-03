package com.valkryst.VTerminal.misc;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class REXPaintLoaderTest {
    @Test(expected=NullPointerException.class)
    public void testLoad_withNullFile() throws IOException {
        REXPaintLoader.load(null);
    }

    @Test(expected=IOException.class)
    public void testLoad_withNonExistantFile() throws IOException {
        REXPaintLoader.load(new File("dfgjsdkjfghokfh"));
    }
}
