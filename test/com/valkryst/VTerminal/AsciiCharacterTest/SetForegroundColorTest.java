package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetForegroundColorTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withValidInput() {
        character.setForegroundColor(Color.RED);
        Assert.assertEquals(character.getForegroundColor(), Color.RED);
    }

    @Test(expected=NullPointerException.class)
    public void withNullColor() {
        character.setForegroundColor(Color.RED);
        character.setForegroundColor(null);
    }

    @Test
    public void withColorThatIsTheSameAsTheExistingForegroundColor() {
        character.setForegroundColor(character.getForegroundColor());
    }
}
