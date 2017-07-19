package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class HashCodeTest {
    // TODO Fix issues with this test.
    /*
    @Test
    public void withEqualStrings() {
        final AsciiString stringA = new AsciiString("A");
        stringA.setBackgroundAndForegroundColor(Color.BLACK, Color.WHITE);

        final AsciiString stringB = new AsciiString("A");
        stringB.setBackgroundAndForegroundColor(Color.BLACK, Color.WHITE);

        Assert.assertEquals(stringA.hashCode(), stringB.hashCode());
    }
    */

    @Test
    public void withNonEqualStrings() {
        final AsciiString stringA = new AsciiString("AAA");
        stringA.setBackgroundAndForegroundColor(Color.BLACK, Color.WHITE);

        final AsciiString stringB = new AsciiString("BBB");
        stringB.setBackgroundAndForegroundColor(Color.RED, Color.WHITE);

        Assert.assertNotEquals(stringA.hashCode(), stringB.hashCode());
    }
}
