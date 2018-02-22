package com.valkryst.VTerminal.revamp.component.palette;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class ColorPalette {
    @Getter private Color defaultBackground =  new Color(45, 45, 45, 255);
    @Getter private Color defaultForeground = new Color(0xFFF9CA00, true);

    @Getter private Color button_defaultBackground = new Color(45, 45, 45, 255);
    @Getter private Color button_defaultForeground = new Color(0xFF2DBEFF, true);
    @Getter private Color button_hoverBackground = new Color(0xFF2DFF63, true);
    @Getter private Color button_hoverForeground = ColorFunctions.shade(button_hoverBackground, 0.5);
    @Getter private Color button_pressedBackground = ColorFunctions.shade(button_hoverBackground, 0.25);
    @Getter private Color button_pressedForeground = ColorFunctions.shade(button_hoverForeground, 0.25);
}
