#### License: 

Use this code however you wish. Modifications and improvements are welcome.

Please refer to the LICENSE file for additional information.

---

![](https://travis-ci.org/Valkryst/VTerminal.svg?branch=master) [![codecov](https://codecov.io/gh/Valkryst/VTerminal/branch/master/graph/badge.svg)](https://codecov.io/gh/Valkryst/VTerminal)

# VPanel


VPanel simulates a Unicode terminal display. It supports arbitrary fore/background colors,  terminal sizes, and allows
the use of any font.

This project is currently under active development and code-breaking changes may be committed as development progresses.

### Example Test Code:

```java
try {
    final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    final Panel panel = new PanelBuilder().setAsciiFont(font)
                                          .setWidthInCharacters(48)
                                          .setHeightInCharacters(36)
                                          .build();

    panel.getCurrentScreen().clear('X');
    panel.draw();
} catch (final IOException | URISyntaxException e) {
    e.printStackTrace();
}
```

There is an issue with the above code that may sometimes appear.

It's possible for the *draw()* call to execute before the *Panel* shows up on-screen. In this case it will display a blank screen.

Making a call to the *draw()* function, after the Panel becomes visible, will properly render the *Panel*'s *Screen*.

### JavaDoc Documentation:

Whenever a Travis CI build passes, the JavaDocs are auto-generated and made available at the following link.

https://valkryst.github.io/VTerminal/

### Fonts:
VTerminal supports the use of any bitmap font. The  [DejaVu](http://dejavu-fonts.org/wiki/Main_Page) and 
[GNU FreeFont](https://www.gnu.org/software/freefont/) monospace fonts are supplied with this project in 16pt, 18pt, and 
20pt sizes.

To use a different font:

* Download and install [BMFont](http://www.angelcode.com/products/bmfont/).
* Under *Options->Font Settings*:
    * Font: Specify whichever font you want to use.
    * Charset: Unicode
    * Size (px): Set this to whichever size you desire.
    * Match char height: Enabled
    * Height %: 100
    * Output invalid char glyph: Enabled
    * Do not include kerning pairs: Enabled
    * Render from TrueType outline: Enabled
    * TrueType hinting: Enabled
    * Font smoothing: Disabled
    * Super sampling: Disabled
* On the right-hand size of the screen, check every box.
* Under *Options->Export Options*:
    * All padding/spacing settings must be set to zero.
    * Force offsets to zero: Enabled
    * Width: 2048
        * This may need to be adjusted based on the font size that you choose.
    * Height: 1024
        * This may need to be adjusted based on the font size that you choose.
    * Bit depth: 8
    * A: glyph
    * Font descriptor: Text
    * Textures: png
* Do *Options->Save bitmap font as...* and save your font. This will generate a png and txt file.
* Run the following regex on the text file, then remove any blank lines.
    * You can use [this](https://regex101.com/) site to perform the regex operation.
    * `info.*|common.*|page.*|chars.*|char id=\d\d\d\d\d\d.*|char id=[7-9]\d\d\d\d.*|char id=6[6-9]\d\d\d.*|char id=65[6-9]\d\d.*|char id=655[4-9]\d.*|char id=6553[6-9].*|    xoff.*|char id=|x=|y=|width=|height=`
  
### Misc:

* The use of non-mono-space fonts is not supported, but it not specifically prevented.
* Ensure the font png image does *not* use a transparent background. Using a transparent background increases redraw times by ~20-30ms.
* Ensure the font png image uses black for the background and white for the foreground. An optimization in the AsciiCharacter's draw method requires this and the program may break otherwise.
