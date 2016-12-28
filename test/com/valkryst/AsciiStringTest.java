package com.valkryst;

import com.valkryst.AsciiPanel.AsciiCharacter;
import com.valkryst.AsciiPanel.AsciiString;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class AsciiStringTest {
    @Test
    public void AsciiStringA() {
        final AsciiString asciiString = new AsciiString(0);
        Assert.assertEquals(asciiString.getCharacters().length, 0);
    }

    @Test
    public void AsciiStringB() {
        AsciiString asciiString;
        boolean resultsCorrect = true;

        for (int i = 0 ; i < 10 ; i++) {
            asciiString = new AsciiString(i);
            resultsCorrect &= asciiString.getCharacters().length == i;
        }

        Assert.assertTrue(resultsCorrect);
    }

    @Test
    public void AsciiStringC() {
        final AsciiString asciiString = new AsciiString(null);
        Assert.assertEquals(asciiString.getCharacters().length, 0);
    }

    @Test
    public void AsciiStringD() {
        String string;
        AsciiString asciiString;
        boolean resultsCorrect = true;

        for (int i = 0 ; i < 10 ; i++) {
            // Create AsciiString with random string:
            final int randomInt = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            string = Integer.toHexString(randomInt);
            asciiString = new AsciiString(string);

            // Check if all characters were properly set:
            boolean allCharactersCorrect = true;

            for (int j = 0 ; j < string.length() ; j++) {
                allCharactersCorrect &= asciiString.getCharacters()[j].getCharacter() == string.charAt(j);
            }

            resultsCorrect &= allCharactersCorrect;
        }

        Assert.assertTrue(resultsCorrect);
    }

    @Test
    public void equalsA() {
        final AsciiString asciiStringA = new AsciiString("nerfherder");
        final Object object = new Object();
        Assert.assertFalse(asciiStringA.equals(object));
    }

    @Test
    public void equalsB() {
        final AsciiString asciiStringA = new AsciiString("nerfherder");
        final AsciiString asciiStringB = new AsciiString("nerfherdernerfherder");
        Assert.assertFalse(asciiStringA.equals(asciiStringB));
    }

    @Test
    public void equalsC() {
        final AsciiString asciiStringA = new AsciiString("nerfherder");
        final AsciiString asciiStringB = new AsciiString("nerfherder");
        Assert.assertTrue(asciiStringA.equals(asciiStringB));
    }

    @Test
    public void setCharacterA() {
        final AsciiString asciiString = new AsciiString("123");
        asciiString.setCharacter(0, null);

        System.out.println(asciiString.getCharacters()[0].getCharacter());
        Assert.assertTrue(asciiString.getCharacters()[0].getCharacter() == '1');
    }

    @Test
    public void setCharacterB() {
        final AsciiString asciiStringA = new AsciiString("123");
        final AsciiString asciiStringB = new AsciiString("123");
        asciiStringA.setCharacter(-1, new AsciiCharacter('a'));

        Assert.assertTrue(asciiStringA.equals(asciiStringB));
    }

    @Test
    public void setCharacterC() {
        final AsciiString asciiStringA = new AsciiString("123");
        final AsciiString asciiStringB = new AsciiString("123");
        asciiStringA.setCharacter(3, new AsciiCharacter('a'));

        Assert.assertTrue(asciiStringA.equals(asciiStringB));
    }

    @Test
    public void setCharacterD() {
        final AsciiString asciiStringA = new AsciiString("123");
        final AsciiString asciiStringB = new AsciiString("123");
        asciiStringA.setCharacter(-1, null);

        Assert.assertTrue(asciiStringA.equals(asciiStringB));
    }

    @Test
    public void setCharacterE() {
        final AsciiString asciiStringA = new AsciiString("123");
        final AsciiString asciiStringB = new AsciiString("abc");

        final AsciiCharacter characterA = new AsciiCharacter('a');
        final AsciiCharacter characterB = new AsciiCharacter('b');
        final AsciiCharacter characterC = new AsciiCharacter('c');

        asciiStringA.setCharacter(0, characterA);
        asciiStringA.setCharacter(1, characterB);
        asciiStringA.setCharacter(2, characterC);

        Assert.assertTrue(asciiStringA.equals(asciiStringB));
    }
}
