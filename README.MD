# AsciiPanel


AsciiPanel simulates a [code page 437](https://en.wikipedia.org/wiki/Code_page_437) ASCII terminal display. It supports all 256 characters of codepage 437, arbitrary foreground colors, arbitrary background colors, and arbitrary terminal sizes.

The default terminal size is 80x24 characters and default colors matching the Windows Command Prompt are provided. The default font is 9x16 pixel CP437 (`CP437_9x16`.)

This should be useful to roguelike developers.

## Usage

AsciiPanel supports the customization of fonts. The following system fonts are provided:
- CP437_9x16 
- CP437_8x8 
- CP437_10x10 
- CP437_12x12
- CP437_16x16

In addition, the following fontsets from the [Dwarf Fortress Tileset](http://dwarffortresswiki.org/Tileset_repository) are also included:
- DRAKE_10x10
- QBICFEET_10x10 
- TALRYTH_15x15 

The AsciiPanel class provides a special three-parameter constructor to support font customization. Simply pass the desired AsciiFont as the third parameter as follows.


    AsciiPanel myPanel = new AsciiPanel(80, 24, AsciiFont.DRAKE_10x10);


## Notes

This project is built with Java 8. However the code itself does not *require* Java 8. If you are supporting a project running an earlier version of Java, you can change the pom file and rebuild the jar using your chosen version of Java without having to modify the code.