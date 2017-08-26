Cataclysm: Dark Days Ahead

==================
RetroDays Tileset

Version: 1.0
Date: 16-04-2014
Author: Antistar (Based on work by HRose and Trihook)
E-mail: josephlollback@hotmail.com
==================


1. Description
2. Requirements
3. Installation/Uninstallation
4. Playing the mod
5. Frequently Asked Questions (FAQ)
6. Save games
7. Conflicts/Known Issues
8. Credits
9. Permissions
10. Contact and Information
11. Legal Stuff/Disclaimer
12. Version History

===============
1. DESCRIPTION
===============

Beyond (ideally) looking good, tilesets are great for efficiently communicating the environment to the player. If each tile is too big and your monitor is not high-res enough however, your view distance in-game is somewhat limited, which can become a handicap.

The base version of the RetroDays tileset uses very small 10x10 pixel tiles (though a 20x20 version is also available) in an attempt to solve this problem. So that it remains possible to interpret such small tiles, RetroDays uses a retro-inspired style reminiscent of the Commodore 64 era.

RetroDays is my personal continuation of HRose's unfinished 'Retro/ASCII' tileset, which in turn is based on Trihook's 'c= roguelike charset'. (Links in Credits section.) I too have drawn from Trihook's charset, in addition to making my own new tiles.

Note that RetroDays is usually targeted at the latest stable version of Cataclysm: Dark Days Ahead (CDDA). You may see the occasional missing tile if you're using an experimental version of CDDA.

===============
2. Requirements
===============

- Cataclysm DDA (http://en.cataclysmdda.com)

========================
3. INSTALLATION/UNINSTALLATION
========================

***Installation***

- Extract the contents of the RetroDays.7z archive file to your base CDDA directory (which contains the game's .exe).

- Merge/overwrite files and folders if asked.

- If using a post-0.A experimental version of CDDA:
--- Open CDDA\gfx\RetroDaysTileset10\tileset.txt for editing.
--- Remove 'gfx/' from the start of the path on the JSON and TILESET lines, so that those lines look like this:

JSON: RetroDaysTileset10/tile_config.json
TILESET: RetroDaysTileset10/retrodaystiles10.png

--- Repeat this process in CDDA\gfx\RetroDaysTileset20\tileset.txt.



***A Note on Updating***

- If you see an option for 'RetroDays' in your game's tileset options (as opposed to 'RetroDays10px' or 'RetroDays20px'), that's from an older version of this tileset and can be deleted. To do so:

- Delete the [RetroDaysTileset] directory from your CDDA\gfx\ directory.



***Uninstallation***

- In-game, switch to a different tileset.

- Delete the [RetroDaysTileset10] and [RetroDaysTileset20] directories from your CDDA\gfx\ directory.

=====================
4. PLAYING THE MOD
=====================

In the game's Display options, enable tilesets and select the RetroDays10px or RetroDays20px tileset.

===============
5. FREQUENTLY ASKED QUESTIONS (FAQ)
===============

None yet!

===============
6. SAVE GAMES
===============

N/A as far as I know.

============================
7. CONFLICTS / KNOWN ISSUES
============================

- As mentioned in the Description section, RetroDays is targeted at the latest stable version of CDDA. Some objects may appear as missing tiles if you're using an experimental version of the game.

- Artifacts may appear as missing tiles. Due to the way the game handles these, I haven't found how to define tiles for them yet.

- I make RetroDays with 10x10 pixel tiles, and this is the version I use when playing the game. The 20x20 version is simply the 10x10 version scaled up, and I don't really test it myself.

================================
8. CREDITS
================================

- RetroDays is by Antistar (Joseph Lollback).
- Based on HRose's unfinished Retro/ASCII tileset (http://smf.cataclysmdda.com/index.php?topic=3901.0)
- Uses some tiles from Trihook's 'c= roguelike charset' (http://csdb.dk/forums/?roomid=13&topicid=97045)

==========================
9. PERMISSIONS
==========================

- Thanks to the kind permissions of everyone involved, RetroDays is released under a CC-BY-SA license (http://creativecommons.org/licenses/by-sa/4.0/).

==========================
10. CONTACT AND INFORMATION
==========================

My e-mail address is josephlollback@hotmail.com

=========================
11. LEGAL STUFF/ DISCLAIMER
=========================

By downloading and using this modification, you agree that the author of the modification cannot be held responsible for any damage to software or hardware directly or indirectly caused by the aforementioned modification. Use at your own risk, basically.

=================
12. VERSION HISTORY
=================

Version 1.0 (16-04-14)
- Calling this version 1.0 now since it's finished for the current stable version of CDDA (v0.A).
- From here on out, RetroDays' version numbers are not intended to match those of CDDA itself.
- All CDDA 0.A stable tiles now accounted for (barring artifacts), plus a few bonus tiles for the current experimental.

Version 0.A WIP (13-03-14)
- Moved to CDDA 0.A stable as the target.
- Still heavily in-progress; updates may not get their own entry here until things settle down.

Version 0.9 WIP (24-02-14)
- Initial public release.
- Still heavily in-progress; updates may not get their own entry here until things settle down.