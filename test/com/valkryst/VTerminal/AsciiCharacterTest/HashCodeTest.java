package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Test;

public class HashCodeTest {
    @Test
    public void withEqualCharacters() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('A');
        Assert.assertEquals(characterA.hashCode(), characterB.hashCode());
    }

    @Test
    public void withNonEqualCharacters() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('B');
        Assert.assertNotEquals(characterA.hashCode(), characterB.hashCode());
    }
}
