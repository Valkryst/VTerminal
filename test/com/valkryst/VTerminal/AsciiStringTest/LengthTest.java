package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Test;

public class LengthTest {
    @Test
    public void testLength() {
        String string = "";

        for (int i = 0 ; i < 10 ; i++) {
            final AsciiString asciiString = new AsciiString(string);

            Assert.assertEquals(i, asciiString.length());

            string += 'A';
        }
    }
}
