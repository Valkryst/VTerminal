package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Before;
import org.junit.Test;

public class DisableBlinkEffectTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withNullTimer() {
        character.disableBlinkEffect();
    }

    @Test
    public void withRunningTimer() {
        character.enableBlinkEffect((short) 666, new Radio<>());
        character.disableBlinkEffect();
    }
}
