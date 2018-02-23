package com.valkryst.VTerminal.palette;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class ColorPalette {
    @Getter private Color defaultBackground =  new Color(45, 45, 45, 255);
    @Getter private Color defaultForeground = new Color(0xFFF9CA00, true);

    @Getter private Color button_defaultBackground = defaultBackground;
    @Getter private Color button_defaultForeground = new Color(0xFF2DBEFF, true);
    @Getter private Color button_hoverBackground = new Color(0xFF2DFF63, true);
    @Getter private Color button_hoverForeground = ColorFunctions.shade(button_hoverBackground, 0.5);
    @Getter private Color button_pressedBackground = ColorFunctions.shade(button_hoverBackground, 0.25);
    @Getter private Color button_pressedForeground = ColorFunctions.shade(button_hoverForeground, 0.25);

    @Getter private Color checkBox_defaultBackground = button_defaultBackground;
    @Getter private Color checkBox_defaultForeground = button_defaultForeground;
    @Getter private Color checkBox_hoverBackground = button_hoverBackground;
    @Getter private Color checkBox_hoverForeground = button_hoverForeground;
    @Getter private Color checkBox_checkedBackground = button_defaultBackground;
    @Getter private Color checkBox_checkedForeground = defaultForeground;

    @Getter private Color layer_defaultBackground = defaultBackground;
    @Getter private Color layer_defaultForeground = defaultForeground;

    @Getter private Color progressBar_incompleteBackground = defaultBackground;
    @Getter private Color progressBar_incompleteForeground = new Color(0xFFFF2D55, true);
    @Getter private Color progressBar_completeBackground = defaultBackground;
    @Getter private Color progressBar_completeForeground = new Color(0xFF2DFF6E, true);

    @Getter private Color radioButton_defaultBackground = button_defaultBackground;
    @Getter private Color radioButton_defaultForeground = button_defaultForeground;
    @Getter private Color radioButton_hoverBackground = button_hoverBackground;
    @Getter private Color radioButton_hoverForeground = button_hoverForeground;
    @Getter private Color radioButton_pressedBackground = defaultBackground;
    @Getter private Color radioButton_pressedForeground = defaultForeground;

    @Getter private Color textArea_defaultBackground = new Color(0xFF68D0FF, true);
    @Getter private Color textArea_defaultForeground = new Color(0xFF8E999E, true);
    @Getter private Color textArea_caretBackground = textArea_defaultForeground;
    @Getter private Color textArea_caretForeground = textArea_defaultBackground;
}
