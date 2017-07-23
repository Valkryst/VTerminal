package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Test;

public class ConstructorTest {
    @Test
    public void withAllCharacters() {
        for (byte i = 0 ; i < Byte.MAX_VALUE ; i++) {
            new AsciiCharacter((char) i);
        }
    }
}
