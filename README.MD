#### License

Use this code however you wish. Modifications and improvements are welcome.

Please refer to the LICENSE file for additional information.

---

![](https://travis-ci.org/Valkryst/VTerminal.svg?branch=master) [![codecov](https://codecov.io/gh/Valkryst/VTerminal/branch/master/graph/badge.svg)](https://codecov.io/gh/Valkryst/VTerminal) [![Release](https://jitpack.io/v/Valkryst/VTerminal.svg)](https://jitpack.io/#Valkryst/VTerminal)

# VTerminal


VPanel simulates a Unicode terminal display. It supports arbitrary fore/background colors,  terminal sizes, and allows
the use of any font.

This project is currently under active development and code-breaking changes may be committed as development progresses.

## Jar Files & Maven

To use this project as a Maven dependency, click on the JitPack badge [![Release](https://jitpack.io/v/Valkryst/VTerminal.svg)](https://jitpack.io/#Valkryst/VTerminal), select a version, click the "Get it!" button, and then follow the instructions.

If you would rather use a Jar file, then you can find the Jars on the [releases](https://github.com/Valkryst/VTerminal/releases) page.

## Example Test Code

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

## JavaDoc Documentation

Whenever a Travis CI build passes, the JavaDocs are auto-generated and made available at the following link.

https://valkryst.github.io/VTerminal/

## Fonts
VTerminal supports the use of any bitmap font. The  [DejaVu](http://dejavu-fonts.org/wiki/Main_Page) and 
[GNU FreeFont](https://www.gnu.org/software/freefont/) monospace fonts are supplied with this project in 16pt, 18pt, and 
20pt sizes.

To use a different font:

* Download and install [BMFont](http://www.angelcode.com/products/bmfont/).
* Under *Options->Font Settings*
    * Font: Specify whichever font you want to use.
    * Charset: Unicode
    * Size (px): Set this to whichever size you desire.
    * Match char height: Enabled
    * Height %: 100
    * Enable Bold and/or Italic if you want to.
    * Output invalid char glyph: Enabled
    * Do not include kerning pairs: Enabled
    * Render from TrueType outline: Enabled
    * TrueType hinting: Enabled
    * Font smoothing: Disabled
    * Super sampling: Disabled
    * Outline Thickness: 0
* On the right-hand size of the screen, check every box. They should all be solid green.
* Under *Options->Export Options*
    * All padding/spacing settings must be set to zero.
    * Force offsets to zero: Enabled
    * Width: 2048
        * This may need to be adjusted based on the font size that you choose.
    * Height: 1024
        * This may need to be adjusted based on the font size that you choose.
    * Bit depth: 32
    * Ignore the ARGB fields and their Invert checkboxes.
    * Preset: White text with alpha
    * Font descriptor: Text
    * Textures: png
* Do *Options->Save bitmap font as...* and save your font. This will generate a png and fnt file.

## Misc

* The use of non-mono-space fonts is not supported, but it not specifically prevented.
* Ensure the font png image uses a transparent background with white characters. The program relies on this and unintended behaviour could occur with any other settings.

### Screen Draw Process

1. All components are drawn onto the Screen.
2. The Screen is drawn onto the Panel.
3. The Layers are drawn on top of the Screen on the Panel.
4. The Sub-Screens are drawn on top of the Screen/Layers on the Panel.
