This document is still a work in progress.

# Basic Info

* To use graphical tile sprites rather than font sprites, you *must* have both a tile sprite sheet and a custom **.fnt* file which tells the FontLoader how to separate the sprites from the sheet.

* I highly recommend, for ease of use, that you use fixed-sized sprites. So, everything on your sprite sheet would be 8x8, 16x16, 32x32, etc...

* When placing graphical tiles, you *must* use the *AsciiString* function *setCharacter(columnIndex, new AsciiTile(characterId))*.

# Requirements

* You *must* ensure that the sprite sheet has a fully transparent background.
* You *must* create a valid custom **.fnt* file to go with the sprite sheet.
* You *must* draw tiles using an AsciiTile object.
    * This does not apply if your sprites are purely white on a transparent background, with no other colors.
    * For an example, see this [file](https://github.com/Valkryst/VTerminal/blob/master/test/com/valkryst/VTerminal/samples/SampleTileSheet.java).

# Data File Format

    char id=# x=# y=# width=# height=#
    
* **char id** - A [Unicode](https://unicode-table.com/en/) integer value representing the character that represents the sprite.
* **x** - The x-axis pixel location, on the sprite sheet, of the top-left pixel of the sprite.
* **y** - The y-axis pixel location, on the sprite sheet, of the top-left pixel of the sprite.
* **width** - The width of the sprite.
* **height** - The height of the sprite.

## Example #1

    char id=32 x=0 y=0 width=32 height=32
    
* This **.fnt* file defines a single sprite.
* The sprite is represented by the ' ' whitespace character *(char id=32)*.
* The sprite's top-left pixel is at (0, 0) because *(x=0)* and *(y=0)*.
* The sprite is 32x32 pixels because *(width=32)* *(height=32)*.

## Example #2

    char id=32 x=0 y=0 width=32 height=32
    char id=33 x=32 y=0 width=32 height=32
    
* This **.fnt* file defines a two sprites.
* The first sprite:
    * The sprite is represented by the ' ' whitespace character *(char id=32)*.
    * The sprite's top-left pixel is at (0, 0) because *(x=0)* and *(y=0)*.
    * The sprite is 32x32 pixels because *(width=32)* *(height=32)*.
* The second sprite:
    * The sprite is represented by the '!' exclamation character *(char id=33)*.
    * The sprite's top-left pixel is at (32, 0) because *(x=32)* and *(y=0)*.
    * The sprite is 32x32 pixels because *(width=32)* *(height=32)*.
    
## Data File Generation

* This code will automatically generate the contents of a valid **.fnt* file. 
* You will need to edit the image/sprite width/height variables.
* You may want to manually change the character IDs of the generated data, but this will get you started.

```java
  public static void main(String[] args) {
    char id = 32; // Starts at the Unicode whitespace char.
    
    final int imageWidth = 1280;
    final int imageHeight = 960;
    
    final int spriteWidth = 32;
    final int spriteHeight = 32;
   
    
    for (int y = 0 ; y < imageHeight ; y += spriteHeight) {
      for (int x = 0 ; x < imageWidth ; x += spriteWidth) {
        System.out.println("char id=" + (int)id + "\tx=" + x + "\ty=" + y + "\twidth=" + 32 + "\theight=" + 32);
        id++;
      }
    }
  }
```