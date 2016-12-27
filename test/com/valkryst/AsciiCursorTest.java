package com.valkryst;

import com.valkryst.AsciiPanel.AsciiCursor;
import org.junit.Test;

public class AsciiCursorTest {
    @Test(expected=NullPointerException.class)
    public void AsciiCursorA() {
        new AsciiCursor(null);
    }
}
