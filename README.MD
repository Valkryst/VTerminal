#### License: 

Use this code however you wish. Modifications and improvements are welcome.

Please refer to the LICENSE file for additional information.

---

# VPanel


VPanel simulates a Unicode terminal display. It supports arbitrary fore/background colors,  terminal sizes, and allows
the use of any font.

This project is currently under active development and code-breaking changes may committed as development progresses. This readme file will be updated to note when the project is in a usable state.

## How to Setup (Development):

1. Clone the project from GitHub.
2. Clone [this](https://github.com/Valkryst/VRadio) project from GitHub as well.
2. Open the VTerminal project in IntelliJ.
3. Open the *Project Structure* menu. (S + ALT + CTRL + SHIFT)
4. Enter the *Modules* subsection.
5. Click the green "+" at the top of the window.
6. Select *Import Module*.
7. Navigate to the cloned VRadio project and double-click on *pom.xml*.
8. Click on VTerminal in the *Modules* subsection.
9. Enter the *Dependencies* tab of your project.
10. Click on the green "+" at the right-edge of the window.
11. Click *Module Dependency...*.
12. Select *VRadio*.

You can now work on the VTerminal project.

### Example Test Code:

```java
try {
    final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt");
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

### Fonts:
VTerminal supports the use of any bitmap font. The  [DejaVu](http://dejavu-fonts.org/wiki/Main_Page) and 
[GNU FreeFont](https://www.gnu.org/software/freefont/) monospace fonts are supplied with this project in 16pt, 18pt, and 
20pt sizes.

To use a different font:

* Download and install [BMFont](http://www.angelcode.com/products/bmfont/).
* Under *Options->Font Settings* use the following settings:
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
* Under *Options->Export Options* use the following settings:
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
