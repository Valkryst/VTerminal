package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class EqualsTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void withNonAsciiStringObject() {
        final AsciiString stringA = new AsciiString(testString);
        stringA.setBackgroundColor(Color.BLACK);
        stringA.setForegroundColor(Color.WHITE);

        final Integer integer = 666;

        Assert.assertNotEquals(stringA, integer);
    }
    
    @Test
    public void withSelf() {
        Assert.assertEquals(string, string);
    }

    @Test
    public void withEqualStrings() {
        final AsciiString stringA = new AsciiString(testString);
        stringA.setBackgroundColor(Color.BLACK);
        stringA.setForegroundColor(Color.WHITE);

        final AsciiString stringB = new AsciiString(testString);
        stringB.setBackgroundColor(Color.BLACK);
        stringB.setForegroundColor(Color.WHITE);

        Assert.assertEquals(stringA, stringB);
    }

    @Test
    public void withNonEqualStrings() {
        final AsciiString stringA = new AsciiString(testString);
        stringA.setBackgroundColor(Color.BLACK);
        stringA.setForegroundColor(Color.WHITE);

        final AsciiString stringB = new AsciiString("Xfgcgfytf");
        stringB.setBackgroundColor(Color.BLACK);
        stringB.setForegroundColor(Color.WHITE);

        Assert.assertNotEquals(stringA, stringB);
    }

    @Test
    public void withNonEqualBackgroundColors() {
        final AsciiString stringA = new AsciiString(testString);
        stringA.setBackgroundColor(Color.BLACK);
        stringA.setForegroundColor(Color.WHITE);

        final AsciiString stringB = new AsciiString(testString);
        stringB.setBackgroundColor(Color.BLACK);
        stringB.setForegroundColor(Color.RED);

        Assert.assertNotEquals(stringA, stringB);
    }

    @Test
    public void withNonEqualForegroundColors() {
        final AsciiString stringA = new AsciiString(testString);
        stringA.setBackgroundColor(Color.BLACK);
        stringA.setForegroundColor(Color.WHITE);

        final AsciiString stringB = new AsciiString(testString);
        stringB.setBackgroundColor(Color.RED);
        stringB.setForegroundColor(Color.WHITE);

        Assert.assertNotEquals(stringA, stringB);
    }
}
