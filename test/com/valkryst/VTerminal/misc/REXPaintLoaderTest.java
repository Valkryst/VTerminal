package com.valkryst.VTerminal.misc;

import org.junit.Test;

import java.io.IOException;

public class REXPaintLoaderTest {
    @Test(expected=NullPointerException.class)
    public void testLoad_withNullFile() throws IOException {
        REXPaintLoader.load(null);
    }
}
