package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import org.junit.Before;

import java.awt.Color;

public class DetailedToStringTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }
}
