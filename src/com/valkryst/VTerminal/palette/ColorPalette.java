package com.valkryst.VTerminal.palette;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class ColorPalette {
    @Getter private final Color defaultBackground = new Color(45, 45, 45, 255);
    @Getter private final Color defaultForeground = Color.WHITE;

    @Getter private final Color button_defaultBackground = defaultBackground;
    @Getter private final Color button_defaultForeground = new Color(0xFF2DBEFF, true);
    @Getter private final Color button_hoverBackground = new Color(0xFF2DFF63, true);
    @Getter private final Color button_hoverForeground = ColorFunctions.shade(button_hoverBackground, 0.5);
    @Getter private final Color button_pressedBackground = ColorFunctions.shade(button_hoverBackground, 0.25);
    @Getter private final Color button_pressedForeground = ColorFunctions.shade(button_hoverForeground, 0.25);

    @Getter private final Color checkBox_defaultBackground = button_defaultBackground;
    @Getter private final Color checkBox_defaultForeground = button_defaultForeground;
    @Getter private final Color checkBox_hoverBackground = button_hoverBackground;
    @Getter private final Color checkBox_hoverForeground = button_hoverForeground;
    @Getter private final Color checkBox_checkedBackground = button_defaultBackground;
    @Getter private final Color checkBox_checkedForeground = new Color(0xFFF9CA00, true);

    @Getter private final Color label_defaultBackground = defaultBackground;
    @Getter private final Color label_defaultForeground = new Color(0xFFF9CA00, true);

    @Getter private final Color layer_defaultBackground = defaultBackground;
    @Getter private final Color layer_defaultForeground = Color.WHITE;

    @Getter private final Color progressBar_incompleteBackground = defaultBackground;
    @Getter private final Color progressBar_incompleteForeground = new Color(0xFFFF2D55, true);
    @Getter private final Color progressBar_completeBackground = defaultBackground;
    @Getter private final Color progressBar_completeForeground = new Color(0xFF2DFF6E, true);

    @Getter private final Color radioButton_defaultBackground = button_defaultBackground;
    @Getter private final Color radioButton_defaultForeground = button_defaultForeground;
    @Getter private final Color radioButton_hoverBackground = button_hoverBackground;
    @Getter private final Color radioButton_hoverForeground = button_hoverForeground;
    @Getter private final Color radioButton_pressedBackground = defaultBackground;
    @Getter private final Color radioButton_pressedForeground = new Color(0xFFF9CA00, true);

    @Getter private final Color textArea_defaultBackground = new Color(0xFF68D0FF, true);
    @Getter private final Color textArea_defaultForeground = new Color(0xFF8E999E, true);
    @Getter private final Color textArea_caretBackground = textArea_defaultForeground;
    @Getter private final Color textArea_caretForeground = textArea_defaultBackground;
}
